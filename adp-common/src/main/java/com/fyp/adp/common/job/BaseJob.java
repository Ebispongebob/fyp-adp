package com.fyp.adp.common.job;


import com.alibaba.fastjson.JSON;
import com.fyp.adp.common.constants.Constants;
import com.fyp.adp.common.utils.HDFSSupport;
import com.fyp.adp.common.utils.PropertiesUtil;
import com.google.common.collect.Maps;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.contrib.streaming.state.PredefinedOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public abstract class BaseJob implements Job {

    protected static Logger logger = LoggerFactory.getLogger(BaseJob.class);

    /**
     * 启动参数
     */
    protected String[] args;

    /**
     * 执行环境
     */
    private StreamExecutionEnvironment env;

    private String left = "{";

    private String right = "}";

    public BaseJob(String[] args) {
        this.args = args;
    }

    /**
     * 开始运行flink任务
     */
    @Override
    public void run() throws Exception {
        init();

        ParameterTool params = (ParameterTool) env.getConfig().getGlobalJobParameters();

        doRun(env, params);

        env.execute();
    }

    /**
     * 开始运行flink任务
     * @param env 环境
     * @param params 全局参数
     * @throws Exception 执行异常
     */
    protected abstract void doRun(StreamExecutionEnvironment env, ParameterTool params) throws Exception;

    /**
     * 初始化
     */
    protected void init() {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 默认最大并行度256
        env.setMaxParallelism(256);

        // 开启rocksdb
        // 默认增量
        EmbeddedRocksDBStateBackend rocksDB = new EmbeddedRocksDBStateBackend(true);
        // 采用预定义的针对固态硬盘的参数配置
        rocksDB.setPredefinedOptions(PredefinedOptions.FLASH_SSD_OPTIMIZED);
        env.setStateBackend(rocksDB);

        // 配置参数全局化

        // 原有全局参数
        Map<String, String> params = Maps.newHashMap();
        params.putAll(env.getConfig().getGlobalJobParameters().toMap());

        // 参数化配置优先级：系统环境变量参数(-Dyy=xx) > main函数参数(--name hello) > 大于配置文件参数(app.name=test)
        Map<String, String> fileParams = Maps.newHashMap();
        for (Map.Entry<Object, Object> entry : PropertiesUtil.getProperties(Constants.APPLICATION_FILE_NAME).entrySet()) {
            if (null != entry.getKey() && null != entry.getValue()) {
                fileParams.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        ParameterTool parameterTool = ParameterTool.fromMap(fileParams)
                                                   .mergeWith(ParameterTool.fromArgs(args))
                                                   .mergeWith(ParameterTool.fromSystemProperties());

        // 覆盖全局参数
        params.putAll(parameterTool.toMap());
        env.getConfig().setGlobalJobParameters(ParameterTool.fromMap(params));

        // 内部不重启，由波塞冬重启处理
        env.setRestartStrategy(RestartStrategies.noRestart());
    }

    /**
     * 从命令行当中解析出参数对象
     * @param str 命令行参数
     * @return 参数对象
     */
    protected  <T> T parseCommandLine(String str, Class<T> cls) {
        // 直接传入json
        if (str.startsWith(left) && str.endsWith(right)) {
            return JSON.parseObject(str, cls);
        }

        try {
            // 从hdfs读取json数据
            return JSON.parseObject(HDFSSupport.readArgs(str), cls);
        } catch (IOException e) {
            throw new RuntimeException("从hdfs读取参数异常：", e);
        }
    }
}

package com.fyp.adp.basedata.datasource;

import com.fyp.adp.basedata.datasource.enums.DatasourceType;
import com.fyp.adp.basedata.user.entity.DatasourceConfig;
import com.fyp.adp.basedata.user.mapper.DatasourceConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

/**
 * @Description: datasource factory
 * @Author: ruitao.wei
 * @Date: 2023/3/10 15:22
 */
@Component
public class DatasourceFactory {

    @Autowired
    DatasourceConfigMapper datasourceConfigMapper;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取数据源配置
     * @param: datasourceType 数据源类型
     * @return: 数据源配置
     */
    public DatasourceConfig getDatasourceConfig(DatasourceType datasourceType) {
        return getDatasourceConfig(datasourceType.getDefault());
    }

    /**
     * 获取数据源配置
     * @param: datasourceName 数据源名称
     * @return: 数据源配置
     */
    public DatasourceConfig getDatasourceConfig(String datasourceName) {
        Example          example  = new Example(DatasourceConfig.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("datasourceName", datasourceName);
        DatasourceConfig config = datasourceConfigMapper.selectOneByExample(example);
        return config.getEnable() == 1 ? config : null;
    }
}

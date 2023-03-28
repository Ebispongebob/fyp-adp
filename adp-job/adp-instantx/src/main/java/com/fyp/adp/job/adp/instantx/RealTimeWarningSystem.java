package com.fyp.adp.job.adp.instantx;

import com.fyp.adp.basedata.event.Event;
import com.fyp.adp.basedata.rule.WarningRule;
import com.fyp.adp.job.adp.instantx.schema.EventSchema;
import com.fyp.adp.job.adp.instantx.schema.WarningSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class RealTimeWarningSystem {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Kafka配置
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");

        // 创建Kafka消费者
        FlinkKafkaConsumer<Event> kafkaConsumer = new FlinkKafkaConsumer<>(
                "input-topic",
                new EventSchema(),
                properties
        );

        // 从Kafka中读取数据并创建数据流
        DataStream<Event> inputEventStream = env.addSource(kafkaConsumer);

        // 从MySQL数据库中读取预警规则
        List<WarningRule> warningRules = readWarningRulesFromMySQL();

        // 遍历预警规则并应用CEP模式
        for (WarningRule rule : warningRules) {
            Pattern<Event, ?> warningPattern = Pattern
                    .<Event>begin("first")
                    .subtype(Event.class);

            PatternStream<Event> patternStream = CEP.pattern(inputEventStream, warningPattern);

            DataStream<Tuple2<String, List<Event>>> warningStream = patternStream.select(
                    new PatternSelectFunction<Event, Tuple2<String, List<Event>>>() {
                        @Override
                        public Tuple2<String, List<Event>> select(Map<String, List<Event>> pattern) {
                            List<Event> events = pattern.get("second");
                            return Tuple2.of(rule.getRuleName(), events);
                        }
                    });

            // 将预警信息写回到Kafka
            warningStream.addSink(new FlinkKafkaProducer<>(
                    "warning-topic",
                    (KeyedSerializationSchema) new WarningSchema(),
                    properties,
                    FlinkKafkaProducer.Semantic.EXACTLY_ONCE
            ));
        }

        // 执行Flink Job
        env.execute("Real-time Warning System");
    }

    private static List<WarningRule> readWarningRulesFromMySQL() {
        // 从MySQL数据库中读取预警规则并返回
        // 这里需要根据实际情况实现相应的数据库操作代码
        // 示例代码：

        // Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "username", "password");
        // PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warning_rule");
        // ResultSet resultSet = preparedStatement.executeQuery();
        // List<WarningRule> warningRules = new ArrayList<>();
        // while (resultSet.next()) {
        //     WarningRule rule = new WarningRule();
        //     rule.setId(resultSet.getInt("id"));
        //     rule.setRuleName(resultSet.getString("rule_name"));
        //     rule.setEventType(resultSet.getString("event_type"));
        //     rule.setThreshold(resultSet.getInt("threshold"));
        //     rule.setWindowSize(resultSet.getInt("window_size"));
        //     rule.setWindowUnit(resultSet.getString("window_unit"));
        //     warningRules.add(rule);
        // }
        // resultSet.close();
        // preparedStatement.close();
        // connection.close();

        // return warningRules;
        return null;
    }
}

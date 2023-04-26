# user profile
create table if not exists `user_profile`
(
    `id`          bigint AUTO_INCREMENT,
    `name`        varchar(20) not null,
    `code`        varchar(20) not null,
    `phone`       varchar(20) default null,
    `email`       varchar(50) default null,
    `create_time` timestamp   not null,
    `update_time` timestamp   not null,
    `status`      varchar(20) not null,
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into `user_profile` (`name`, `code`, `phone`, `email`, `create_time`, `update_time`, `status`)
values ('admin', 'x-admin', '13202302171', 'admin@adp.com', '2023-02-17 00:12:15', '2023-02-17 00:12:15', 'normal');
insert into `user_profile` (`name`, `code`, `phone`, `email`, `create_time`, `update_time`, `status`)
values ('adp', 'r-adp', '13202302172', 'adp@adp.com', '2023-02-18 00:00:30', '2023-02-18 00:00:30', 'normal');
insert into `user_profile` (`name`, `code`, `phone`, `email`, `create_time`, `update_time`, `status`)
values ('manager', 'w-manager', '13202302173', 'manager@adp.com', '2023-02-18 10:05:30', '2023-02-18 10:05:30', 'normal');

# auth
create table if not exists `local_auth`
(
    `id`         bigint,
    `user_code`  varchar(20)  not null,
    `user_name`  varchar(20)  not null,
    `user_phone` varchar(20) default null,
    `user_email` varchar(50) default null,
    `password`   varchar(255) not null,
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into `local_auth` (`id`, `user_code`, `user_name`, `user_phone`, `user_email`, `password`)
values (10001, 'x-admin', 'admin', '13202302171', 'admin@adp.com', '21232f297a57a5a743894a0e4a801fc3');
insert into `local_auth` (`id`, `user_code`, `user_name`, `user_phone`, `user_email`, `password`)
values (10002, 'r-adp', 'adp', '13202302172', 'adp@adp.com', 'e219be8994730c07d8d6cafdbe9b6468');
insert into `local_auth` (`id`, `user_code`, `user_name`, `user_phone`, `user_email`, `password`)
values (10003, 'w-manager', 'manager', '13202302173', 'manager@adp.com', '1d0258c2440a8d19e716292b231e3190');

# datasource config for web
create table if not exists `datasource_config`
(
    `id`                 bigint,
    `datasource_name`    varchar(255),
    `username`           varchar(255),
    `password`           varchar(255),
    `host`               varchar(255),
    `port`               varchar(255),
    `url`                varchar(255),
    `_desc`              varchar(255),
    `driver`             varchar(255),
    `enable`             int,
    `data_provider_type` varchar(255),
    `extra`              varchar(255),
    primary key (`id`),
    UNIQUE INDEX index_datasourceName_uniq (datasource_name ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into `datasource_config` (`id`, `datasource_name`, `username`, `password`, `url`, `host`, `port`, `_desc`, `driver`, `enable`, `data_provider_type`, `extra`)
values (0006379, 'redis', null, '333', null, '43.143.23.30', '6379', 'redis', null, 1, '', '');
insert into `datasource_config` (`id`, `datasource_name`, `username`, `password`, `url`, `host`, `port`, `_desc`, `driver`, `enable`, `data_provider_type`, `extra`)
values (0019030, 'doris-luckin', 'idataservice', 'idataservice', 'jdbc:mysql://10.218.23.72:9030/ods_test', '10.218.23.72', '9030', 'doris in luckin', 'com.mysql.jdbc.Driver', 0,
        '', '{"sql-script-encoding":"UTF-8"}');

# event type
CREATE TABLE `event_type`
(
    `id`          BIGINT       NOT NULL,
    `event_type`  VARCHAR(255) NOT NULL,
    `enable`      BOOLEAN                              DEFAULT true,
    `_desc`       varchar(255)                         DEFAULT NULL,
    `create_time` DATETIME                             DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`),
    UNIQUE INDEX index_rule_name_uniq (event_type ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
insert into event_type (id, event_type, _desc)
values (0, 'normal', 'Normal');
insert into event_type (id, event_type, _desc)
values (1, 'npe', 'Null point exception');
insert into event_type (id, event_type, _desc)
values (2, 'uv', 'unique visit');
insert into event_type (id, event_type, _desc)
values (3, 'pv', 'page visit');
insert into event_type (id, event_type, _desc)
values (4, 'timeout', 'request timeout / response timeout');
insert into event_type (id, event_type, _desc)
values (5, 'weekend', 'on the weekend');
insert into event_type (id, event_type, _desc)
values (6, 'blacklist', 'app in blacklist');

# event record
CREATE TABLE `event_record`
(
    `id`            VARCHAR(255) NOT NULL,
    `reference_id`  VARCHAR(255) NOT NULL comment '来源id',
    `event_type`    VARCHAR(255) NOT NULL,
    `event_time`    DATETIME     NOT NULL comment '事件时间',
    `received_time` DATETIME     NOT NULL comment '接收时间',
    `sink_time`     DATETIME DEFAULT CURRENT_TIMESTAMP comment '落库时间',
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('b17ufsdjfqpk2e', 'kafka-app1', 'normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('92ejnd108udoaa', 'kafka-app1', 'npe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('091ah98hkjasbn', 'kafka-app2', 'normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('12szcwr24fds2e', 'flume-1', 'normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('q2edcsazf2e1da', 'flume-2', 'npe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('091ah98hkjasbn', 'eventlog104', 'normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('d12df4ygsd32rf', 'eventlog108', 'normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('23dfs23fdsf32f', 'game1-anti-log', 'npe', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('2d2r9ai0dqija9', 'game1-anti-log', 'normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('1d2d23f34tg6sw', 'game2-anti-log', 'normal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('cvbasdazxcq321', 'flume-1', 'uv', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('paodjd23f34tgw', 'flume-1', 'uv', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('p12asd23f34tgw', 'eventlog104', 'timeout', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('12swqecq3rase2', 'eventlog104', 'pv', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
insert into event_record(id, reference_id, event_type, event_time, received_time) value ('nm2d23f34tg6sw', 'eventlog104', 'pv', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

# event reference relation
CREATE TABLE `rule_reference_rel`
(
    `reference_id` VARCHAR(255) NOT NULL comment '来源id',
    `rule_name`    VARCHAR(255) NOT NULL comment 'rule'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into rule_reference_rel(reference_id, rule_name)
VALUES ('kafka-app1', 'npe_eq_1_immediately');
insert into rule_reference_rel(reference_id, rule_name)
VALUES ('kafka-app2', 'uv_gt_1000_10s');

# rules
CREATE TABLE `adp_rule`
(
    `id`           INT UNSIGNED AUTO_INCREMENT,
    `rule_name`    VARCHAR(255) NOT NULL,
    `event_type`   VARCHAR(255) NOT NULL,
    `window_size`  INT          NOT NULL comment '窗口大小，秒; -1及不限时间',
    `threshold`    INT          NOT NULL comment '阈值, -1及不限',
    `alert_config` VARCHAR(255) NOT NULL,
    `create_time`  DATETIME DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`),
    UNIQUE INDEX index_rule_name_uniq (rule_name ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into adp_rule (rule_name, event_type, window_size, threshold, alert_config)
VALUES ('npe_eq_1', 'npe', -1, -1, '{"way":"bot","botId": 1,"msg":"null point exception"}');
insert into adp_rule (rule_name, event_type, window_size, threshold, alert_config)
values ('uv_gt_1000_10s', 'uv', 10, 1000, '{"way":"email","email": "example@outlook.com","msg":"unique visit greater than 1000 times in 10 seconds"}');
insert into adp_rule (rule_name, event_type, window_size, threshold, alert_config)
VALUES ('refer_eq_blacklist', 'blacklist', -1, -1, '{"way":"bot","botId": 1, "msg":"app in blacklist"}');

# valid reference
CREATE TABLE `valid_reference`
(
    `id`           BIGINT       NOT NULL,
    `reference_id` VARCHAR(255) NOT NULL,
    `enable`       BOOLEAN      NOT NULL DEFAULT true,
    primary key (`id`),
    UNIQUE INDEX index_rule_name_uniq (reference_id ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into valid_reference(id, reference_id)
VALUES (1, 'kafka-app1');
insert into valid_reference(id, reference_id)
VALUES (2, 'kafka-app2');
insert into valid_reference(id, reference_id)
VALUES (3, 'flume-1');
insert into valid_reference(id, reference_id)
VALUES (4, 'flume-2');
insert into valid_reference(id, reference_id)
VALUES (5, 'eventlog104');
insert into valid_reference(id, reference_id)
VALUES (6, 'eventlog108');
insert into valid_reference(id, reference_id)
VALUES (7, 'game1-anti-log');
insert into valid_reference(id, reference_id)
VALUES (8, 'game2-anti-log');

# alert record
CREATE TABLE `alert_record`
(
    `id`         BIGINT       NOT NULL,
    `record_id`  VARCHAR(255) NOT NULL,
    `alert_way`  VARCHAR(255) NOT NULL,
    `alert_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

# reference
CREATE TABLE `adp_reference`
(
    `id`           INT UNSIGNED AUTO_INCREMENT,
    `reference_id` VARCHAR(30)  NOT NULL,
    `description`  VARCHAR(255) NOT NULL,
    `broker_addr`  VARCHAR(255) NOT NULL,
    `topic`        VARCHAR(255) NOT NULL,
    `create_time`  DATETIME DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into adp_reference(reference_id, description, broker_addr, topic)
values ('kafka-app1', 'kafka app1', 'localhost:9092', 'kafka_app_01');
insert into adp_reference(reference_id, description, broker_addr, topic)
values ('kafka-app2', 'kafka app2', 'localhost:9092', 'kafka_app_02');
insert into adp_reference(reference_id, description, broker_addr, topic)
values ('flume-1', 'flume 1', 'localhost:9092', 'flume_01');
insert into adp_reference(reference_id, description, broker_addr, topic)
values ('flume-2', 'flume 2', 'localhost:9092', 'flume_02');
insert into adp_reference(reference_id, description, broker_addr, topic)
values ('eventlog104', 'eventlog 104', 'localhost:9092', 'eventlog_104');
insert into adp_reference(reference_id, description, broker_addr, topic)
values ('eventlog108', 'eventlog 108', 'localhost:9092', 'eventlog108');
insert into adp_reference(reference_id, description, broker_addr, topic)
values ('game1-anti-log', 'game1 anti log', 'localhost:9092', 'game1_anti_log');
insert into adp_reference(reference_id, description, broker_addr, topic)
values ('game2-anti-log', 'game2 anti log', 'localhost:9092', 'game2_anti_log');

# bot addr
CREATE TABLE `adp_bot`
(
    `id`      INT UNSIGNED AUTO_INCREMENT,
    `name`    VARCHAR(255) NOT NULL,
    `webhook` VARCHAR(255) NOT NULL,
    primary key (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

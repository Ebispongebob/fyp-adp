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
values (1, 'npe', 'Null point exception');
insert into event_type (id, event_type, _desc)
values (2, 'uv', 'unique visit');
insert into event_type (id, event_type, _desc)
values (3, 'pv', 'page visit');
insert into event_type (id, event_type, _desc)
values (4, 'timeout', 'request timeout / response timeout');
insert into event_type (id, event_type, _desc)
values (5, 'weekend', 'on the weekend');

CREATE TABLE `event_record`
(
    `id`            BIGINT       NOT NULL,
    `reference_id`  VARCHAR(255) NOT NULL comment '来源id',
    `event_type`    VARCHAR(255) NOT NULL,
    `event_time`    DATETIME     NOT NULL comment '事件时间',
    `received_time` DATETIME     NOT NULL comment '接收时间',
    `sink_time`     DATETIME DEFAULT CURRENT_TIMESTAMP comment '落库时间',
    primary key (`id`),
    UNIQUE INDEX index_rule_name_uniq (event_type ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `event_reference_rel`
(
    `reference_id` VARCHAR(255) NOT NULL comment '来源id',
    `event_type`   VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into event_reference_rel(reference_id, event_type)
VALUES ('kafka-app1', 'npe');
insert into event_reference_rel(reference_id, event_type)
VALUES ('kafka-app1', 'timeout');
insert into event_reference_rel(reference_id, event_type)
VALUES ('kafka-app2', 'timeout');


CREATE TABLE `adp_rule`
(
    `id`            BIGINT                                      NOT NULL,
    `rule_name`     VARCHAR(255)                                NOT NULL,
    `event_type`    VARCHAR(255)                                NOT NULL,
    `event_id`      BIGINT                                      NOT NULL,
    `window_size`   INT                                         NOT NULL comment '窗口大小，秒; -1及不限时间',
    `threshold`     INT                                         NOT NULL comment '阈值',
    `condition`     ENUM ('EQUAL', 'GREATER_THAN', 'LESS_THAN') NOT NULL comment '条件',
    `alert_message` VARCHAR(255)                                NOT NULL,
    `enable`        BOOLEAN                              DEFAULT true,
    `create_time`   DATETIME                             DEFAULT CURRENT_TIMESTAMP,
    `update_time`   DATETIME ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`),
    UNIQUE INDEX index_rule_name_uniq (rule_name ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into adp_rule (id, rule_name, event_type, event_id, window_size, threshold, `condition`, alert_message)
VALUES (1, 'npe_eq_1_immediately', 'npe', 1, -1, 1, 'EQUAL', 'null point exception');
insert into adp_rule (id, rule_name, event_type, event_id, window_size, threshold, `condition`, alert_message)
values (2, 'uv_gt_1000_10s', 'uv', 2, 10, 1000, 'GREATER_THAN', 'unique visit greater than 1000 times in 10 seconds');

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

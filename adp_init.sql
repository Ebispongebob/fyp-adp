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
    `desc`               varchar(255),
    `driver`             varchar(255),
    `enable`             int,
    `data_provider_type` varchar(255),
    `extra`              varchar(255),
    primary key (`id`),
    UNIQUE INDEX index_datasourceName_uniq (datasource_name ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

insert into `datasource_config` (`id`, `datasource_name`, `username`, `password`, `url`, `host`, `port`, `desc`, `driver`, `enable`, `data_provider_type`, `extra`)
values (0006379, 'redis', null, '333', null, '43.143.23.30', '6379', 'redis', null, 1, '', '');

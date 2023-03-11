package com.fyp.adp.basedata.user.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: DatasourceConfig
 * @Author: ruitao.wei
 * @Date: 2023/3/10 15:25
 */
@Data
@Table(name = "datasource_config")
public class DatasourceConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long   id;
    @Column(name = "datasource_name")
    private String datasourceName;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "url")
    private String url;
    @Column(name = "host")
    private String host;
    @Column(name = "port")
    private String port;

    @Column(name = "desc")
    private String desc;
    @Column(name = "driver")
    private String driver;
    @Column(name = "enable")
    private Integer enable;
    @Column(name = "data_provider_type")
    private String dataProviderType;
    @Column(name = "extra")
    private String extra;
}

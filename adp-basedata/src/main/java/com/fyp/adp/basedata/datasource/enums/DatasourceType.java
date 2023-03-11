package com.fyp.adp.basedata.datasource.enums;

/**
 * @Author: ruitao.wei
 * @Date: 2023/3/10 15:38
 * @Description: 数据源类型
 */
public enum DatasourceType {
    REDIS("redis"),
    MYSQL("mysql"),
    DORIS("doris"),
    KAFKA("kafka");

    private final String _default;

    DatasourceType(String _default) {
        this._default = _default;
    }

    public String getDefault() {
        return _default;
    }
}

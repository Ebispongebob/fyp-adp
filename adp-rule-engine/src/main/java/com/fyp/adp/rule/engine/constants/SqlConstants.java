package com.fyp.adp.rule.engine.constants;

public interface SqlConstants {

    String url      = "\\{url}";
    String table    = "\\{table}";
    String username = "\\{username}";
    String password = "\\{password}";
    String driver   = "\\{driver}";

    String connectValidReferenceSql = "CREATE TABLE valid_reference (" +
            "  id BIGINT," +
            "  reference_id STRING," +
            "  enable BOOLEAN" +
            ") WITH (" +
            "  'connector' = 'jdbc'," +
            "  'url' = '{url}'," +
            "  'table-name' = '{table}'," +
            "  'username' = '{username}'," +
            "  'password' = '{password}'," +
            "  'driver' = '{driver}'" +
            ")";
    String queryValidReferenceSql = "SELECT * FROM valid_reference WHERE enable is true";
}

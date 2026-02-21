package com.example.techshop;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

public class DBConnection {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/tech_shop?useSSL=false&serverTimezone=UTC");
        ds.setUsername("labuser");
        ds.setPassword("root");
        ds.setInitialSize(5);
    }

    public static DataSource getDataSource() { return ds; }
}
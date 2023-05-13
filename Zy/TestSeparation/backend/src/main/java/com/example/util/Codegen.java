package com.example.util;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Codegen {

    public static void main(String[] args) {

        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/woniubookshop?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置只生成哪些表
        globalConfig.addGenerateTable("t_booktype");

        //设置 entity 的包名
        globalConfig.setEntityPackage("com.example.entity");

        //设置表前缀
        globalConfig.setTablePrefix("t_");

        //设置 entity 是否使用 Lombok
        globalConfig.setEntityWithLombok(true);

        //是否生成 mapper 类，默认为 false
        globalConfig.setMapperGenerateEnable(true);

        //设置 mapper 类的包名
        globalConfig.setMapperPackage("com.example.mapper");

        //可以单独配置某个列
//        ColumnConfig columnConfig = new ColumnConfig();
//        columnConfig.setColumnName("tenant_id");
//        columnConfig.setLarge(true);
//        columnConfig.setVersion(true);
//        globalConfig.addColumnConfig("account", columnConfig);

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
        System.out.println("已生成");
    }
}

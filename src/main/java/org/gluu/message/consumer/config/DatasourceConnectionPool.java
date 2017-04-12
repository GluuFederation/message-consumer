package org.gluu.message.consumer.config;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Created by eugeniuparvan on 4/12/17.
 */
public class DatasourceConnectionPool {
    public static void configureDatasource(DataSource dataSource){
        dataSource.setInitialSize(34);
        dataSource.setMaxActive(377);
        dataSource.setMaxIdle(233);
        dataSource.setMinIdle(89);
        dataSource.setTimeBetweenEvictionRunsMillis(34000);
        dataSource.setMinEvictableIdleTimeMillis(55000);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setValidationInterval(34000);
        dataSource.setTestOnBorrow(true);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(55);
    }
}

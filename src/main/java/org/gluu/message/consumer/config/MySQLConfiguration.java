package org.gluu.message.consumer.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.gluu.message.consumer.config.condition.MySQLCondition;
import org.gluu.message.consumer.config.util.Constants;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by eugeniuparvan on 1/27/17.
 */
@Profile(Constants.SPRING_PROFILE_PRODUCTION)
@Conditional(MySQLCondition.class)
@Configuration
public class MySQLConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.mysql.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.mysql.datasource")
    public DataSource dataSource() {
        return (DataSource) DataSourceBuilder.create().type(DataSource.class).build();
    }
}

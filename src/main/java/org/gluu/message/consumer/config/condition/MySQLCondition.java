package org.gluu.message.consumer.config.condition;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by eugeniuparvan on 1/27/17.
 */
public class MySQLCondition implements ProductionCondition {
    private static final String MYSQL = "mysql";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String database = conditionContext.getEnvironment().getProperty(DATABASE);
        return MYSQL.equalsIgnoreCase(database);
    }
}

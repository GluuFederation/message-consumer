package org.gluu.message.consumer.config.condition;

import org.springframework.context.annotation.Condition;

/**
 * Created by eugeniuparvan on 1/29/17.
 */
public interface ProductionCondition extends Condition {
    String DATABASE = "database";
    String ENABLE_LOGGING = "enable-logging";
}

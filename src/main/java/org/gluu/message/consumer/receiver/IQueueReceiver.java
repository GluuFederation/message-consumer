package org.gluu.message.consumer.receiver;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@FunctionalInterface
public interface IQueueReceiver {
    void receiveQueue(String message);
}

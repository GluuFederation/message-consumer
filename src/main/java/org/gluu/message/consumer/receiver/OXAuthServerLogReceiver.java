package org.gluu.message.consumer.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by eugeniuparvan on 10/26/16.
 */
@Component
public class OXAuthServerLogReceiver implements IQueueReceiver {

    @JmsListener(destination = "oxauth.server.logging")
    public void receiveQueue(String message) {
        System.out.println(message);
    }
}

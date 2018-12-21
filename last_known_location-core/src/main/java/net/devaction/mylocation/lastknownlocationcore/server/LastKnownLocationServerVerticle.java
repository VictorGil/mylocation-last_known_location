package net.devaction.mylocation.lastknownlocationcore.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationServerVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationServerVerticle.class);
    
    //Spring should set this value from the configuration
    private String address = "last_known_data.request";
    
    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();
        
        MessageConsumer<Buffer> consumer = eventBus.consumer(address, new LastKnownLocationServerHandler());
        
        consumer.completionHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("The " + LastKnownLocationServerHandler.class.getSimpleName() + 
                        " has been successfully registered ans it started listening for messages on " + address + " event bus address.");
            } else {
                log.error("FATAL: Registration of " + LastKnownLocationServerHandler.class.getSimpleName() + " has failed.");
            }
        });        
    } 
}


package net.devaction.mylocation.lastknownlocationcore.allinonevertxclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import net.devaction.mylocation.lastknownlocationcore.allinonevertxclient.buffer.TestBufferProvider1;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationTesterClientVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationTesterClientVerticle.class);
    
    //Spring should set this value from the configuration
    private String address = "last_known_location_request";
    
    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();
        
        Buffer buffer = TestBufferProvider1.provide();
        
        eventBus.send(address, buffer, new ResponseFromServerHandler());        
    }
}



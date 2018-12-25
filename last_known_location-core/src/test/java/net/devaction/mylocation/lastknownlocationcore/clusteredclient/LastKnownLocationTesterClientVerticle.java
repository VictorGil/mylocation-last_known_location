package net.devaction.mylocation.lastknownlocationcore.clusteredclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;

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
        
        LastKnownLocationRequest protoRequest = LastKnownLocationRequestProvider01.provide();
        Buffer buffer = Buffer.buffer(); 
        buffer.appendBytes(protoRequest.toByteArray());
        
        log.info("Going to send a message to " + address + " address.");
        eventBus.send(address, buffer, new ResponseFromServerHandler());        
    }
}

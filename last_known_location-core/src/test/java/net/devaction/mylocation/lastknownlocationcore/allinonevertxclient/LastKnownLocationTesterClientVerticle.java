package net.devaction.mylocation.lastknownlocationcore.allinonevertxclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationTesterClientVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationTesterClientVerticle.class);
    
    //Spring should set this value from the configuration
    private String address = "last_known_data.request";
    
    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();
        
        Buffer buffer = Buffer.buffer();
        final byte byteVar = (byte) 0B00000001;
        int intVar = byteVar;
        log.debug("bit literal 0B00000001 represents the following integer: " + intVar);
        buffer.appendByte((byte) byteVar);
        buffer.appendByte((byte) byteVar);
        buffer.appendByte((byte) byteVar);
        
        eventBus.send(address, buffer, new ResponseFromServerHandler());        
    }
}



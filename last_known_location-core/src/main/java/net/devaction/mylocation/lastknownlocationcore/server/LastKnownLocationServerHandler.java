package net.devaction.mylocation.lastknownlocationcore.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationServerHandler implements Handler<Message<Buffer>>{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationServerHandler.class);

    @Override
    public void handle(Message<Buffer> binaryMessage){
        
        Buffer buffer = binaryMessage.body();
        int numberOfBytesReceived = buffer.length();
        //Example thread name: vert.x-eventloop-thread-1
        log.trace("We have received a message which contains " + numberOfBytesReceived + " bytes.");        
    }
}



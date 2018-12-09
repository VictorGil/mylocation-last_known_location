package net.devaction.mylocation.lastknownlocationcore.netsocket.server;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class BufferHandler implements Handler<Buffer>{
    private static final Logger log = LoggerFactory.getLogger(BufferHandler.class);
    
    private final NetSocket netSocket;
    private final ConnectionIdentifier connectionIdentifier;
    
    public BufferHandler(NetSocket netSocket, ZonedDateTime connCreatedTime, long threadCreatorId){
        this.netSocket = netSocket;
        this.connectionIdentifier = new ConnectionIdentifier(connCreatedTime, threadCreatorId); 
        
        log.info("Started connection: " + connectionIdentifier);
    }
    
    @Override
    public void handle(Buffer buffer){
        String messageReceived = buffer.toString("UTF-8");
        
        //this is executed by vert.x-eventloop-thread-0 (for example)
        log.info(connectionIdentifier + " --> Received message: " + messageReceived);
        log.info(connectionIdentifier + " --> Going to reply to the message.");
        netSocket.write("Response from " + connectionIdentifier + " to the message: " + messageReceived, "UTF-8");
    }
}


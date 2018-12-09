package net.devaction.mylocation.lastknownlocationcore.netsocket.server;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.net.NetSocket;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class NetSocketConnectionHandler implements Handler<NetSocket>{
    private static final Logger log = LoggerFactory.getLogger(NetSocketConnectionHandler.class);
        
    @Override
    public void handle(NetSocket netSocket){
        ZonedDateTime connCreationTime = ZonedDateTime.now();
        long threadId = Thread.currentThread().getId();
        
        netSocket.handler(new BufferHandler(netSocket, connCreationTime, threadId));
    }
}


package net.devaction.mylocation.lastknownlocationcore.clusteredclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class ResponseFromServerHandler implements Handler<AsyncResult<Message<Buffer>>>{
    private static final Logger log = LoggerFactory.getLogger(ResponseFromServerHandler.class);

    @Override
    public void handle(AsyncResult<Message<Buffer>> asyncResult){
        if (asyncResult.succeeded()){
            Message<Buffer> message = asyncResult.result();
            Buffer buffer = message.body();
            log.debug("Received reply from the server, number of bytes in the body of the message: " + buffer.length());
            
            LastKnownLocationResponse response;
            try{
                response = LastKnownLocationResponse.parseFrom(buffer.getBytes());
            } catch (InvalidProtocolBufferException ex){
                log.error("Invalid response received from the server: " + ex, ex);
                return;
            }            
            log.info("Response received from the server: " + response);
            
        } else {   
            Throwable throwable = asyncResult.cause();
            log.error("The request could not be processed by the server: " + throwable, throwable);
        }
    }
}


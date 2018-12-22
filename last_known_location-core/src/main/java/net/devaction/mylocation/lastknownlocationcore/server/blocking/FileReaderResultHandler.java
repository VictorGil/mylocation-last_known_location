package net.devaction.mylocation.lastknownlocationcore.server.blocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class FileReaderResultHandler implements Handler<AsyncResult<LastKnownLocationResponse>>{
    private static final Logger log = LoggerFactory.getLogger(FileReaderResultHandler.class);
    
    //this should be set by the caller handler for every single request
    private Message<Buffer> messageToRespondTo;
    
    @Override
    public void handle(AsyncResult<LastKnownLocationResponse> result){
        if (result.succeeded()){
            LastKnownLocationResponse response = result.result();
            Buffer responseBuffer = Buffer.buffer(response.toByteArray());
            
            log.trace("Going to send response: " + response);
            
            messageToRespondTo.reply(responseBuffer);
        } else{
            log.error("Asynchronous execution on a working thread failed, cause: " + result.cause(), result.cause());
        }   
    }

    public void setMessageToRespondTo(Message<Buffer> messageToRespondTo){
        this.messageToRespondTo = messageToRespondTo;
    }
}


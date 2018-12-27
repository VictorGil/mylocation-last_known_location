package net.devaction.mylocation.lastknownlocationcore.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;
import net.devaction.mylocation.lastknownlocationapi.util.ProtoUtil;
import net.devaction.mylocation.lastknownlocationcore.server.blocking.LastLocationReader;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationServerWorkerHandler implements Handler<Message<Buffer>>{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationServerWorkerHandler.class);

    private ErrorBufferProvider errorBufferProvider;
    
    private LastLocationReader lastLocationReader;
    
    @Override
    public void handle(final Message<Buffer> binaryMessage){
        
        Buffer buffer = binaryMessage.body();
        int numberOfBytesReceived = buffer.length();
        //Example thread name: vert.x-eventloop-thread-1
        log.trace("We have received a message which contains " + numberOfBytesReceived + " bytes.");
        
        LastKnownLocationRequest request;
        try{
            request = LastKnownLocationRequest.parseFrom(buffer.getBytes());
        } catch (InvalidProtocolBufferException ex){
            log.error(ex.toString(), ex);
            binaryMessage.reply(errorBufferProvider.provide(ex.toString(), ex));
            return;
        }
        log.trace("Received " + LastKnownLocationRequest.class.getSimpleName() + 
                " :\n" + ProtoUtil.toString(request));
        
        LastKnownLocationResponse response = lastLocationReader.read();
        Buffer responseBuffer = Buffer.buffer(response.toByteArray());
        
        log.trace("Going to send response: " + ProtoUtil.toString(response));
        
        binaryMessage.reply(responseBuffer);
    }

    public void setErrorBufferProvider(ErrorBufferProvider errorBufferProvider){
        this.errorBufferProvider = errorBufferProvider;
    }

    public LastLocationReader getLastLocationReader(){
        return lastLocationReader;
    }

    public void setLastLocationReader(LastLocationReader lastLocationReader){
        this.lastLocationReader = lastLocationReader;
    }
}


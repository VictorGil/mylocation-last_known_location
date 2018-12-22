package net.devaction.mylocation.lastknownlocationcore.server;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.buffer.Buffer;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;
import net.devaction.mylocation.lastknownlocationapi.protobuf.Status;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class ErrorBufferProvider{
    private static final Logger log = LoggerFactory.getLogger(ErrorBufferProvider.class);
    
    public Buffer provide(String message, Exception ex){
        LastKnownLocationResponse.Builder responseBuilder = LastKnownLocationResponse.newBuilder();
        responseBuilder.setStatus(Status.FAILURE);
        String errorMessage = message + ". " + Arrays.toString(ex.getStackTrace());
        responseBuilder.setErrorMessage(errorMessage);
        
        Buffer buffer = Buffer.buffer();
        buffer.appendBytes(responseBuilder.build().toByteArray());
        
        return buffer;
    }
}

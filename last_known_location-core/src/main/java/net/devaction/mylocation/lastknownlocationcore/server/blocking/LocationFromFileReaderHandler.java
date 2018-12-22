package net.devaction.mylocation.lastknownlocationcore.server.blocking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LocationFromFileReaderHandler implements Handler<Future<LastKnownLocationResponse>>{
    private static final Logger log = LoggerFactory.getLogger(LocationFromFileReaderHandler.class);

    private LastLocationReader lastLocationReader;
    
    @Override
    public void handle(Future<LastKnownLocationResponse> future){
        log.trace("Going to read the latest known location from the file.");
        
        LastKnownLocationResponse response = lastLocationReader.read();
        future.complete(response);        
    }

    public void setLastLocationReader(LastLocationReader lastLocationReader){
        this.lastLocationReader = lastLocationReader;
    }
}


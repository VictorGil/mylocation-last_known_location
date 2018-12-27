package net.devaction.mylocation.lastknownlocationcore.clusteredclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationRequestProvider01{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationRequestProvider01.class);

    public static LastKnownLocationRequest provide(){
        
        LastKnownLocationRequest.Builder requestBuilder = LastKnownLocationRequest.newBuilder();
        requestBuilder.setTimestamp(System.currentTimeMillis() / 1000);
        LastKnownLocationRequest request = requestBuilder.build();
        
        log.info("Test request:\n" + request);
        return request;
    }
}


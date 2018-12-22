package net.devaction.mylocation.lastknownlocationcore.clusteredclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class TesterClientMainVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(TesterClientMainVerticle.class);
    
    @Override
    public void start() throws Exception{
        LastKnownLocationTesterClientMain.setVertx(vertx);
        
        vertx.deployVerticle(new LastKnownLocationTesterClientVerticle(), asyncResult -> {
            if (asyncResult.succeeded()){
                log.info("Successfully deployed " +  
                        LastKnownLocationTesterClientVerticle.class.getSimpleName() + ". Result: " + asyncResult.result());
            } else{
                log.error("FATAL: Error when trying to deploy " + LastKnownLocationTesterClientVerticle.class.getSimpleName());
                vertx.close(closeHandler -> {
                    log.info("vertx has been closed");
                });
            }
        });        
    }
}


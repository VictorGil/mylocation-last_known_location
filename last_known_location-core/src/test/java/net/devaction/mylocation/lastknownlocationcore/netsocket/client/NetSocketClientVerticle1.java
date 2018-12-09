package net.devaction.mylocation.lastknownlocationcore.netsocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetSocket;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class NetSocketClientVerticle1 extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(NetSocketClientVerticle1.class);
    
    @Override
    public void start() throws Exception {
        //this is for the sun.misc.SignalHandler.handle method to be able to shutdown Vert.x
        NetSocketClientMain1.setVertx(vertx);
        
        final int TCP_PORT = 9901;
        vertx.createNetClient().connect(TCP_PORT, "localhost", res -> {

            if (res.succeeded()) {
                NetSocket netSocket = res.result();
          
                netSocket.handler(buffer -> {
                    log.info("NetSocket client just received: " + buffer.toString("UTF-8"));
                });
                // Now send some data
                for (int i = 0; i < 10; i++) {
                    String str = "hello " + i + "\n";
                    log.info("Net client sending: " + str);
                    netSocket.write(str);
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                log.error("Failed to connect " + res.cause());
            }
        });
    }
}


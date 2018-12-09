package net.devaction.mylocation.lastknownlocationcore.netsocket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class NetSocketServerVerticle extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(NetSocketServerVerticle.class);
    
    @Override
    public void start() throws Exception {
        //this is for the sun.misc.SignalHandler.handle method to be able to shutdown Vert.x
        NetSocketServerMain.setVertx(vertx);
        
        NetServer netServer = vertx.createNetServer().connectHandler(new NetSocketConnectionHandler());
        int tcpPort = 9901;
        netServer.listen(tcpPort);
        //this is executed by vert.x-eventloop-thread-0 (for example)
        log.info("NetServer is listening on port " + tcpPort);
    }    
}


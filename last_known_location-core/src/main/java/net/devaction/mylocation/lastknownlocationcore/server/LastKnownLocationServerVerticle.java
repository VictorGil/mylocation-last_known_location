package net.devaction.mylocation.lastknownlocationcore.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import net.devaction.mylocation.lastknownlocationcore.config.AddressProvider;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationServerVerticle extends AbstractVerticle implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationServerVerticle.class);
    
    //Spring should set this value from the configuration
    //private String address = "last_known_location_request";
    private String address;
    private AddressProvider addressProvider;
    
    private LastKnownLocationServerHandler handler;

    @Override
    public void afterPropertiesSet() throws Exception{
        if (address == null)
            address = addressProvider.provideAddress();
        
        log.info("Vert.x event bus address: " + address);
    }
    
    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();
        
        
        MessageConsumer<Buffer> consumer = eventBus.consumer(address, handler);
        
        consumer.completionHandler(asyncResult -> {
            if (asyncResult.succeeded()) {
                log.info("The " + LastKnownLocationServerHandler.class.getSimpleName() + 
                        " has been successfully registered and it started listening for messages on " + address + " event bus address.");
            } else {
                log.error("FATAL: Registration of " + LastKnownLocationServerHandler.class.getSimpleName() + " has failed.");
            }
        });        
    }

    public void setAddressProvider(AddressProvider addressProvider){
        this.addressProvider = addressProvider;
    }

    public void setHandler(LastKnownLocationServerHandler handler){
        this.handler = handler;
    } 
}


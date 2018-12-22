package net.devaction.mylocation.lastknownlocationcore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class ConfigValuesProvider implements AddressProvider{
    private static final Logger log = LoggerFactory.getLogger(ConfigValuesProvider.class);
    
    //this contains the file path where we read the latest known location data from
    private static JsonObject lastKnownLocationServiceConfig;

    @Override
    public String provideAddress(){
        return lastKnownLocationServiceConfig.getString("event_bus_last_known_location_address");
    }
    
    //TO DO
    //@Override
    public String provideFilePath(){
        //TO DO: we need to add the base directory to the path
        return lastKnownLocationServiceConfig.getString("file_path");
    }
    
    public static void setLastKnownLocationServiceConfig(JsonObject lastKnownLocationServiceConfig){
        ConfigValuesProvider.lastKnownLocationServiceConfig = lastKnownLocationServiceConfig;
    }
}



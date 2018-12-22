package net.devaction.mylocation.lastknownlocationcore.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.JsonObject;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class ConfigValuesProvider implements AddressProvider, FilePathProvider{
    private static final Logger log = LoggerFactory.getLogger(ConfigValuesProvider.class);
    
    //this contains the file path where we read the latest known location data from
    private static JsonObject lastKnownLocationServiceConfig;
    
    private BaseDirProvider baseDirProvider;
    
    @Override
    public String provideAddress(){
        return lastKnownLocationServiceConfig.getString("event_bus_last_known_location_address");
    }
    
    @Override
    public String provideFilePath(){
        String relativeFilePath = lastKnownLocationServiceConfig.getString("file_path");
        String baseDir = baseDirProvider.provide();
        String filePath = baseDir + File.separator + relativeFilePath;
        log.info("Fiel path to read the latest known location: " + filePath);
        
        return filePath; 
    }
    
    public static void setLastKnownLocationServiceConfig(JsonObject lastKnownLocationServiceConfig){
        ConfigValuesProvider.lastKnownLocationServiceConfig = lastKnownLocationServiceConfig;
    }

    public void setBaseDirProvider(BaseDirProvider baseDirProvider){
        this.baseDirProvider = baseDirProvider;
    }
}


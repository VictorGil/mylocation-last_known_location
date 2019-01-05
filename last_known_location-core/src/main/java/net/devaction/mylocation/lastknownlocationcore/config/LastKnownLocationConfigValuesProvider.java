package net.devaction.mylocation.lastknownlocationcore.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devaction.mylocation.vertxutilityextensions.config.ConfigValuesProvider;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationConfigValuesProvider implements AddressProvider, FilePathProvider{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationConfigValuesProvider.class);
    
    //this is used to obtain the file path where we read the latest known location data from
    private ConfigValuesProvider configValuesProvider;
    
    private PersistenceBaseDirProvider persistenceBaseDirProvider;
    
    @Override
    public String provideAddress(){
        return configValuesProvider.getString("event_bus_last_known_location_address");
    }
    
    @Override
    public String provideFilePath(){
        String relativeFilePath = configValuesProvider.getString("file_path");
        String baseDir = persistenceBaseDirProvider.provide();
        String filePath = baseDir + File.separator + relativeFilePath;
        log.info("File path to read the latest known location: " + filePath);
        
        return filePath; 
    }
    
    public void setPersistenceBaseDirProvider(PersistenceBaseDirProvider baseDirProvider){
        this.persistenceBaseDirProvider = baseDirProvider;
    }

    public void setConfigValuesProvider(ConfigValuesProvider configValuesProvider){
        this.configValuesProvider = configValuesProvider;
    }
}


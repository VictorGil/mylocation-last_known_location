package net.devaction.mylocation.lastknownlocationcore.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class PersistenceBaseDirProviderImpl implements PersistenceBaseDirProvider{
    private static final Logger log = LoggerFactory.getLogger(PersistenceBaseDirProviderImpl.class);

    private static final String BASE_DIR = "base.dir";
    
    @Override
    public String provide(){
        String baseDir = System.getProperty(BASE_DIR);
        log.info("Base directory: " + baseDir);
        
        if (baseDir == null || baseDir.isEmpty()) {
            String errMessage = "FATAL: the " + BASE_DIR + " Java system property must be set";
            log.error(errMessage);
            throw new RuntimeException("errMessage");
        }
        return baseDir;
    }    
}


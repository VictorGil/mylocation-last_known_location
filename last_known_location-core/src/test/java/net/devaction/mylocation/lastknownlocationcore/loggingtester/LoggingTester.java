package net.devaction.mylocation.lastknownlocationcore.loggingtester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 * 
 * since December 2018
 * 
 */
public class LoggingTester{
    private static final Logger log = LoggerFactory.getLogger(LoggingTester.class);

    public static void main(String[] args) {
        log.debug("Example of log statement");    
    }
}


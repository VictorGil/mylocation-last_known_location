package net.devaction.mylocation.lastknownlocationcore.netsocket.server;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class ConnectionIdentifier{
    private static final Logger log = LoggerFactory.getLogger(ConnectionIdentifier.class);
    
    public static final String DATE_PATTERN = "EEEE dd-MMM-yyyy HH:mm:ss.SSSZ";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);
    
    private final ZonedDateTime connCreatedTime;
    private final long threadCreatorId;
    
    public ConnectionIdentifier(ZonedDateTime connCreatedTime, long threadCreatorId){
        this.connCreatedTime = connCreatedTime;
        this.threadCreatorId = threadCreatorId;
    }

    @Override
    public String toString(){
        return "ConnectionIdentifier [connCreatedTime=" + connCreatedTime.format(FORMATTER)  + ", threadCreatorId=" + threadCreatorId + "]";
    }
}


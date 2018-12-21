package net.devaction.mylocation.lastknownlocationcore.allinonevertxclient.buffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.buffer.Buffer;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class TestBufferProvider1{
    private static final Logger log = LoggerFactory.getLogger(TestBufferProvider1.class);
    
    public static Buffer provide(){
        Buffer buffer = Buffer.buffer();
        final byte byteVar = (byte) 0B00000001;
        int intVar = byteVar;
        log.debug("bit literal 0B00000001 represents the following integer: " + intVar);
        buffer.appendByte((byte) byteVar);
        buffer.appendByte((byte) byteVar);
        buffer.appendByte((byte) byteVar);
        
        return buffer;
    }
}



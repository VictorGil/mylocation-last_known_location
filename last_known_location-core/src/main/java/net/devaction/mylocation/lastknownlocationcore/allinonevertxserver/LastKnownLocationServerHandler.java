package net.devaction.mylocation.lastknownlocationcore.allinonevertxserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationServerHandler implements Handler<Message<Buffer>>{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationServerHandler.class);

    @Override
    public void handle(Message<Buffer> binaryMessage){
        Buffer buffer = binaryMessage.body();
        int numberOfBytesReceived = buffer.length();
        log.debug("We have received a message which contains " + numberOfBytesReceived + " bytes.");
        
        //Test reply message:
        final byte byteVar = (byte) 0B00000010;
        int intVar = byteVar;
        log.debug("bit literal 0B00000010 represents the following interger: " + intVar);
        
        final Buffer responseBuffer = Buffer.buffer();
        responseBuffer.appendByte((byte) byteVar);
        responseBuffer.appendByte((byte) byteVar);
        responseBuffer.appendByte((byte) byteVar);
        responseBuffer.appendByte((byte) byteVar);
        
        binaryMessage.reply(responseBuffer);
    }
}



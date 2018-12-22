package net.devaction.mylocation.lastknownlocationcore.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;
import net.devaction.mylocation.lastknownlocationcore.server.blocking.FileReaderResultHandler;
import net.devaction.mylocation.lastknownlocationcore.server.blocking.LocationFromFileReaderHandler;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastKnownLocationServerHandler implements Handler<Message<Buffer>>{
    private static final Logger log = LoggerFactory.getLogger(LastKnownLocationServerHandler.class);

    private ErrorBufferProvider errorBufferProvider;
    private LocationFromFileReaderHandler fileReaderBlockingHandler;
    private FileReaderResultHandler fileReaderResultHandler;
    
    //we need this reference to be able to execute blocking code on one of the worker threads
    private Vertx vertx;
    
    @Override
    public void handle(Message<Buffer> binaryMessage){
        
        Buffer buffer = binaryMessage.body();
        int numberOfBytesReceived = buffer.length();
        //Example thread name: vert.x-eventloop-thread-1
        log.trace("We have received a message which contains " + numberOfBytesReceived + " bytes.");
        
        try{
            LastKnownLocationRequest.parseFrom(buffer.getBytes());
        } catch (InvalidProtocolBufferException ex){
            log.error(ex.toString(), ex);
            binaryMessage.reply(errorBufferProvider.provide(ex.toString(), ex));
            return;
        }
        
        //As per what I understood, this should be a safe thing to do
        //https://github.com/vietj/advanced-vertx-guide/blob/master/src/main/asciidoc/Demystifying_the_event_loop.adoc
        fileReaderResultHandler.setMessageToRespondTo(binaryMessage);
        
        //Execute blocking code on a worker thread
        vertx.executeBlocking(fileReaderBlockingHandler, fileReaderResultHandler);
    }

    public void setVertx(Vertx vertx){
        this.vertx = vertx;
    }

    public void setFileReaderBlockingHandler(LocationFromFileReaderHandler fileReaderBlockingHandler){
        this.fileReaderBlockingHandler = fileReaderBlockingHandler;
    }

    public void setErrorBufferProvider(ErrorBufferProvider errorBufferProvider){
        this.errorBufferProvider = errorBufferProvider;
    }

    public void setFileReaderResultHandler(FileReaderResultHandler fileReaderResultHandler){
        this.fileReaderResultHandler = fileReaderResultHandler;
    }
}


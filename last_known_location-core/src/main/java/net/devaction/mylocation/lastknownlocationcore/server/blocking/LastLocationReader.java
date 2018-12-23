package net.devaction.mylocation.lastknownlocationcore.server.blocking;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.google.protobuf.InvalidProtocolBufferException;

import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;
import net.devaction.mylocation.lastknownlocationapi.protobuf.Status;
import net.devaction.mylocation.lastknownlocationcore.config.FilePathProvider;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class LastLocationReader implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(LastLocationReader.class);
    
    private String filePath;
    private FilePathProvider filePathProvider;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        if (filePath == null)
            filePath = filePathProvider.provideFilePath();
        
        log.info("File path to read the last know location data: " + filePath);
    }
    
    //this should be executed on a worker thread.
    public LastKnownLocationResponse read(){
        File file = new File(filePath);
        LastKnownLocationResponse.Builder failureResponseBuilder = LastKnownLocationResponse.newBuilder();
        
        if(!file.exists()){ 
           failureResponseBuilder.setStatus(Status.FAILURE);
           String errMessage = "The file " + filePath + " does not exist.";
           log.error(errMessage);
           failureResponseBuilder.setErrorMessage(errMessage);
           
           return failureResponseBuilder.build();
        }
        
        if (!file.isFile()){
            failureResponseBuilder.setStatus(Status.FAILURE);
            String errMessage = "FATAL: " + filePath + " is not a file.";
            failureResponseBuilder.setErrorMessage(errMessage);
            return failureResponseBuilder.build();
        }
        
        Path path = file.toPath();
        FileChannel fileChannel = null;        
        try{
            fileChannel = FileChannel.open(path, StandardOpenOption.READ);
        } catch (IOException ex){
            String errMessage = "Unable to open file " + filePath + " for reading: " 
                    + ex;
            log.error(errMessage, ex);
            failureResponseBuilder.setStatus(Status.FAILURE);
            failureResponseBuilder.setErrorMessage(errMessage);
            return failureResponseBuilder.build();
        }
        
        FileLock lock = null;
        int attempts = 0;
        while (lock == null && attempts < 2) {
            if (attempts == 1) {
                log.warn("Unable to acquire lock, sleeping for half a second and then trying again");
                try{
                    Thread.sleep(500);
                } catch(InterruptedException ex){
                    log.error(ex.toString(), ex);                
                }
            }
            try{
                lock  = fileChannel.tryLock(0, Long.MAX_VALUE, true);
            } catch (OverlappingFileLockException | IOException ex) {
                String errMessage = "Unable to obtain shared lock on file " + filePath 
                    + " " + ex;
                log.error(errMessage, ex);
                failureResponseBuilder.setStatus(Status.FAILURE);
                failureResponseBuilder.setErrorMessage(errMessage);
                return failureResponseBuilder.build();
            }
            attempts++;
        }
        
        if (lock == null){
            String errMessage = "Unable to acquire read/shared lock after two attempts on " + filePath;
            log.error(errMessage);
            failureResponseBuilder.setStatus(Status.FAILURE);
            failureResponseBuilder.setErrorMessage(errMessage);
            return failureResponseBuilder.build();    
        }
            
        byte[] bytes = null;
        try{
            bytes = Files.readAllBytes(path);
        } catch (IOException ex){
            String errMessage = "Unable to read bytes from file " + filePath + " " + ex;
            log.error(errMessage, ex);
            failureResponseBuilder.setStatus(Status.FAILURE);
            failureResponseBuilder.setErrorMessage(errMessage);
            return failureResponseBuilder.build();
        }
        
        LastKnownLocationResponse successResponse = null;
        try{
            successResponse = LastKnownLocationResponse.parseFrom(bytes);
        } catch (InvalidProtocolBufferException ex){
            String errMessage = "Wrong binary format in file " + filePath + 
                    " " + ex;
            log.error(errMessage, ex);
            failureResponseBuilder.setStatus(Status.FAILURE);
            failureResponseBuilder.setErrorMessage(errMessage);
            return failureResponseBuilder.build();            
        }        
        
        finally {
            log.trace("Running finally block");
            if (lock != null) {
                log.trace("Going to release the lock");
                try{
                    lock.release();
                } catch (IOException ex){
                    log.error("Error when releasing the lock: " + ex.toString(), ex);                    
                }
            }
            if (fileChannel != null){
                log.trace("Going to close the file channel");
                try{
                    fileChannel.close();
                } catch (IOException ex){
                    log.error("Error when closing the file channel: " + ex.toString(), ex);
                }
            }
        }
        return successResponse;
    }

    public void setFilePathProvider(FilePathProvider filePathProvider){
        this.filePathProvider = filePathProvider;
    }
}


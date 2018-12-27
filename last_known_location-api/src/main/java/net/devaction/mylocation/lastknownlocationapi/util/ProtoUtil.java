package net.devaction.mylocation.lastknownlocationapi.util;

import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationRequest;
import net.devaction.mylocation.lastknownlocationapi.protobuf.LastKnownLocationResponse;

/**
 * @author Víctor Gil
 *
 * since December 2018
 */
public class ProtoUtil{

    public static String toString(LastKnownLocationRequest request){        
        StringBuilder stringBuilder = new StringBuilder(request.toString());
        
        if (request.hasTimestamp())
            stringBuilder.append("timestamp (string): ")
            .append(DateUtil.getDateString(request.getTimestamp())).append("\n");       
        
        return stringBuilder.toString();
    }      
    
    public static String toString(LastKnownLocationResponse response){        
        StringBuilder stringBuilder = new StringBuilder(response.toString());
        
        if (response.hasTimeChecked())
            stringBuilder.append("timeChecked (string): ")
            .append(DateUtil.getDateString(response.getTimeChecked())).append("\n"); 
    
        
        if (response.hasTimeMeasured())
            stringBuilder.append("timeMeasured (string): ")
            .append(DateUtil.getDateString(response.getTimeMeasured())).append("\n");
        
        return stringBuilder.toString();
    }    
}

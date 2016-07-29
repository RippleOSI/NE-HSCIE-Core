/*
 * Copyright 2015 Ripple OSI
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.hscieripple.common.util;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

/**
 */
public final class HSCIEDateFormatter {

    private HSCIEDateFormatter() {
        // Prevent construction
    }

    public static Date toDate(String input, String systemFrom) {
    	
    	//Define incoming date format by system
        if (input == null) {
            return null;
        }
        String format = "dd.MM.yyyy";
        
        switch (systemFrom){
        case "Liquid Logic":
        case "PCS":
        case "Emis Community":
        	format = "yyyy-MM-dd HH:mm:ss";   
        	break;
        case "PCS Time":
        case "PCS long Time":
        	format = "yyyy-MM-dd HH:mm:ss";   
        	break;
        case "PCS alerts":
        	format = "yyyy-MM-dd HH:mm";   
        	break;
        case "Sunquest ICE":
        	format = "yyyyMMddHHmmss";   
        	break;
        case "Meditech":
        	format = "yyyyMMddHHmm";   
        	break;
        case "RIO":
        	if (input.contains("-")){
            	format = "yyyy-MM-dd HH:mm:ss";        	
            } else {
            	format = "dd/MM/yyyy HH:mm";        	
            } 
        	break;
        }
        
        //Return value of formatted String
        try {
            return DateUtils.parseDate(input, format);
        } catch (ParseException ignore) {
            return null;
        }
    }
    
}

   

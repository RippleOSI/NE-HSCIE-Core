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
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
        
        if (systemFrom.equals("RIO") && input.contains("-")){
        	format = "yyyy-MM-dd HH:mm:ss";        	
        }
        
        if (systemFrom.equals("RIO") && input.contains("/")){
        	format = "dd/MM/yyyy HH:mm";        	
        }
        
        if(systemFrom.equals("Liquid Logic")){
        	format = "yyyy-MM-dd HH:mm:ss";
        }
        
        if(systemFrom.equals("PCS")){
        	format = "yyyy-MM-dd HH:mm:ss";
        }
        
        if(systemFrom.equals("PCS Time")){
        	format = "HH:mm:ss";
        }
                
        if(systemFrom.equals("PCS long Time")){
        	format = "HH:mm:ss";
        }
        
        if(systemFrom.equals("Emis Community")){
        	format = "yyyy-MM-dd HH:mm:ss";
        }
        
        //Return value of formatted String
        try {
            return DateUtils.parseDate(input, format);
        } catch (ParseException ignore) {
            return null;
        }
    }
    
}

   

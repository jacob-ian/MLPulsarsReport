package com.jacobianmatthews.pulsarvalidator.utils;

import java.io.File;

/**
 * This class contains the common utility functions across the program.
 * 
 * @author Jacob Ian Matthews
 * @version 1.0, 29/05/2020
 */
public class Utilities {

    /** CONSTRUCTOR */
    public Utilities(){
        // Empty
    }
    
    /**
     * This method validates that a file exists.
     * @param path A string containing the path to a file.
     * @return True if it is a valid, existing file.
     */
    public static boolean isFile(String path)
    {
        // Trim the whitespace
        String trimmed = path.trim();

        // Create the file
        File file = new File(trimmed);

        // Validate the file
        if(file.isFile()){
            
            // Return true
            return true;
        } else {

            // Return false;
            return false;

        }

    }
}
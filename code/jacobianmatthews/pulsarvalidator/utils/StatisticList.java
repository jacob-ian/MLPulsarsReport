package com.jacobianmatthews.pulsarvalidator.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A list to hold the classifier's statistics.
 * 
 * @author Jacob Ian Matthews
 * @version 1.0, 29/05/2020
 */
public class StatisticList {

    /** VARIABLES */
    private List<Statistic> list;

    /** CONSTRUCTOR */
    public StatisticList() {

        // Create the list
        this.list = new ArrayList<Statistic>();

    }

    /**
     * 
     * @param name
     * @param value
     */
    public void add(String name, int value)
    {  
        // Create a statistic
        Statistic stat = new Statistic(name, value);

        // Add it to the list
        this.list.add(stat);
    }

    /**
     * Get the value of a statistic by its name
     * @param name String containing the name of the statistic
     * @return integer value of statistic
     */
    public int getValueByName(String name)
    {
        // Loop through the list
        for(Statistic stat: this.list)
        {
            if( stat.getName().equals(name) ){

                // Return the value
                return stat.getValue();
            }
        }

        // Couldn't find it, return 0
        return 0;
    }

}
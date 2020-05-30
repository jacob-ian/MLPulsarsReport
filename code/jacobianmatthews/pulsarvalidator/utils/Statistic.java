package com.jacobianmatthews.pulsarvalidator.utils;

/**
 * This is a data object to hold a statistic for the classifier.
 * 
 * @author Jacob Ian Matthews
 * @version 1.0, 29/05/2020
 */
public class Statistic {

    /** VARIABLES */
    private String name;
    private int value;

    /** CONSTRUCTOR */
    public Statistic(String name, int value) {

        // Get the variables
        this.name = name;
        this.value = value;
        
    }

    /** GETTERS AND SETTERS */
    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue(){

        return this.value;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
    
}
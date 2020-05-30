package com.jacobianmatthews.pulsarvalidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jacobianmatthews.pulsarvalidator.utils.StatisticList;
import com.jacobianmatthews.pulsarvalidator.utils.Utilities;

/**
 * This class contains the Pulsar Classifier validation mode of the program. It
 * will compare the output files of the Pulsar Classifier to the true list of
 * pulsars and output statistics.
 * 
 * @author Jacob Ian Matthews
 * @version 1.0, 29/05/2020
 */
public class ValidationMode {

    /** VARIABLES */
    private String positiveOutputPath;

    private String negativeOutputPath;

    private List<String> pulsars;

    /** CONSTRUCTOR */
    /**
     * Instantiates the Validation mode.
     * 
     * @param pulsarListPath     A string containing the path to the list of pulsars
     *                           in the data set.
     * @param postiveOutputPath  A string containing the path to the positive output
     *                           file of the classifier.
     * @param negativeOutputPath A string containing the path to the negative output
     *                           file of the classifier.
     * @throws IOException
     */
    public ValidationMode(String pulsarListPath, String positiveOutputPath, String negativeOutputPath)
            throws IOException {

        // Assign the variables
        this.positiveOutputPath = positiveOutputPath;
        this.negativeOutputPath = negativeOutputPath;

        // Validate the filepaths
        if (!Utilities.isFile(pulsarListPath)) {
            // Print an error and end the program
            System.out.println("The path given to the pulsar list is not valid.\nExiting program.");
            System.exit(0);
        }

        if (!Utilities.isFile(positiveOutputPath)) {
            // Print an error and end the program
            System.out.println("The path given to the positive classifier output is not valid.\nExiting program.");
            System.exit(0);
        }

        if (!Utilities.isFile(negativeOutputPath)) {
            // Print an error and end the program
            System.out.println("The path given to the negative classifier output is not valid.\nExiting program.");
            System.exit(0);
        }

        // Get the list of pulsars
        this.pulsars = Files.readAllLines(Paths.get(pulsarListPath));

    };

    /**
     * This validates the classifier's outputs against the list of pulsars.
     * 
     * @return String containg the validation statistics.
     * @throws IOException
     */
    public String validate() throws IOException 
    {
        // Create a list of the statistics
        StatisticList statistics = new StatisticList();

        // Add the true and false positive statistics to the list
        StatisticList positiveStatistics = processPositive(statistics);

        // Add the true and false negative statistics to the list
        StatisticList allStatistics = processNegative(positiveStatistics);

        // Get the statistics
        int TP = allStatistics.getValueByName("TruePositives");
        int FP = allStatistics.getValueByName("FalsePositives");
        int TN = allStatistics.getValueByName("TrueNegatives");
        int FN = allStatistics.getValueByName("FalseNegatives");

        // To find the number of pulsars without generating a new list from the output data of 
        // PulsarFeatureLab, we can just add the TruePositive count with the FalseNegative count.
        // The same can be applied for non-pulsars
        int pulsarCount = TP + FN;
        int nonpulsarCount = TN + FP;

        int pulsarsDetected = TP + FP;
        int nonpulsarsDetected = TN + FN;

        // Output the statistics as a string
        String output = "Number of Pulsars: " + pulsarCount;
        output+= "\nPulsars Detected: " + pulsarsDetected;
        output+= "\nTrue Positives: " + TP;
        output+= "\nFalse Positives: " + FP;
        output+= "\n\nNumber of Non-Pulsars: " + nonpulsarCount;
        output+= "\nNon-Pulsars Detected: " + nonpulsarsDetected;
        output+= "\nTrue Negatives: " + TN;
        output+= "\nFalse Negatives: " + FN;

        // Return the output
        return output;

    }

    /**
     * This will create the true positive and false positive lists and output their
     * results
     * 
     * @return a StatisticList object containing the postiive statistics
     * @throws IOException
     */
    private StatisticList processPositive(StatisticList statistics) throws IOException
    {
        // Create the True Positive list
        List<String> truePositive = new ArrayList<String>();

        // Create the False Positive list
        List<String> falsePositive = new ArrayList<String>();

        // Get the positive output file list
        List<String> classifier = Files.readAllLines(Paths.get(this.positiveOutputPath));

        // Check each line of the classifier's output
        for(String classification: classifier)
        {
            // Get the filename of the classification
            String name = classification.substring(classification.lastIndexOf("/")+1, classification.length());

            // Check if it is in the list of pulsars
            // Create search flag and index
            boolean found = false;
            int i = 0;

            // Loop through the list
            while(!found){

                // Check if the classified pulsar is in the real list
                if( this.pulsars.get(i).equals(name) ){

                    // Add pulsar to the true positive list
                    truePositive.add(name);

                    // Set the flag to found
                    found = true;

                } else {
                    // Increment loop 
                    i++;
                }

                // Check if we have reached the end of the list
                if ( !(i < this.pulsars.size()) ){

                    // Add this pulsar to the false positive list
                    falsePositive.add(name);
                    
                    // End the loop
                    found = true;

                }
            }
        }

        // Count the number of true and false positives
        int truePositiveCount = truePositive.size();
        int falsePositiveCount = falsePositive.size();

        // Add the statistics to the list
        statistics.add("TruePositives", truePositiveCount);
        statistics.add("FalsePositives", falsePositiveCount);

        // Return the list
        return statistics;

    }

    /**
     * This creates the false and true negative lists and output the results of
     * their statistics
     * 
     * @return a StatisticList containing the negative statistics
     * @throws IOException
     */
    private StatisticList processNegative(StatisticList statistics) throws IOException
    {
        // Create the False Negative List
        List<String> falseNegative = new ArrayList<String>();

        // Create the True Negative List
        List<String> trueNegative = new ArrayList<String>();

        // Get the negative output list
        List<String> classifier = Files.readAllLines(Paths.get(this.negativeOutputPath));

        // Loop through the negative output file
        for(String classification: classifier)
        {
            // Get the filename of the candidate
            String name = classification.substring(classification.lastIndexOf("/")+1,classification.length());

            // Create the loop variables
            boolean found = false;
            int i = 0;
            while(!found)
            {
                // Check if the name is in the list of pulsars
                if( this.pulsars.get(i).equals(name) ){
                    // Detected a false negative, therefore add to the list
                    falseNegative.add(name);

                    // End loop
                    found = true;
                } else {
                    // increment loop
                    i++;
                }

                // Check if the end of the pulsars list has been reached
                if ( !(i < this.pulsars.size() )){

                    // Add this classification to the true negatives list
                    trueNegative.add(name);

                    // End loop
                    found = true;
                }
            }
        }

        // Create the statistics
        int trueNegativeCount = trueNegative.size();
        int falseNegativeCount = falseNegative.size();
        
        // Add the statistics to the list
        statistics.add("TrueNegatives", trueNegativeCount);
        statistics.add("FalseNegatives", falseNegativeCount);

        // Return the statistics list
        return statistics;

    }


}
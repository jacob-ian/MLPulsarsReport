package com.jacobianmatthews.pulsarvalidator;

import java.io.IOException;

/**
 * This program is a validator for the machine learning software
 * PulsarClassifier.
 * 
 * This program will generate a list of candidate filenames from an output file
 * of PulsarFeatureLab. It will then be possible to filter the list of known
 * pulsars in the original dataset to the pulsars that had successful feature
 * extraction. Finally, the program can compare the list of known pulsars in the
 * dataset to the pulsars classified in the classifier, and produce comparison
 * statistics.
 * 
 * @author Jacob Ian Matthews
 * @version 1.0, 28/05/2020
 */
public class PulsarValidator {

    /**
     * 
     * VARIABLES
     * 
     */

    // Pulsar list mode
    private static boolean list = false;

    // Pulsar classification output validation mode
    private static boolean validation = false;

    // String containaing the path to the PulsarFeatureLab output file
    private static String pflOutput;

    // String containing the path to the list of pulsars
    private static String pulsarList;

    // String containing the path to the positive classifier output
    private static String positiveClassifier;

    // String containing the path to the negative classifier output
    private static String negativeClassifier;

    public static void main(String[] args) throws IOException {
        // Get the input arguments
        getCliVariables(args);

        // Check to see which flag has been given
        if (list) {
            // LIST MODE CHOSEN
            // Print a message
            System.out.println("Pulsar list generation mode chosen.\n");

            // Create a list mode instance
            System.out.println("This feature is under development. Exiting program.");
            System.exit(0);

        } else if (validation) {

            // VALIDATION MODE CHOSEN
            // Print a message
            System.out.println("\nPulsar classifier validation mode chosen.");

            // Print the location of the pulsar list
            System.out.println("\nPulsar list location: "+pulsarList);

            // Print the location of the positive output file
            System.out.println("Classifier positive output location: "+positiveClassifier);

            // Print the location of the positive output file
            System.out.println("Classifier negative output location: "+negativeClassifier);

            // Create the validation mode instance
            ValidationMode validationMode = new ValidationMode(pulsarList, positiveClassifier, negativeClassifier);

            // Get the output string
            String output = validationMode.validate();

            // Output the string and then exit the program
            System.out.println("\nPulsar classifier validated successfully.\n");
            System.out.println(output);

            System.exit(0);

        } else if (list && validation) {

            // Display error that you can only do one thing at once
            System.out.println("'-l' and '-v' arguments entered. Please choose one mode only. \n");

            // Exit the application
            System.exit(0);

        } else if (!list && !validation) {

            // Display error that you need to pick a flag
            System.out.println("Please choose a mode by using this program with a '-l' or '-v' argument.\n");

            // Exit the application
            System.exit(0);
        }

    }

    /**
     * This function checks the command-line input arguments to decide how the
     * program should be ran.
     * 
     * @param args
     */
    private static void getCliVariables(String[] args)
    {

        // Loop through arguments for the list and compare flags
        for(int i = 0; i<args.length; i++)
        {
            // Define the current argument
            String argument = args[i];

            // The list flag
            if( argument.equals("-l") ){

                // Set the list mode boolean to true
                list = true;

                // Get the next argument (the PulsarFeatureLab output file)
                pflOutput = args[i+1];

                // Get the input pulsar list
                pulsarList = args[i+2];

            // Check for compare flag
            } else if( argument.equals("-v") ){

                // Set the compare mode boolean to true
                validation = true;

                // Get the list of pulsars
                pulsarList = args[i+1];

                // Get the positive classifier output file
                positiveClassifier = args[i+2];

                // Get the negative classifier output file
                negativeClassifier = args[i+3];

            } 
        }

    }
}

/*
 * Course:     CS 1011 - 71
 * Fall 2019
 * File header contains class WavGenerator
 * Name:       fassg
 * Created:    11/30/2019
 */
package msoe.fassg.lab01;

import java.util.ArrayList;
import java.util.Scanner;
import us.msoe.taylor.audio.WavFile;

/**
 * Course: CS 1011 - 71
 * Fall 2019
 * WavGenerator purpose: will create wav files for use in lab 1 which manipulates wav audio files
 *
 * @author fassg
 * @version created on 11/30/2019 at 8:21 PM
 */
public class WavGenerator {
    //global vars
    private static Scanner in = new Scanner(System.in);

    /**
     * the number of channels to be used in a single channel audio file
     */
    public static final int NUM_CHANNELS_MONO = 1;

    /**
     * the number of channels to be used in a dual channel or stereo audio file
     */
    public static final int NUM_CHANNELS_STEREO = 2;

    /**
     * the number of frames to use in created audio files
     */
    public static final int NUM_FRAMES = 8000;

    /**
     * the number of valid bits in created audio files
     */
    public static final int VALID_BITS = 8;

    /**
     * the sample rate to be used in created audio files
     * because created files are one second in length
     * this value should be the same as NUM_FRAMES
     */
    public static final int SAMPLE_RATE = 8000;

    /**
     * this method will print out a selection menu to the user
     * so that they can decide what they would like to do.
     */
    public void printMenu(){
        System.out.format("%s\n%s\n%s\n%s\n%s\n",
                "Options Menu",
                "0: Exit",
                "1: Reverse Wave File",
                "2: Create Wave File Of Specified Tone",
                "3: Create Stereo Wave File Of Two Specified Tones");
    }

    /**
     * this method will check if a string contains only digits
     * @param input the string to be checked
     * @return true if number, false if not
     */
    public boolean isValid(String input){
        for(int i = 0; i < input.length(); i++){
            if(!Character.isDigit(input.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * this method will return a valid integer value for the user choice in the menu selection
     * Should never fail here.
     * @return integer menu choice between 0 and 3 inclusive
     */
    public int getUserMenuSelection(){
        String input = "";
        boolean loop = true;
        while (loop) {
            System.out.print("Selection: ");
            input = in.nextLine();
            if (isValid(input) && (input.equals("0") || input.equals("1") ||
                    input.equals("2") || input.equals("3"))) {
                System.out.println("Valid Input");
                loop = false;
            } else {
                System.out.println("Invalid Input Detected!");
            }
        }
        return Integer.parseInt(input);
    }

    /**
     * this method will print out the options menu to the user and then return a user input
     * @return the users choice for what they would like to do.
     */
    public int promptUser(){
        printMenu();
        return getUserMenuSelection();
    }

    /**
     * will prompt the user to enter a file name and will then
     * read that filename and return it to the program
     * @return user-inputted filename
     */
    public String getFileNameFromUser(){
        System.out.print("Please Enter File Name: ");
        return in.nextLine();
    }

    /**
     * will prompt the user to enter a frequency and will then
     * read that frequency and return it to the program
     * makes sure that the input entered is valid
     * @return user-inputted frequency
     */
    public double getFrequencyFromUser(){
        String input = "";
        boolean loop = true;
        while (loop) {
            System.out.print("Please Enter Frequency: ");
            input = in.nextLine();
            if(isValid(input)){
                System.out.println("Valid Input");
                loop = false;
            } else {
                System.out.println("Invalid Input Detected");
            }
        }
        return Double.parseDouble(input);
    }

    /**
     * will generate a sample to be added to one of the arrays.
     * generates a sin wave between -1 and 1 at the specified frequency
     * @param loopCounter the counter in the loop generating samples
     * @param frequency the frequency of the sample you want to generate
     * @param sampleRate the number of samples in one second of audio
     * @return a double value of the sample.
     */
    private double createSample(int loopCounter, double frequency, int sampleRate){
        return Math.sin(Math.PI * 2 * loopCounter * frequency / sampleRate);
    }

    /**
     * prompts the user to enter a filename without the .wav extension
     * reads the file in and will write a new .wav file with "Rev" added to the end of the filename
     * this new file will have all of the audio samples placed in reverse order
     * @return reversed wave file
     */
    public WavFile reverseFile(){
        System.out.println("Enter the name of the wave file you would " +
                "like to reverse, do not include .wav");
        String filename = getFileNameFromUser();
        WavFile input = new WavFile(filename + ".wav");
        ArrayList<Double> arrayOfSamples = input.getSamples();
        input.close();
        ArrayList<Double> reversedArrayOfSamples = new ArrayList<Double>();
        for(int i = arrayOfSamples.size() -1; i >= 0; i--){
            reversedArrayOfSamples.add(arrayOfSamples.get(i));
        }
        WavFile output = new WavFile(filename + "Rev.wav", input.getNumChannels(),
                input.getNumFrames(), input.getValidBits(), input.getSampleRate());
        output.setSamples(reversedArrayOfSamples);
        output.close();
        return output;
    }

    /**
     * prompts the user for a filename without the .wav extension
     * prompts the user for a frequency
     * creates a one second long wave file with the specified filename at the specified frequency.
     * @return one second wave file at specified frequency
     */
    public WavFile generateFrequency(){
        System.out.println("Enter the name of the wave file you would " +
                "like to create do not include .wav");
        String filename = getFileNameFromUser();
        System.out.println("Enter the frequency of the wave file you would like to generate");
        double frequency = getFrequencyFromUser();
        WavFile output = new WavFile(filename + ".wav", NUM_CHANNELS_MONO,
                NUM_FRAMES, VALID_BITS, SAMPLE_RATE);
        ArrayList<Double> arrayOfSamples = new ArrayList<>();
        for (int i = 0; i < SAMPLE_RATE; i++){
            arrayOfSamples.add(createSample(i, frequency, SAMPLE_RATE));
        }
        output.setSamples(arrayOfSamples);
        output.close();
        return output;
    }

    /**
     * prompts the user for a filename without the .wav extension
     * prompts the user for the first frequency
     * prompts the user for the second frequency
     * generates a one second stereo wave file with the specified
     * filename and one frequency on each audio channel
     * @return one second stereo frequency
     */
    public WavFile generateStereoFrequencies(){
        System.out.println("Enter the name of the wave file you would like " +
                "to create do not include .wav");
        String filename = getFileNameFromUser();
        System.out.println("Enter the first frequency of the wave file " +
                "you would like to generate");
        double frequency1 = getFrequencyFromUser();
        System.out.println("Enter the second frequency of the wave file " +
                "you would like to generate");
        double frequency2 = getFrequencyFromUser();
        //create a new empty wave file
        WavFile output = new WavFile(filename + ".wav", NUM_CHANNELS_STEREO,
                NUM_FRAMES, VALID_BITS, SAMPLE_RATE);
        ArrayList<Double> arrayOfSamples = new ArrayList<Double>();
        for (int i = 0; i < SAMPLE_RATE; i++){
            arrayOfSamples.add(createSample(i, frequency1, SAMPLE_RATE));
            arrayOfSamples.add(createSample(i, frequency2, SAMPLE_RATE));
        }
        output.setSamples(arrayOfSamples);
        output.close();
        return output;
    }

    /**
     * assumes that the input is valid
     * takes input to determine which wave file should be created
     * then calls a method to create that file and then returns it.
     * @param choice the choice for witch wave file should be created
     * @return the wave file that was created
     */
    public WavFile wavFileSelect(int choice){
        WavFile output;
        switch (choice) {
            case 1:
                output = reverseFile();
                break;
            case 2:
                output = generateFrequency();
                break;
            case 3:
                output = generateStereoFrequencies();
                break;
            default:
                output = null;
        }
        return output;
    }

    /**
     * the main loop for the program.
     * will loop until the user enters a 0.
     */
    public void loop(){
        int input = promptUser();
        while (input != 0){
            System.out.format("\n%s\n\n", wavFileSelect(input));
            input = promptUser();
        }
    }

    //main method used for testing purposes
    public static void main(String[] args) {
        WavGenerator generator = new WavGenerator();
        generator.loop();
    }
}
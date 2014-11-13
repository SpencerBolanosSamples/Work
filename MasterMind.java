/*
Name: Spencer Bolanos
VUnetID: bolanosc
email: spencer.c.bolanos@vanderbilt.edu
date: Nov 6, 2014
honor: I pledge that I did not cheat or receive unauthorized assistance. Nor did I offer any.
description: This program plays the game master mind
*/

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import org.apache.commons.lang.ArrayUtils;
import java.io.*;


class MasterMind {
	/*
	Parameters for easy access; 'hidden' is the number of hidden numbers (size of array)
	'min' and 'max' are for range of guessable numbers
	*/
	public static int hidden = 4;
	public static int min = 1;
	public static int max = 6;
	
	//counters for descriptive statistics
	public static int count = 0;
	public static int count2 = 0;
	public static int total_guess = 0;
	//arbitrary high number for computations
	public static int best = 9999999;
	
	/*main
	Prints the "I am thinking of..." line and calls the necessary methods for building
	the hidden array and propmtping the player for inputs
	
	returns nothing
	*/
	public static void main(String[] args) {
		count++;
		int[] hidden_numbers = getRandom(hidden,min,max);
		//uncomment to see hidden array
		System.out.println(Arrays.toString(hidden_numbers));
		System.out.println("I am thinking of "+hidden+" numbers in the range "+min+" to "+max+".");
		String[] guess = new String[hidden];
		guess = prompt();
		
		chkGuess(hidden_numbers,guess,args);
	}
	
	/*
	prints game statistics (count of guesses, count of games, average guess rate, and best score)
	Takes no parameters
	
	returns nothing
	*/
	public static void print() {
		System.out.println("\nYou played a total of "+count+" games.");
		System.out.println("You made "+total_guess+" total guesses over those games.");
		System.out.println("That is an average of "+((double)total_guess/count)+" guesses per game.");
		System.out.println("Your best game was "+best+" guesses.");

	}
	
	/*
	Checks for accuracy of guess
	Prints whether any guesses were correct and if they were correctly placed
	Takes paramters hidden_numbers (the hidden array), guess (the guessed array), and args
	
	returns nothing
	*/
	public static void chkGuess(int[] hidden_numbers, String[] guess, String[] args){
		int[] guess_Int = new int[guess.length];
		int correct_counter = 0;
		int close_call_counter = 0;
		
		//converts String[] to int[]
		for(int i = 0; i < guess.length; i ++) {
			try{
				guess_Int[i] = Integer.parseInt(guess[i]);
			} catch (NumberFormatException nfe) {};
		}
		
		//number of guesses in correct positions
		for(int i = 0; i < hidden; i++) {
			if(hidden_numbers[i] == guess_Int[i]) {
				correct_counter++;
			}
		}
		
		//counts instances of correct numbers with wrong positions
		for(int i = 0; i < hidden; i++) {
			boolean does_contain = ArrayUtils.contains(hidden_numbers,guess_Int[i]);
			if(does_contain == true && guess_Int[i] != hidden_numbers[i]) {
				close_call_counter++;
			}
		}
	
		//prints results
		System.out.println("You had "+correct_counter+" correct numbers in the correct positions");
		System.out.println("And you had "+close_call_counter+" correct numbers in the wrong positions");
		
		//checks to see if all numbers were guessed correctly and placed correctly
		if(correct_counter == hidden){
			again(args);
		} else{
			count2++;
			guess = prompt();
			chkGuess(hidden_numbers,guess,args);
		}
		
	}
	
	/*
	checks if player wants to play again
	takes parameter args
	
	returns nothing
	*/
	public static void again(String[] args) {
		count2++;
		total_guess = total_guess + count2;
		
		//prints number of guesses
		Scanner console = new Scanner(System.in);
		System.out.println("\nCongratulations, you solved the puzzle in "+count2+" guesses.");
		System.out.println("\nDo you want to play again? (Y|N)");
		String input = console.nextLine();
		
		//keeps track of best score
		if(count2 < best) {
			best = count2;
		}
		
		//checks if player wants another go
		if(input.equalsIgnoreCase("Y")){
			count2 = 0;
			main(args);
		} else {
			print();
		}
	}
	
	/*
	prompts user for guesses
	Takes no paramters
	
	returns 'array', which is an array of all the user's guesses
	*/
	public static String[] prompt() {
		Scanner console = new Scanner(System.in);
		System.out.print("\nEnter "+hidden+" numbers separated by blanks: ");
		String input = console.nextLine();
		String[] array = input.split(" ");
		
		return array;
	}
	
	/*
	Makes a list of numbers from min and max and randomly selects them for the hidden array
	Takes paramters hidden (size of hidden array), min, max
	
	returns an array of 4 random, non-repeating numbers
	*/
	public static int[] getRandom(int hidden, int min, int max) {
		Random rand = new Random();
		int[] array = new int[hidden];
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		int count = 0;
		for(int i = min; i <= max; i++) {
			numbers.add(i);
		}
		while(numbers.size() > max-hidden){
			array[count] = numbers.remove(rand.nextInt(numbers.size()));
			count++;
		}
		return array;
	}
}
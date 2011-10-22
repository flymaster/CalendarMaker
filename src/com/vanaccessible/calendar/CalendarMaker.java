package com.vanaccessible.calendar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.GregorianCalendar;

public class CalendarMaker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 5)
		{
			System.err.println("Usage: CalendarMaker [inputFile] [outputFile] [startYear] [startMonth] [startDay]");
			System.err.println("Example: CalendarMaker myPlan.txt myCal.ics 2012 3 23");
		}
		
		String inputFileName = args[0];
		String outputFileName = args[1];
		int startYear = Integer.parseInt(args[2]);
		int startMonth = Integer.parseInt(args[3]);
		int startDay = Integer.parseInt(args[4]);
		
		GregorianCalendar jCal = new GregorianCalendar();
		jCal.set(GregorianCalendar.YEAR, startYear);
		jCal.set(GregorianCalendar.MONTH, startMonth - 1);
		jCal.set(GregorianCalendar.DAY_OF_MONTH, startDay);
		
		 try {
			BufferedReader in = new BufferedReader(new FileReader(inputFileName));
		} catch (FileNotFoundException e) {
			System.err.println("Input file does not exist!");
		}
		
		


	}

}

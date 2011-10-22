package com.vanaccessible.calendar;

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

	}

}

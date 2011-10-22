package com.vanaccessible.calendar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

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
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(inputFileName));
		} catch (FileNotFoundException e) {
			System.err.println("Input file does not exist!");
			return;
		}
		
		Calendar iCal = new Calendar();
		iCal.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		iCal.getProperties().add(Version.VERSION_2_0);
		iCal.getProperties().add(CalScale.GREGORIAN);
		
		ArrayList<VEvent> events = new ArrayList<VEvent>();
		VEvent v = null;
		PropertyList properties = null;
		String title = null;
		try {
			if (in.ready())
			{
				title = in.readLine();
			}
			else
			{
				System.err.println("No data in input file!");
			}
		
			while (in.ready())
			{
				String next = in.readLine();
				if (next.equals("--"))		//Start/End event;
				{
					if (properties!=null)
					{
						v = new VEvent(properties);
						events.add(v);
					}
					properties = new PropertyList();
					UidGenerator ug;
					try {
						ug = new UidGenerator(title);
					} catch (SocketException e) {
						e.printStackTrace();
						return;
					}
					properties.add(ug.generateUid());

				}
				
				if (next.startsWith("+"))	//date;
				{
					next = next.substring(1);
					GregorianCalendar eventDate = getDate(jCal, next.trim());
					properties.add(new DtStart(new Date(eventDate.getTime())));
				}
				
				if (next.startsWith("!"))	//title;
				{
					next = next.substring(1);
					next = next.trim();
					properties.add(new Summary(next));
				}
				else						//description
				{
					next = next.trim();
					properties.add(new Description(next));
				}
			}
		} catch (IOException e) {
			System.err.println("Input file broken?");
			e.printStackTrace();
		}

		for (VEvent e : events)
		{
			iCal.getComponents().add(e);
		}

		FileOutputStream fout;
		try {
			fout = new FileOutputStream(outputFileName);
		} catch (FileNotFoundException e1) {
			System.err.println("Output file not writable");
			return;
		}

		CalendarOutputter outputter = new CalendarOutputter();
		try {
			outputter.output(iCal, fout);
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		} catch (ValidationException e1) {
			System.err.println("Input file not a valid calendar!");
			e1.printStackTrace();
			return;
		}

	}

	private static GregorianCalendar getDate(GregorianCalendar jCal, String next) {
		GregorianCalendar cal = (GregorianCalendar)jCal.clone();
		
		String[] dates = next.split(" ");
		for (String s : dates)
		{
			if (s.startsWith("D"))
			{
				int days = Integer.parseInt(s.substring(1));
				cal.add(GregorianCalendar.DAY_OF_YEAR, days);
				
			}
			else if (s.startsWith("W"))
			{
				int weeks = Integer.parseInt(s.substring(1));
				cal.add(GregorianCalendar.WEEK_OF_YEAR, weeks);
			}			
			else if (s.startsWith("M"))
			{
				int months = Integer.parseInt(s.substring(1));
				cal.add(GregorianCalendar.MONTH, months);
			}
			
		}
		
		return cal;
	}

}

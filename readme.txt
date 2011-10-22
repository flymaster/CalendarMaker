ReadMe

Usage: CalendarMaker [inputFile] [outputFile] [startYear] [startMonth] [startDay]
Example: CalendarMaker myPlan.txt myCal.ics 2012 3 23

Calendar Maker takes an input file of the following format:

Workout Plan
--
+D0
!First Workout
run a lot
--
+D2
!Second Workout
run a lot more!
--
+W1
!Third Workout
keep running
--
+M1 D2
!Fourth Workout
run an odd number of days later
--


The first line of the file is the calendar title. 
	This won't be used anywhere, but is useful for keeping track of what is in the file.
	
Each event must be surrounded by a line containing two - characters.

An event consists of three lines:

First, an offset from the start date, preceded by a plus sign (+). Options are (D)ays, (W)eeks, and (M)onths.
Dates are specified with a letter and an integer, with no spaces between the letter and number.
Any or all units may be specified in a line.

Second, an event summary, preceded by an exclamation point (!). This is the line that will show up in a Google Calendar.

Third, a line containing a longer description, which will be displayed in the event details on your calendar.

The last line of the input must also be two - characters.


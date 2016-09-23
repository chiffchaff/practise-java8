package com.sumit.java8.practise.dateandtime;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTest {
	public static void main(String[] args) {
		/**
		 * The first class is Clock which provides access to the current instant, date and time using a time-zone. 
		 * Clock can be used instead of System.currentTimeMillis() and TimeZone.getDefault().
		 */
		// Get the system clock as UTC offset
		final Clock clock = Clock.systemUTC();
		System.out.println( clock.instant() );
		System.out.println( clock.millis() );
		
		
		/**
		 * LocaleDate and LocalTime. 
		 * LocaleDate holds only the date part without a time-zone in the ISO-8601 calendar system.
		 * LocaleTime holds only the time part without time-zone in the ISO-8601 calendar system. 
		 * Both LocaleDate and LocaleTime could be created from Clock.
		 */
		// Get the local date and local time
		
		final LocalDate date = LocalDate.now();
		final LocalDate dateFromClock = LocalDate.now( clock );
		System.out.println( date );
		System.out.println( dateFromClock );
		
		// Get the local date and local time
		final LocalTime time = LocalTime.now();
		final LocalTime timeFromClock = LocalTime.now( clock );
		System.out.println( time );
		System.out.println( timeFromClock );
		
		
		/**
		 * The LocalDateTime combines together LocaleDate and LocalTime 
		 * and holds a date with time but without a time-zone in the ISO-8601 calendar system
		 */
		// Get the local date/time
		
		final LocalDateTime datetime = LocalDateTime.now();
		final LocalDateTime datetimeFromClock = LocalDateTime.now( clock );
		System.out.println( datetime );
		System.out.println( datetimeFromClock );
		
		/**
		 * ZonedDateTime -> you need a date/time for particular timezone.
		 * It holds a date with time and with a time-zone in the ISO-8601 calendar system
		 */
		// Get the zoned date/time
		final ZonedDateTime zonedDatetime = ZonedDateTime.now();
		final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now( clock );
		final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );
		System.out.println( zonedDatetime );
		System.out.println( zonedDatetimeFromClock );
		System.out.println( zonedDatetimeFromZone );

		/**
		 * Duration class: an amount of time in terms of seconds and nanoseconds. 
		 * It makes very easy to compute the different between two dates.
		 */
		
		// Get duration between two dates
		final LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
		final LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );
		final Duration duration = Duration.between( from, to );
		System.out.println( "Duration in days: " + duration.toDays() );
		System.out.println( "Duration in hours: " + duration.toHours() );

		
		
	}
}

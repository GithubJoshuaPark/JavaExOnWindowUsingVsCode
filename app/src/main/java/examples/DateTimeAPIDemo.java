package examples;

import utils.Utils;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class DateTimeAPIDemo {
  public static void demonstrateDateTime() {
    Utils.printLine("Date/Time API Examples");

    // 1. Creating and Working with Local Dates
    System.out.println("Local Date Examples:");

    LocalDate today = LocalDate.now();
    LocalDate specificDate = LocalDate.of(2025, 12, 25);
    LocalDate parsedDate = LocalDate.parse("2025-11-03");

    System.out.println("Today: " + today);
    System.out.println("Specific date: " + specificDate);
    System.out.println("Parsed date: " + parsedDate);

    // Date manipulation
    System.out.println("\nDate Manipulation:");
    System.out.println("Tomorrow: " + today.plusDays(1));
    System.out.println("Last month: " + today.minusMonths(1));
    System.out.println("Next year: " + today.plusYears(1));

    // 2. Working with Local Time
    System.out.println("\nLocal Time Examples:");

    LocalTime now = LocalTime.now();
    LocalTime specificTime = LocalTime.of(13, 30, 15);
    LocalTime parsedTime = LocalTime.parse("14:45:30");

    System.out.println("Current time: " + now);
    System.out.println("Specific time: " + specificTime);
    System.out.println("Parsed time: " + parsedTime);

    // Time manipulation
    System.out.println("\nTime Manipulation:");
    System.out.println("2 hours later: " + now.plusHours(2));
    System.out.println("30 minutes before: " + now.minusMinutes(30));
    System.out.println("With modified hour: " + now.withHour(10));

    // 3. LocalDateTime - combining date and time
    System.out.println("\nLocalDateTime Examples:");

    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime specificDateTime = LocalDateTime.of(2025, Month.DECEMBER, 24, 20, 30);

    System.out.println("Current date and time: " + currentDateTime);
    System.out.println("Specific date and time: " + specificDateTime);

    // 4. Working with Zones
    System.out.println("\nTime Zone Examples:");

    ZonedDateTime zonedDateTime = ZonedDateTime.now();
    System.out.println("Current date and time with zone: " + zonedDateTime);

    // List available zone IDs
    Set<String> zoneIds = ZoneId.getAvailableZoneIds();
    System.out.println("Number of available time zones: " + zoneIds.size());

    // Time in different zones
    ZonedDateTime tokyoTime = currentDateTime.atZone(ZoneId.of("Asia/Tokyo"));
    ZonedDateTime newYorkTime = currentDateTime.atZone(ZoneId.of("America/New_York"));

    System.out.println("Tokyo time: " + tokyoTime);
    System.out.println("New York time: " + newYorkTime);

    // 5. Periods and Durations
    System.out.println("\nPeriods and Durations:");

    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    Period period = Period.between(startDate, endDate);
    System.out.printf("Period: %d years, %d months, %d days%n",
        period.getYears(), period.getMonths(), period.getDays());

    LocalTime startTime = LocalTime.of(9, 0);
    LocalTime endTime = LocalTime.of(17, 30);
    Duration duration = Duration.between(startTime, endTime);
    System.out.println("Duration in hours: " + duration.toHours());
    System.out.println("Duration in minutes: " + duration.toMinutes());

    // 6. Formatting and Parsing
    System.out.println("\nFormatting and Parsing Examples:");

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
    String formatted = currentDateTime.format(formatter);
    System.out.println("Formatted date time: " + formatted);

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate parsedDateFormatted = LocalDate.parse("11/03/2025", dateFormatter);
    System.out.println("Parsed custom format: " + parsedDateFormatted);

    // 7. Temporal Adjusters
    System.out.println("\nTemporal Adjusters Examples:");

    LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
    LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
    LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

    System.out.println("First day of month: " + firstDayOfMonth);
    System.out.println("Last day of month: " + lastDayOfMonth);
    System.out.println("Next Monday: " + nextMonday);

    // 8. Calculations and Comparisons
    System.out.println("\nCalculations and Comparisons:");

    LocalDate birthday = LocalDate.of(1990, 5, 15);
    long daysSinceBirth = ChronoUnit.DAYS.between(birthday, today);
    long monthsSinceBirth = ChronoUnit.MONTHS.between(birthday, today);
    long yearsSinceBirth = ChronoUnit.YEARS.between(birthday, today);

    System.out.println("Days since birth: " + daysSinceBirth);
    System.out.println("Months since birth: " + monthsSinceBirth);
    System.out.println("Years since birth: " + yearsSinceBirth);

    // Date comparisons
    System.out.println("\nDate Comparisons:");
    System.out.println("Is today before specific date? " +
        today.isBefore(specificDate));
    System.out.println("Is today after birthday? " +
        today.isAfter(birthday));
    System.out.println("Is specific time before now? " +
        specificTime.isBefore(now));
  }
}
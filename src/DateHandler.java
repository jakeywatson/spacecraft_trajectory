import java.time.*;

public class DateHandler {
	
	public static int daysBetween(int end_year){
		LocalDate start_date = LocalDate.of(2020, Month.JANUARY, 01);
		LocalDate end_date = LocalDate.of(end_year, Month.JANUARY, 01);
		Period period = Period.between(start_date, end_date);
		return period.getDays();
	}
	
	public static string outputDate(int days){
		LocalDate start_date = LocalDate.of(2020, Month.JANUARY, 01);
		localDate output = start_date.plus(days, ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd mm yyyy");
		return output.format(formatter);
	}
	
}
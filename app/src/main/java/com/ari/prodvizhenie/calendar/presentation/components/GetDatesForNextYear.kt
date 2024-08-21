import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

fun getDatesForCurrentYearGroupedByWeeks(): List<List<LocalDate>> {
    val currentDate = LocalDate.now()
    val startOfYear = currentDate.with(TemporalAdjusters.firstDayOfYear())
    val endOfYear = currentDate.with(TemporalAdjusters.lastDayOfYear())

    var currentWeekStart = startOfYear
    val weeks = mutableListOf<List<LocalDate>>()

    while (currentWeekStart.isBefore(endOfYear) || currentWeekStart == endOfYear) {
        val currentWeek = mutableListOf<LocalDate>()
        for (i in 0 until 7) {
            currentWeek.add(currentWeekStart)
            currentWeekStart = currentWeekStart.plusDays(1)
            if (currentWeekStart.dayOfWeek == DayOfWeek.MONDAY) {
                break
            }
        }
        weeks.add(currentWeek)
    }

    return weeks
}

fun getCurrentWeekNumber(items: List<List<LocalDate>>): Int {
    val currentDate = LocalDate.now()
    items.forEachIndexed { index, localDates ->
        if (localDates.contains(currentDate)) {
            return index + 2
        }
    }
    return 14
}

fun getCurrentDay(): LocalDate {
    return LocalDate.now()
}
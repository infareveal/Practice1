package com.example.habitexm.utility

object DateUtils {
    fun formatEpochToLocale(epochDay: Long?): String {
        if ((epochDay == null)) return "Daily"

        val date = java.time.LocalDate.ofEpochDay(epochDay)
        // "yMMMd" is the skeleton for: Year, Abbreviated Month, and Day.
        // Android will automatically turn this into:
        // India/UK: 6 May 2026
        // US: May 6, 2026
        val pattern = java.time.format.DateTimeFormatterBuilder.getLocalizedDateTimePattern(
            java.time.format.FormatStyle.MEDIUM,
            null,
            java.time.chrono.IsoChronology.INSTANCE,
            java.util.Locale.getDefault()
        )

        val formatter = java.time.format.DateTimeFormatter.ofPattern(pattern)
        return date.format(formatter)
    }

    // Helper to convert DatePicker millis to EpochDay
    fun millisToEpochDay(millis: Long): Long {
        return java.time.Instant.ofEpochMilli(millis)
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDate()
            .toEpochDay()
    }
}
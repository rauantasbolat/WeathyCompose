package com.rauantasbolat.weathykazdreamcompose.core.utils


import androidx.compose.ui.graphics.Color
import com.rauantasbolat.weathykazdreamcompose.R
import com.rauantasbolat.weathykazdreamcompose.ui.theme.Purple
import com.rauantasbolat.weathykazdreamcompose.ui.theme.Yellow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.math.roundToInt

fun getTemperature(temp : Double) : String {
       var formatted = temp - 273.15
       formatted = (temp*100).roundToInt() / 100.0
       formatted = String.format("%.1f", formatted).toDouble()
       return "${formatted}\u00B0C"
}

fun getTemperatureInteger(temp: Double): String {
       var formattedTemp = temp - 273.15
       formattedTemp = (formattedTemp * 100.0).roundToInt() / 100.0
       val newFormattedTemp = formattedTemp.toInt()
       return "$newFormattedTemp"
}

fun getTime(date : Int) : String {
       val dateTime = getDateTime(date)
       return getTimeFromDateTime(dateTime)
}
fun getDateTime(date: Int): String {
       return Instant
              .fromEpochSeconds(date.toLong())
              .toLocalDateTime(TimeZone.currentSystemDefault())
              .toString()
}

fun getTimeFromDateTime(dateTime: String): String {
       var i = 0
       while (dateTime[i] != 'T') i++
       return dateTime.substring(i + 1, dateTime.length)
}

fun getFormattedDate(date : Int): String {
       val dateTime = getDateTime(date)
       return formatDateParts(getDateFromDateTime(dateTime))
}
fun getDateFromDateTime(dateTime: String): String {
       var i = 0
       var date = ""
       while (dateTime[i] != 'T') {
              date += dateTime[i]
              i++
       }
       return date
}
fun formatDateParts(date: String): String {
       val datePartsArray = date.split('-')
       val formattedDateNumber = datePartsArray[2]
       val formattedMonth = mapMonthNumberToName(datePartsArray[1])
       val formattedDay = getWeekDayName(date)
       return "${formattedDay}, $formattedDateNumber $formattedMonth"
}

fun mapMonthNumberToName(monthNumber: String): String {
       return when (monthNumber) {
              "01" -> "January"
              "02" -> "February"
              "03" -> "March"
              "04" -> "April"
              "05" -> "May"
              "06" -> "June"
              "07" -> "July"
              "08" -> "August"
              "09" -> "September"
              "10" -> "October"
              "11" -> "November"
              "12" -> "December"
              else -> "NA"
       }
}

fun getWeekDayName(date: String): String {
       val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("u-M-d", Locale.ENGLISH)
       return LocalDate.parse(date, formatter).dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
}

fun getFormattedUVRange(uv: Double): String {
       return when {
              uv <= 2 -> "Low"
              uv > 2 && uv <= 5 -> "Moderate"
              uv > 5 && uv <= 7 -> "High"
              uv > 7 && uv <= 11 -> "Very High"
              else -> "Extreme"
       }
}

fun getUVIndexColor(level: String): Color {
       return when (level) {
              "Low" -> Color.Green
              "Moderate" -> Color.Yellow
              "High" -> Yellow
              "Very High" -> Color.Red
              "Extreme" -> Purple
              else -> Color.White
       }
}

fun getImageFromUrl(url: String): Int {
       return when (url) {
              "01d" -> R.drawable.ic_sun
              "01n" -> R.drawable.ic_moon
              "02d" -> R.drawable.ic_few_clouds
              "02n" -> R.drawable.ic_night_clouds
              "03d", "03n" -> R.drawable.ic_scattered_clouds
              "04d", "04n" -> R.drawable.ic_broken_clouds
              "09d", "09n" -> R.drawable.ic_shower_rain
              "10d", "10n" -> R.drawable.ic_rain
              "11d", "11n" -> R.drawable.ic_thunderstorm
              "13d", "13n" -> R.drawable.ic_snow
              "50d", "50n" -> R.drawable.ic_mist
              else -> R.drawable.ic_launcher_foreground
       }
}

fun getDayFromDate(date: Int) : String {
       val newDate = getDateFromDateTime(getDateTime(date))
       return getWeekDayName(newDate).substring(0, 3)
}
package com.example.weatherapplication


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


val ForecastItems = listOf(
    DayForecast(1688875200, 1688875200, 1688936400, ForecastTemp(75f, 65f, 80f), 1023f, 100),
    DayForecast(1688962200, 1688962200, 1689026400, ForecastTemp(80f, 64f, 86f), 1073f, 88),
    DayForecast(1689051600, 1689051600, 1689109800, ForecastTemp(62f, 55f, 71f), 1032f, 67),
    DayForecast(1689136500, 1689136500, 1689197100, ForecastTemp(65f, 62f, 72f), 991f, 35),
    DayForecast(1689225900, 1689225900, 1689282900, ForecastTemp(68f, 53f, 87f), 1051f, 83),
    DayForecast(1689311700, 1689311700, 1689373200, ForecastTemp(70f, 65f, 79f), 1047f, 22),
    DayForecast(1689402600, 1689402600, 1689459000, ForecastTemp(82f, 61f, 86f), 995f, 26),
    DayForecast(1689488400, 1689488400, 1689546600, ForecastTemp(56f, 55f, 63f), 1018f, 48),
    DayForecast(1689573000, 1689573000, 1689629700, ForecastTemp(74f, 51f, 83f), 1062f, 59),
    DayForecast(1689660300, 1689660300, 1689720600, ForecastTemp(71f, 53f, 78f), 1000f, 52),
    DayForecast(1689745200, 1689745200, 1689807600, ForecastTemp(85f, 79f, 97f), 1038f, 20),
    DayForecast(1689830400, 1689830400, 1689894900, ForecastTemp(64f, 57f, 77f), 1070f, 40),
    DayForecast(1689915300, 1689915300, 1689976200, ForecastTemp(87f, 80f, 92f), 11027f, 33),
    DayForecast(1689998700, 1689998700, 1690060680, ForecastTemp(79f, 74f, 84f), 1043f, 61),
    DayForecast(1690085520, 1690085520, 1690149480, ForecastTemp(66f, 53f, 72f), 1065f, 67),
    DayForecast(1690173480, 1690173480, 1690234920, ForecastTemp(84f, 82f, 90f), 1025f, 19),
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen(ForecastItems: List<DayForecast>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar(stringResource(R.string.second_screen))
        LazyColumn {
            items(ForecastItems.size) { item ->
                ForecastInfo(ForecastItems[item])
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastInfo(forecastItem: DayForecast) {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm")
    val sunriseTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(forecastItem.sunrise), ZoneOffset.UTC)
    val sunsetTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(forecastItem.sunset), ZoneOffset.UTC)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        MyImage(modifier = Modifier.size(50.dp))
        Texts(stringResource(R.string.date, sunriseTime.month, sunriseTime.dayOfMonth), 14.sp)
        Spacer(modifier = Modifier.width(width = 30.dp))

        Column(modifier = Modifier.padding(10.dp)) {
            Texts(text = stringResource(R.string.temp, forecastItem.temp.day.toInt()), 12.sp)
            Spacer(modifier = Modifier.height(height = 5.dp))
            Texts(stringResource(R.string.high, forecastItem.temp.max.toInt()), 12.sp)
        }

        Column(modifier = Modifier.padding(10.dp)) {
            Texts(text = "", size = 12.sp)
            Spacer(modifier = Modifier.height(height = 5.dp))
            Texts(stringResource(R.string.low, forecastItem.temp.min.toInt()), 12.sp)
        }

        Column(modifier = Modifier.padding(10.dp)) {
            Texts(stringResource(R.string.sunrise, sunriseTime.format(formatter)), 12.sp)
            Spacer(modifier = Modifier.height(height = 5.dp))
            Texts(stringResource(R.string.sunset, sunsetTime.format(formatter)), 12.sp)
        }
    }
}
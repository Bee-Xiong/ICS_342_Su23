package com.example.weatherapplication

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun ForecastScreen(zipCode: String, viewModel: ForecastViewModel = hiltViewModel()) {
    val forecastData = viewModel.forecastData.observeAsState()
    val forecastList = forecastData.value?.ForecastList
    val timezone = forecastData.value?.city?.timezone
    LaunchedEffect(Unit) {
        viewModel.setZipCode(zipCode)
    }

    if (forecastData == null) {
        EmptyView()
        return
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleBar(stringResource(R.string.second_screen))
            LazyColumn {
                if (forecastList != null) {
                    items(forecastList.size) { item ->
                        if (timezone != null) {
                            ForecastInfo(forecastList[item], timezone)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastInfo(forecastItem: DayForecast, timezone: Long) {
    val formatTime: DateTimeFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.time_format))
    val formatMonth: DateTimeFormatter =
        DateTimeFormatter.ofPattern(stringResource(R.string.month_format))
    val sunriseTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(
                forecastItem.sunrise + timezone
            ), ZoneOffset.UTC

        )
    val sunsetTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(
                forecastItem.sunset + timezone
            ), ZoneOffset.UTC
        )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = forecastItem.iconUrl,
            contentDescription = forecastItem.weather.firstOrNull()?.description,
            modifier = Modifier.size(50.dp)
        )
        Texts(
            stringResource(
                R.string.date,
                sunriseTime.format(formatMonth),
                sunriseTime.dayOfMonth
            ), 14.sp
        )
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
            Texts(stringResource(R.string.sunrise, sunriseTime.format(formatTime)), 12.sp)
            Spacer(modifier = Modifier.height(height = 5.dp))
            Texts(stringResource(R.string.sunset, sunsetTime.format(formatTime)), 12.sp)
        }
    }
}
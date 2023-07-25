package com.example.weatherapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Start") {
                this.composable("Start") {
                    MyApp(navController)
                }
                composable(
                    "ForecastScreen",
                ) {
                    ForecastScreen()
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(navController: NavController, viewModel: CurrentConditionsViewModel = hiltViewModel()) {
    val weatherData = viewModel.weatherData.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.viewAppeared()
    }

    if (weatherData == null) {
        EmptyView()
        return
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleBar(stringResource(R.string.app_name))
            Location()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrentTemp(weatherData)
                }
                Spacer(modifier = Modifier.width(width = 75.dp))
                MainImage(weatherData)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                MoreInfo(weatherData)
            }
            Column {
                ForecastButton(navController)
            }
        }
    }
}

@Composable
fun EmptyView() {
    Text(text = "No Data")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(text: String) {
    TopAppBar(
        title = {
            Text(text = text)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Gray)
    )
}

@Composable
fun Location() {
    Texts(stringResource(R.string.location), 20.sp)
}

@Composable
fun CurrentTemp(weatherData: State<WeatherData?>) {
    val currTemp = weatherData.value?.main?.temp?.toInt().toString()
    val feelTemp = weatherData.value?.main?.feelsLike?.toInt().toString()
    Texts(stringResource(R.string.currTemp, currTemp), 75.sp)
    Texts(stringResource(R.string.feel, feelTemp), 12.sp)
}

@Composable
fun MainImage(weatherData: State<WeatherData?>) {
    val description = weatherData.value?.weather?.firstOrNull()?.description.toString()
    AsyncImage(
        model = weatherData.value?.iconUrl,
        contentDescription = description,
        modifier = Modifier.size(100.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoreInfo(weatherData: State<WeatherData?>) {
    val low = weatherData.value?.main?.tempMin?.toInt().toString()
    val high = weatherData.value?.main?.tempMax?.toInt().toString()
    val humidity = weatherData.value?.main?.humidity.toString()
    val pressure = weatherData.value?.main?.pressure.toString()
    Texts(stringResource(R.string.low, low), 20.sp)
    Texts(stringResource(R.string.high, high), 20.sp)
    Texts(stringResource(R.string.humidity, humidity), 20.sp)
    Texts(stringResource(R.string.pressure, pressure), 20.sp)
}

@Composable
fun Texts(text: String, size: TextUnit) {
    Text(
        text = text,
        fontSize = size
    )
}

@Composable
fun ForecastButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("ForecastScreen") },
        modifier = Modifier.width(150.dp),
        colors = ButtonDefaults.buttonColors(Color.Gray),
        shape = RectangleShape,
    ) {
        Text(text = "Forecast", color = Color.Black)
    }
}
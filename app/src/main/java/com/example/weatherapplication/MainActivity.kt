package com.example.weatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = stringResource(R.string.start)
            ) {
                this.composable("Start") {
                    MyApp(navController)
                }
                composable("ForecastScreen/{zipCodeNum}",
                    arguments = listOf(
                        navArgument("zipCodeNum") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val item = it.arguments?.getString(stringResource(R.string.zipcode_pass_value))
                    if (item != null) {
                        ForecastScreen(zipCode = item)
                    }
                }
            }
        }
    }
}


@Composable
fun MyApp(navController: NavController, viewModel: CurrentConditionsViewModel = hiltViewModel()) {
    val weatherData = viewModel.weatherData.observeAsState()
    val zipCodeNumber = viewModel.zipCode
    viewModel.setCurrentZipCode(zipCodeNumber)
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
            Location(weatherData)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(137, 207, 240))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrentTemp(weatherData)
                }
                Spacer(modifier = Modifier.width(width = 65.dp))
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
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ForecastButton(viewModel, navController)
                }
                Spacer(modifier = Modifier.height(height = 50.dp))
                ZipCodeSearch(viewModel)
            }
        }
    }
}

@Composable
fun EmptyView() {
    Text(stringResource(R.string.no_data))
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
fun Location(weatherData: State<WeatherData?>) {
    val city: String = weatherData.value?.name.toString()
    val country: String = weatherData.value?.country?.country.toString()
    Texts("$city, $country", 20.sp)
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
fun ForecastButton(viewModel: CurrentConditionsViewModel, navController: NavController) {
    Button(
        onClick = {
            val zipCodeNum: String = viewModel.zipCode
            navController.navigate("ForecastScreen/$zipCodeNum")
        },
        modifier = Modifier.width(150.dp),
        colors = ButtonDefaults.buttonColors(Color.Gray),
        shape = RectangleShape,
    ) {
        Text(stringResource(R.string.button_forecast), color = Color.Black)
    }
}

@Composable
fun Alert(name: String, showDialog: Boolean, onDismiss: () -> Unit) {
    AlertDialog(
        title = { Text(text = stringResource(R.string.alert_title)) },
        text = { Text(stringResource(R.string.alert_text)) },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.button_confirm))
            }
        },
        dismissButton = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZipCodeSearch(viewModel: CurrentConditionsViewModel) {
    val showDialog = remember { mutableStateOf(false) }
    val zipCode = viewModel.zipCodeLiveData.observeAsState()


    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = zipCode.value.toString(),
            onValueChange = { newText ->
                val regex = Regex("\n")
                val newTextNoNewSpace = newText.replace(regex, "")
                viewModel.setCurrentZipCode(newTextNoNewSpace)
            },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color(250, 250, 250)),
            modifier = Modifier.width(150.dp),
            textStyle = TextStyle.Default.copy(fontSize = 20.sp),
            singleLine = true,
            label = { Text(stringResource(R.string.textField_label)) },
        )
        Button(
            onClick = {
                if (zipCode.value?.length != 5 || !(zipCode.value?.isDigitsOnly())!!) {
                    showDialog.value = true
                } else {
                    viewModel.setZipCode(zipCode.value.toString())
                    viewModel.viewAppeared()
                }
            },
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(Color.Gray),
            shape = RectangleShape,
        ) {
            Text(stringResource(R.string.button_search), color = Color.Black)
        }
        if (showDialog.value) {
            Alert(
                stringResource(R.string.alert_name),
                showDialog = showDialog.value,
                onDismiss = { showDialog.value = false })
        }
    }
}
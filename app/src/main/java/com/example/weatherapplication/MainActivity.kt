package com.example.weatherapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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
                    ForecastScreen(ForecastItems)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(navController: NavController) {
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
                CurrentTemp()
            }
            Spacer(modifier = Modifier.width(width = 75.dp))
            MyImage(modifier = Modifier.size(100.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            MoreInfo()
        }
        Column {
            ForecastButton(navController)
        }
    }
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
fun CurrentTemp() {
    val currInfo = ForecastItems[0]
    val currTemp = stringResource(R.string.currTemp, currInfo.temp.day.toInt())
    Texts(currTemp,75.sp)
    Texts(stringResource(R.string.feel), 12.sp)
}

@Composable
fun MyImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable._1d),
        contentDescription = stringResource(R.string.image_description),
        modifier = modifier
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoreInfo() {
    val currInfo = ForecastItems[0]
    Texts(stringResource(R.string.low, currInfo.temp.min.toInt()), 20.sp)
    Texts(stringResource(R.string.high, currInfo.temp.max.toInt()), 20.sp)
    Texts(stringResource(R.string.humidity, currInfo.humidity), 20.sp)
    Texts(stringResource(R.string.pressure, currInfo.pressure.toInt()), 20.sp)
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

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MyApp()
//}
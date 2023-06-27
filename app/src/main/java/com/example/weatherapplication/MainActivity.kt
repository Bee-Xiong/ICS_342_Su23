package com.example.weatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapplication.ui.theme.WeatherApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar()
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
            MyImage()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
        ) {
            MoreInfo()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Gray)
    )
}

@Composable
fun Location() {
    Text(
        text = stringResource(R.string.location),
        style = TextStyle(
            fontSize = 20.sp,
        )
    )
}

@Composable
fun CurrentTemp() {
    Text(
        text = stringResource(R.string.currTemp),
        style = TextStyle(
            fontSize = 75.sp,
        )
    )
    Text(
        text = stringResource(R.string.feel),
    )
}

@Composable
fun MyImage() {
    Image(
        painter = painterResource(id = R.drawable._1d),
        contentDescription = stringResource(R.string.image_description),
        modifier = Modifier.size(100.dp)
    )
}

@Composable
fun MoreInfo() {
    Text(
        text = stringResource(R.string.low),
        style = TextStyle(
            fontSize = 20.sp,
        )
    )
    Text(
        text = stringResource(R.string.high),
        style = TextStyle(
            fontSize = 20.sp,
        )
    )
    Text(
        text = stringResource(R.string.humidity),
        style = TextStyle(
            fontSize = 20.sp,
        )
    )
    Text(
        text = stringResource(R.string.pressure),
        style = TextStyle(
            fontSize = 20.sp,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
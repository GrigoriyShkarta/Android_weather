package com.example.weather

import com.android.volley.Request
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest

import com.android.volley.toolbox.Volley
import com.example.weather.ui.theme.WeatherTheme
import org.json.JSONObject

const val API_KEY = "7a3303f20d894ae1932123045232108 "
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Kharkiv", this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context) {
    val state = remember{
        mutableStateOf("Unknown")
    }
    Column(
        Modifier.fillMaxSize()
    ) {
        Box(
            Modifier.fillMaxHeight(0.5f)
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Temp in $name = ${state.value} C")
        }
        Box(
            Modifier.fillMaxHeight()
                    .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = {
                getResult(name, state, context)
            }, Modifier.padding(5.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Refresh")
            }
        }
    }
}

private fun getResult(city: String, state: MutableState<String>, context: Context) {
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        {
            res ->
            val obj = JSONObject(res)
            state.value = obj.getJSONObject("current").getString("temp_c")
        },
        {
            err ->
            Log.d("MyLog", "Error $err")
        }
    )
    queue.add(stringRequest)
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    WeatherTheme {
//        Greeting("Android")
//    }
//}
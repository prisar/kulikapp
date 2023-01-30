package com.agrohi.kulik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.agrohi.kulik.ui.theme.KulikTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KulikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        Text("Kulik app for raiganj")

        Image(
            painter = painterResource(id = R.drawable.kulik),
            contentDescription = "train",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )

        Text("Kulik is a river in Raiganj town of West bengal. It used to be an important port" +
                "for uttar dinajpur district. Raiganj town has developed impressively in recent years." +
                "There is a bird santurary besides the river.")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KulikTheme {
        Home()
    }
}
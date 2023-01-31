package com.agrohi.kulik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.LightGreen

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

        Card(shape = RoundedCornerShape(20.dp),
            elevation = 10.dp,
            backgroundColor = LightGreen,
            modifier = Modifier
                .width(200.dp)
                .height(150.dp)
                .clickable() {
//                context.startActivity(Intent(context, MapActivity::class.java).putExtra("url", "https://agrohikulik.web.app/raiganj_06/${dialogMouza.name}/${it}/MouzaMap.html"))
//                openDialog.value = false
            }) {
            Column(modifier = Modifier.padding(all = 10.dp),
                verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Do you like this app?",
                    style = TextStyle(
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }

        }

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
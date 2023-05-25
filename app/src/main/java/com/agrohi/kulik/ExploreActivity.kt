package com.agrohi.kulik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agrohi.kulik.ui.theme.KulikTheme
import com.agrohi.kulik.ui.theme.exploreCardBlue
import com.agrohi.kulik.ui.theme.exploreCardYellow

class ExploreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KulikTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Explore()
                }
            }
        }
    }
}

@Composable
fun Explore() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text("Explore", style = TextStyle(fontSize = 32.sp))
        }
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Card(
                shape = RoundedCornerShape(5.dp),
                backgroundColor = exploreCardYellow,
                modifier = Modifier
                    .height(180.dp)
                    .width(160.dp)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Text("Community Posts", fontSize = 16.sp, fontWeight = FontWeight.W600)
            }

            Card(
                shape = RoundedCornerShape(5.dp),
                backgroundColor = exploreCardBlue,
                modifier = Modifier
                    .height(180.dp)
                    .width(160.dp)
            ) {
                Text("Updates", fontSize = 16.sp, fontWeight = FontWeight.W600)
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Card(
                shape = RoundedCornerShape(5.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .height(300.dp)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text("", fontSize = 16.sp, fontWeight = FontWeight.W600)
            }
        }
    }

}
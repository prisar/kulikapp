package com.agrohi.kulik.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agrohi.kulik.R
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.agrohi.kulik.ui.theme.LightGreen


@Composable
fun HomeScreen(
    navController: NavController,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(LightBlueBg)
    ) {
        val context = LocalContext.current

        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "train",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Card(shape = RoundedCornerShape(20.dp),
            elevation = 1.dp,
            backgroundColor = LightGreen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .height(75.dp)
                .clickable() {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=com.agrohi.kulik")
                        )
                    )
                }) {
            Column(
                modifier = Modifier.padding(all = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                androidx.compose.material.Text(
                    text = "Do you like this app?",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                    ),
                    modifier = Modifier.padding(12.dp)
                )
            }

        }

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 1.dp,
            backgroundColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .height(250.dp)
        ) {
            Column(
                modifier = Modifier.padding(all = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                ) {
                    androidx.compose.material.Text(
                        text = "History",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    )


                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                ) {
                    androidx.compose.material.Text(
                        "Kulik is a river in Raiganj town of West bengal. It used to be an important port" +
                                "for uttar dinajpur district. Raiganj town has developed impressively in recent years." +
                                "There is a bird santurary besides the river.",
                        style = TextStyle(color = Color.Black),
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Divider(
                    color = LightBlueBg,
                    thickness = 1.dp,
                    startIndent = 5.dp
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                ) {
                    androidx.compose.material.Text(
                        "Radhikapur railway station is getting new platform",
                        style = TextStyle(color = Color.Black),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

package com.agrohi.kulik.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agrohi.kulik.R
import com.agrohi.kulik.ui.theme.exploreCardBlue
import com.agrohi.kulik.ui.theme.exploreCardYellow

@Composable
fun ExploreScreen(
    navController: NavController,
) {
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
            Text("Explore", style = TextStyle(fontSize = 32.sp), fontWeight = FontWeight.W700)
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
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(180.dp)
                        .width(160.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.height(120.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.community),
                            contentDescription = stringResource(id = R.string.community_posts),
                            modifier = Modifier.size(90.dp)
                        )
                    }
                    Row() {
                        Text("Community Posts", fontSize = 16.sp, fontWeight = FontWeight.W600)
                    }
                }
            }

            Card(
                shape = RoundedCornerShape(5.dp),
                backgroundColor = exploreCardBlue,
                modifier = Modifier
                    .height(180.dp)
                    .width(160.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(180.dp)
                        .width(160.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.height(120.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.write),
                            contentDescription = null,
                            modifier = Modifier.size(90.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Text("Updates", fontSize = 16.sp, fontWeight = FontWeight.W600)
                    }
                }

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
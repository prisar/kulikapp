package com.agrohi.kulik.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.agrohi.kulik.R
import com.agrohi.kulik.model.Post
import com.agrohi.kulik.ui.theme.exploreCardBlue
import com.agrohi.kulik.ui.theme.exploreCardYellow
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    viewModel: ExploreViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        item {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Text("Explore", style = TextStyle(fontSize = 32.sp), fontWeight = FontWeight.W700)
            }
        }

        // Explore Cards
        item {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            ) {
                Card(
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = exploreCardYellow),
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
                    colors = CardDefaults.cardColors(containerColor = exploreCardBlue),
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
        }

        // Top Posts Section Header
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Top Posts",
                    style = TextStyle(fontSize = 24.sp),
                    fontWeight = FontWeight.W600
                )
            }
        }

        // Posts Grid
        item {
            val postsCount = viewModel.posts.size
            val gridHeight = if (postsCount > 0) ((postsCount / 3 + 1) * 160).dp else 0.dp

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(gridHeight)
            ) {
                items(viewModel.posts) { post ->
                    PostCard(post = post)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PostCard(post: Post) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Post Image
            if (post.thumbnail.isNotEmpty()) {
                GlideImage(
                    model = post.thumbnail,
                    contentDescription = post.message,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            } else if (post.photoUrl.isNotEmpty() && post.photoUrl != "null") {
                GlideImage(
                    model = post.photoUrl,
                    contentDescription = post.message,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            } else {
                // Fallback for posts without images
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(exploreCardYellow),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = post.message,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W600,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            // Post Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Text(
                    text = post.message,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Likes
                Text(
                    text = "${post.likes} likes",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExploreScreenPreview() {
    val navController = rememberNavController()
    ExploreScreen(navController = navController)
}
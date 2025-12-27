package com.agrohi.kulik.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.agrohi.kulik.R
import com.agrohi.kulik.ui.theme.exploreCardBlue
import com.agrohi.kulik.ui.theme.exploreCardYellow

data class Post(
    val id: Int,
    val userName: String,
    val userAvatar: Int,
    val postImage: Int,
    val title: String,
    val description: String,
    val likes: Int,
    val comments: Int
)

// Sample data for top posts
fun getSamplePosts(): List<Post> {
    return listOf(
        Post(
            id = 1,
            userName = "Rahul Kumar",
            userAvatar = R.drawable.community,
            postImage = R.drawable.community,
            title = "Best practices for crop rotation",
            description = "Sharing my experience with crop rotation techniques that increased my yield by 30%",
            likes = 45,
            comments = 12
        ),
        Post(
            id = 2,
            userName = "Priya Singh",
            userAvatar = R.drawable.write,
            postImage = R.drawable.write,
            title = "Organic farming success story",
            description = "How I transitioned to organic farming and the lessons learned along the way",
            likes = 67,
            comments = 23
        ),
        Post(
            id = 3,
            userName = "Amit Patel",
            userAvatar = R.drawable.community,
            postImage = R.drawable.community,
            title = "Smart irrigation techniques",
            description = "Using technology to optimize water usage and reduce costs",
            likes = 89,
            comments = 34
        ),
        Post(
            id = 4,
            userName = "Sunita Devi",
            userAvatar = R.drawable.write,
            postImage = R.drawable.write,
            title = "Pest management tips",
            description = "Natural and effective ways to manage pests without harmful chemicals",
            likes = 56,
            comments = 18
        ),
        Post(
            id = 5,
            userName = "Vijay Sharma",
            userAvatar = R.drawable.community,
            postImage = R.drawable.community,
            title = "Seasonal crop planning",
            description = "Planning your crops according to seasons for maximum profit",
            likes = 72,
            comments = 27
        )
    )
}

@Composable
fun ExploreScreen(
    navController: NavController,
) {
    val posts = getSamplePosts()

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

        // Posts List
        items(posts) { post ->
            PostCard(post = post)
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // User Info
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = post.userAvatar),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = post.userName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600
                )
            }

            // Post Image
            Image(
                painter = painterResource(id = post.postImage),
                contentDescription = "Post Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            // Post Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = post.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = post.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Likes and Comments
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${post.likes} likes",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "${post.comments} comments",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.DarkGray
                    )
                }
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
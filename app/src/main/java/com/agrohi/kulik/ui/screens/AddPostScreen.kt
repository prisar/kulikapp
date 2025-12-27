package com.agrohi.kulik.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.agrohi.kulik.ui.theme.LightBlueBg
import com.agrohi.kulik.ui.theme.LightGreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import com.agrohi.kulik.utils.PostUtils

private lateinit var auth: FirebaseAuth

@Composable
fun AddPostScreen(
    navController: NavController,
    viewModel: AddPostViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(LightBlueBg)
    ) {

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .height(500.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight() // Use fillMaxHeight to allow SpaceBetween to work
            ) {
                Row {
                    Text(
                        "Post",
                        style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 16.dp) // Added padding for better spacing
                    )
                }

                OutlinedTextField(
                    value = viewModel.message,
                    onValueChange = { viewModel.updateMessage(it) },
                    label = { Text("Write the description of your post") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(horizontal = 15.dp),
                    shape = RoundedCornerShape(5.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(60.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(30.dp))
                        .background(LightGreen)
                        .clickable {
                            viewModel.createPost(
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Post created",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                    navController.navigate("feed")
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Failed to create post.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                },
                                onNotSignedIn = {
                                    Toast.makeText(
                                        context,
                                        "Please sign in",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            )
                        }
                ) {
                    Text(
                        "Create",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = Color.White // Added for better contrast
                        )
                    )
                }
            }
        }
    }
}

package com.example.internshiptask.view.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.internshiptask.R
import com.example.internshiptask.view.navigation.InternshipTaskScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked:() -> Unit = {}
) {
    TopAppBar(title = {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                          if(showProfile){
                              Icon(imageVector = Icons.Default.Favorite, contentDescription = "fav icon",
                                  modifier = Modifier
                                      .clip(RoundedCornerShape(12.dp))
                                      .scale(0.9f))
                          }
                          if(icon!=null) {
                              Icon(imageVector = icon, contentDescription = "arrow back",
                                  tint = Color.Red.copy(0.7f),
                                  modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                          }
                          Spacer(modifier = Modifier.width(40.dp))

                          Text(text = title,
                              color = Color.Red.copy(0.7f),
                              style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                          )

                      }
    },
        actions = {
                  IconButton(onClick = {
                      FirebaseAuth.getInstance().signOut().run {
                          navController.navigate(InternshipTaskScreens.LoginScreen.route){
                              popUpTo(InternshipTaskScreens.HomeScreen.route){
                                  inclusive = true
                                  saveState = true
                              }
                          }
                      }
                  }) {
                      if(showProfile) Row {
                          Icon(painter = painterResource(id = R.drawable.log_out_icon),
                              contentDescription = "log out button",
                              modifier = Modifier.scale(0.7f))
                      } else Box{}
                  }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ), modifier = Modifier.shadow(0.dp)
    )
}
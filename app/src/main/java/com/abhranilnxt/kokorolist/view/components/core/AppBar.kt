package com.abhranilnxt.kokorolist.view.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.abhranilnxt.kokorolist.R
import com.abhranilnxt.kokorolist.ui.theme.highlightColor
import com.abhranilnxt.kokorolist.ui.theme.poppinsFamily
import com.abhranilnxt.kokorolist.view.navigation.KokoroListScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    showStats: Boolean = false,
    navController: NavController,
    onBackArrowClicked:() -> Unit = {}
) {
    TopAppBar(title = {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                          if(showProfile){
                              Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = "star icon",
                                  tint = Color(0xFF5E8DCF),
                                  modifier = Modifier.size(28.dp)
                              )
                          }
                          if(icon!=null) {
                              Icon(imageVector = icon, contentDescription = "arrow back",
                                  tint = highlightColor,
                                  modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                          }
                          Spacer(modifier = Modifier.width(40.dp))

                          Text(text = title,
                              color = Color.White,
                              style = TextStyle(fontSize = 28.sp),
                              fontFamily = poppinsFamily, fontWeight = FontWeight.Bold
                          )

                      }
    },
        actions = {
                  IconButton(modifier = Modifier.shadow(elevation = 4.dp), onClick = {
                      if(showStats) {
                          FirebaseAuth.getInstance().signOut().run {
                              navController.navigate(KokoroListScreens.LoginScreen.route){
                                  popUpTo(KokoroListScreens.HomeScreen.route){
                                      inclusive = true
                                      saveState = true
                                  }
                              }
                          }
                      } else if(showProfile) {
                          navController.navigate(KokoroListScreens.StatsScreen.route)
                      }

                  }) {
                      if(showStats) Row {
                          Icon(painter = painterResource(id = R.drawable.log_out_icon),
                              contentDescription = "log out button",
                              tint = highlightColor,
                              modifier = Modifier.scale(0.7f))
                      }
                      else if(showProfile) Row {
                          Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "account logo",
                              tint = highlightColor,
                              modifier = Modifier.size(64.dp)
                          )
                      }
                      else Box{}
                  }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF030710)
        ), modifier = Modifier.shadow(0.dp)
    )
}
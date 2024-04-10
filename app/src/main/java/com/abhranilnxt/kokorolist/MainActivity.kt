package com.abhranilnxt.kokorolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abhranilnxt.kokorolist.ui.theme.InternshipTaskTheme
import com.abhranilnxt.kokorolist.view.navigation.KokoroListNavigation
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.setPersistenceEnabled(true)
        setContent {
            InternshipTaskTheme {
                InternshipTaskApp()
            }
        }
    }
}

@Composable
fun InternshipTaskApp() {
    Surface(color = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            KokoroListNavigation()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InternshipTaskTheme {
        InternshipTaskApp()
    }
}
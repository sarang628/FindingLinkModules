package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.screen_map.compose.MapScreenForRestaurant
import com.example.screen_map.data.MarkerData
import com.sarang.torang.di.finding_di.Finding
import com.sarang.torang.theme.FindingLinkModulesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindingLinkModulesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val rootNavController = RootNavController()
                    NavHost(navController = navController, startDestination = "finding") {
                        composable("finding") {
                            Box {
                                Finding(
                                    navController = rootNavController
                                )
                            }
                        }

                        composable("test") {
                        }

                        composable("restaurant/{restaurantId}") {
                            Text(text = "")
                        }

                        composable("map") {
                            val marker = MarkerData(
                                id = 0,
                                lon = 123.90530463625674,
                                lat = 10.317742495546137,
                                title = "restaurant",
                                foodType = ""
                            )
                            MapScreenForRestaurant(
                                selectedMarkerData = marker,
                                zoom = 17f
                            )
                        }
                    }

                }
            }
        }
    }
}
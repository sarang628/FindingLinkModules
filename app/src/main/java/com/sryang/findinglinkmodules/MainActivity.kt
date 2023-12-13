package com.sryang.findinglinkmodules

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sryang.findinglinkmodules.di.finding.Finding
import com.sryang.findinglinkmodules.ui.theme.FindingLinkModulesTheme
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
                    NavHost(navController = navController, startDestination = "finding") {
                        composable("finding") {
                            Box {
                                Finding(
                                    navController = rememberNavController()
                                )
                                Button(onClick = { navController.navigate("test") }) {

                                }
                            }
                        }

                        composable("test") {
                            Button(onClick = { navController.popBackStack() }) {

                            }
                        }
                    }

                }
            }
        }
    }
}
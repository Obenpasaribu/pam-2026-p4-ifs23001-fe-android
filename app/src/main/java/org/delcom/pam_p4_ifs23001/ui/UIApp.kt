package org.delcom.pam_p4_ifs23001.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.delcom.pam_p4_ifs23001.helper.ConstHelper
import org.delcom.pam_p4_ifs23001.ui.components.CustomSnackbar
import org.delcom.pam_p4_ifs23001.ui.screens.HomeScreen
import org.delcom.pam_p4_ifs23001.ui.screens.PlantsAddScreenpc
import org.delcom.pam_p4_ifs23001.ui.screens.PlantsDetailScreen
import org.delcom.pam_p4_ifs23001.ui.screens.PlantsDetailScreenpc
import org.delcom.pam_p4_ifs23001.ui.screens.PlantsEditScreenpc
import org.delcom.pam_p4_ifs23001.ui.screens.PlantsScreen
import org.delcom.pam_p4_ifs23001.ui.screens.PlantsScreenpc
import org.delcom.pam_p4_ifs23001.ui.screens.ProfileScreen
import org.delcom.pam_p4_ifs23001.ui.viewmodels.PlantViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UIApp(
    navController: NavHostController = rememberNavController(),
    plantViewModel: PlantViewModel
) {
    // Inisialisasi SnackbarHostState
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState){ snackbarData ->
            CustomSnackbar(snackbarData, onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() })
        } },
    ) { _ ->
        NavHost(
            navController = navController,
            startDestination = ConstHelper.RouteNames.Home.path,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F8FA))

        ) {
            // Home
            composable(
                route = ConstHelper.RouteNames.Home.path,
            ) { _ ->
                HomeScreen(
                    navController = navController,
                )
            }

            // Profile
            composable(
                route = ConstHelper.RouteNames.Profile.path,
            ) { _ ->
                ProfileScreen(
                    navController = navController,
                    plantViewModel = plantViewModel
                )
            }

            // Plants (Data Statis)
            composable(
                route = ConstHelper.RouteNames.Plants.path,
            ) { _ ->
                PlantsScreen(
                    navController = navController
                )
            }

            // Plants Detail (Data Statis)
            composable(
                route = ConstHelper.RouteNames.PlantsDetail.path,
                arguments = listOf(
                    navArgument("plantId") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val plantId = backStackEntry.arguments?.getString("plantId") ?: ""

                PlantsDetailScreen(
                    navController = navController,
                    plantId = plantId
                )
            }

            // PC (Data API)
            composable(
                route = ConstHelper.RouteNames.Plantspc.path,
            ) { _ ->
                PlantsScreenpc(
                    navController = navController,
                    plantViewModel = plantViewModel
                )
            }
            
            // PC Add
            composable(
                route = ConstHelper.RouteNames.PlantsAddpc.path,
            ) { _ ->
                PlantsAddScreenpc(
                    navController = navController,
                    snackbarHost = snackbarHostState,
                    plantViewModel = plantViewModel
                )
            }
            
            // PC Detail
            composable(
                route = ConstHelper.RouteNames.PlantsDetailpc.path,
                arguments = listOf(
                    navArgument("plantId") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val plantId = backStackEntry.arguments?.getString("plantId") ?: ""

                PlantsDetailScreenpc(
                    navController = navController,
                    snackbarHost = snackbarHostState,
                    plantViewModel = plantViewModel,
                    plantId = plantId
                )
            }

            // PC Edit
            composable(
                route = ConstHelper.RouteNames.PlantsEditpc.path,
                arguments = listOf(
                    navArgument("plantId") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val plantId = backStackEntry.arguments?.getString("plantId") ?: ""

                PlantsEditScreenpc(
                    navController = navController,
                    snackbarHost = snackbarHostState,
                    plantViewModel = plantViewModel,
                    plantId = plantId
                )
            }
        }
    }
}

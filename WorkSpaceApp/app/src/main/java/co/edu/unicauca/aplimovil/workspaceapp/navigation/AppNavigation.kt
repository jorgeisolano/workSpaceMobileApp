package co.edu.unicauca.aplimovil.workspaceapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import co.edu.unicauca.aplimovil.workspaceapp.screens.*

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.InitialScreen.route, builder ={
        composable(route=AppScreens.InitialScreen.route){
            InitialScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = AppScreens.DetailScreen.route){
            DetailScreen(navController)
        }
        composable(route = AppScreens.ReservationScreen.route){
            ReservationScreen(navController)
        }
        composable(route = AppScreens.MapScreen.route){
            MapScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
    } )
}
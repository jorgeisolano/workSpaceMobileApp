package co.edu.unicauca.aplimovil.workspaceapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.screens.*
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController : NavHostController){

    NavHost(navController = navController, startDestination = AppScreens.InitialScreen.route, builder ={
        composable(route=AppScreens.InitialScreen.route){
            InitialScreen(navController)
        }
        composable(route = AppScreens.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = AppScreens.DetailScreen.route + "/{place}",
        arguments = listOf(navArgument(name="place"){
            type = NavType.StringType
        })
        ){ backStackEntry ->
            val placeAsJson = backStackEntry.arguments?.getString("place")
            val placeS = Gson().fromJson(placeAsJson, Place::class.java)
            DetailScreen(navController, placeS)
        }
        composable(route =  AppScreens.ReservationScreen.route + "/{place}",
            arguments = listOf(navArgument(name="place"){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val placeAsJson = backStackEntry.arguments?.getString("place")
            val placeS = Gson().fromJson(placeAsJson, Place::class.java)
            ReservationScreen(navController, placeS)
        }
        composable(route = AppScreens.MapScreen.route){
            MapScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route=AppScreens.BookingsScreen.route){
            BookingsScreen(navController)
        }
        composable(route=AppScreens.FavoritesScreen.route){
            FavoritesScreen(navController)
        }
        composable(route=AppScreens.ProfileScreen.route){
            ProfileScreen(navController)
        }
    } )
}
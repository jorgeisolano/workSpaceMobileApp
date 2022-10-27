package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun HomeScreen(navController: NavController){
    Scaffold {
        HomeBodyContent(navController)
    }
}

@Composable
fun HomeBodyContent(navController: NavController){
    Column(){
        Text(text = "Screen Home")
        Button(onClick = { navController.navigate(route= AppScreens.DetailScreen.route)}) {
            Text(text = "Ir a detalles")
        }
        Button(onClick = { navController.navigate(route=AppScreens.MapScreen.route) }) {
            Text(text = "Ir a Mapa")
        }
    }
}

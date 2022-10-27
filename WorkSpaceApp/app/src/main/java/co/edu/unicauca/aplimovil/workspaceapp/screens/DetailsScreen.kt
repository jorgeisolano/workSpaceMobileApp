package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun DetailScreen(navController: NavController){
    Scaffold {
        DetailBodyContent(navController)
    }
}

@Composable
fun DetailBodyContent(navController: NavController){
    Column(){
        Text(text = "Screen Details")
        Button(onClick = { navController.navigate(route= AppScreens.ReservationScreen.route) }) {
            Text(text = "Ir a Reservar")
        }
    }
}
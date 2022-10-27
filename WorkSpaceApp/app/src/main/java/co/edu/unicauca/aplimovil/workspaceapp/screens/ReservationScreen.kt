package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun ReservationScreen(navController: NavController){
    Scaffold {
        ReservationBodyContent(navController)
    }
}

@Composable
fun ReservationBodyContent(navController: NavController){
    Column(){
        Text(text = "Screen Reservations")
        Button(onClick = { navController.navigate(route= AppScreens.LoginScreen.route)}) {
            Text(text = "Ir a Login")
        }
    }
}
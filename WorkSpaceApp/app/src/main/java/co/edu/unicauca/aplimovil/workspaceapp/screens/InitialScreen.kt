package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun InitialScreen(navController:NavController){
    Scaffold {
        BodyContent(navController)
    }
}

@Composable
fun BodyContent(navController:NavController){
    Column(){
        Text(text = "Screen Inicial")
        Button(onClick = { navController.navigate(route=AppScreens.HomeScreen.route)}) {
            Text(text = "Ir a Home")
        }
    }
}


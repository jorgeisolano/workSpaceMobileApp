package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun LoginScreen(navController: NavController){
    Scaffold {
        LoginBodyContent(navController)
    }
}

@Composable
fun LoginBodyContent(navController: NavController){
    Column(){
        Text(text = "Screen Login")
        Button(onClick = { navController.navigate(route= AppScreens.HomeScreen.route) }) {
            Text(text = "Ir a Home")
        }
    }
}
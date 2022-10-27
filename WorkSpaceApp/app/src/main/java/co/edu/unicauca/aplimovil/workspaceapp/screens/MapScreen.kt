package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun MapScreen(navController: NavController){
    Scaffold {
       MapBodyContent(navController)
    }
}

@Composable
fun MapBodyContent(navController: NavController){
    Column(){
        Text(text = "Screen Map")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Navegar")
        }
    }
}
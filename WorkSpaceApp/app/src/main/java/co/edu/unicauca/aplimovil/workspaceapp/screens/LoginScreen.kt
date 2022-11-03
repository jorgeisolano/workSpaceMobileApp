package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun LoginScreen(navController: NavController){
    val nav=navController
    Scaffold {
        LoginBodyContent(navController)

    }
}

@Composable
fun LoginBodyContent(navController: NavController){
    Column(){
        TopBar()
        Text(text = "Screen Login")
        Button(onClick = { navController.navigate(route= AppScreens.HomeScreen.route) }) {
            Text(text = "Ir a Home")
        }
    }
}
@Preview
@Composable
fun PreviewContent(){
    TopBar()

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    CenterAlignedTopAppBar(
        title = {
            Text(
                "¡Bienvenido!",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Localized description"
                )
            }
        }

    )
}




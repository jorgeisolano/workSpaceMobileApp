package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Azul
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Blanco

private var favoriteList: MutableList<Place> = ArrayList()

@Composable
fun FavoritesScreen(navController: NavController){

    Scaffold(
      topBar = {topBar(navController)},
      content = {
          FavoritesBodyContent(navController)
      }
    )
}

@Composable
fun topBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(text = "Favoritos", color = Azul)
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, "backIcon", tint = Azul)
            }
        },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = Blanco,
        elevation = 10.dp
    )
}

@Composable
fun favoriteCardPlace(navController: NavController){

}

@Composable
fun FavoritesBodyContent(navController: NavController){

}
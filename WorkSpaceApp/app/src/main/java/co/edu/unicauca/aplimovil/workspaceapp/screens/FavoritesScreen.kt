package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.models.FavoritePlace
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Azul
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Blanco
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisClaro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisOscuro
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.orm.SugarRecord

private var favoriteList: MutableList<FavoritePlace> = ArrayList()
private var currentUser = FirebaseAuth.getInstance().currentUser?.email

@Composable
fun FavoritesScreen(navController: NavController){
    var auxFavPlace = FavoritePlace()
    favoriteList = auxFavPlace.getFavoritePlaces(currentUser!!)
    Scaffold(
      topBar = {topBar(navController)},
      content = {
          FavoritesBodyContent(favoriteList,navController)
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
        backgroundColor = Blanco,
        contentColor = Blanco,
        elevation = 10.dp
    )
}
private fun handleClickFavoriteIcon(place : Place) : Int {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email.toString()
    println("EMAIL " + currentUserEmail)
    if(currentUserEmail != null){
        var favoritePlace = FavoritePlace()
        var flag = favoritePlace.isFavoriteSaved(currentUserEmail,place)
        favoritePlace.userEmail = currentUserEmail
        favoritePlace.favoritePlace = place
        if(flag){
            favoritePlace.delete()
            return 1
        }else{
            favoritePlace.save()
            return 0
        }
    }
    return -1
}

@Composable
fun favoriteCardPlace(place: FavoritePlace, navController: NavController){
    val placeJson = Uri.encode(Gson().toJson(place))
    val favoritePlaceSelected = remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp))
    {
        Text(text = place.favoritePlace?.name.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Azul)
        Box() {
            Image(
                painter = painterResource(id = co.edu.unicauca.aplimovil.workspaceapp.R.drawable.ic_launcher_background),
                contentDescription = "Andy Rubin",
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(15))
                    .clickable(onClick = {
                        navController.navigate(AppScreens.DetailScreen.route + "/" + placeJson)
                    })
            )
        }
        Column() {
            Text(text = place.favoritePlace?.city.toString() + " • Colombia", fontSize = 12.sp, color = Azul)
            Text(text = place.favoritePlace?.address.toString(),
                fontSize = 12.sp,
                color = GrisOscuro,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun gridFavoritePlaces(favoriteList: MutableList<FavoritePlace>, navController: NavController) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        content = {
            items(favoriteList) { place ->
                favoriteCardPlace(place = place, navController)
            }
        }
    )
}

@Composable
fun FavoritesBodyContent(favoriteList: MutableList<FavoritePlace>, navController: NavController){
    Column(modifier = Modifier.fillMaxHeight()) {
        Divider(color = GrisClaro, modifier = Modifier
            .fillMaxWidth()
            .height(4.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp)
        ) {
            gridFavoritePlaces(favoriteList = favoriteList, navController)
        }
    }
}
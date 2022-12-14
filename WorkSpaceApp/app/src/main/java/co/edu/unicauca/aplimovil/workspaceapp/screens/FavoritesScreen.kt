package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.models.FavoritePlace
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.*
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.orm.SugarRecord
import com.orm.query.Condition
import com.orm.query.Select


private var favoriteList = mutableStateListOf<FavoritePlace>()

@Composable
fun FavoritesScreen(navController: NavController){
    var currentUser = FirebaseAuth.getInstance().currentUser?.email
    Scaffold(
      topBar = {topBar(navController)},
    ){
        if(currentUser == null){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Debes iniciar sesión si deseas observar tus lugares favoritos",
                    textAlign = TextAlign.Center,fontSize = 19.sp, color = GrisOscuro
                )
            }
        }else{
            val query = SugarRecord.find(FavoritePlace::class.java,"user_Email = ?", currentUser.toString())
            favoriteList = remember { query.toMutableStateList() }
            //elements = favoriteList as SnapshotStateList<FavoritePlace>
            FavoritesBodyContent(favoriteList,navController)
        }
    }
}

@Composable
fun topBar(navController: NavController) {
    Column {
        TopAppBar(backgroundColor = Color.White) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Favoritos", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Azul)
            }

        }
    }
}
private fun onClickFavoriteIcon(place : Place): Boolean {
    var response = getFavoritePlace(place)
    if (response == null) return false
    var objFavoritePlace = SugarRecord.findById(FavoritePlace::class.java, response.id)
    objFavoritePlace.delete()
    val favorite = favoriteList.find { it.favoritePlace?.id == objFavoritePlace.favoritePlace?.id }
    favoriteList.remove(favorite)
    return true
}

private fun getFavoritePlace(place: Place): FavoritePlace? {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    return Select.from(FavoritePlace::class.java).
    where(Condition.prop("favorite_place").eq(place.id.toString()),
        Condition.prop("user_email").eq(currentUserEmail.toString())).first()
}

@Composable
fun favoriteCardPlace(place: FavoritePlace, navController: NavController){
    val placeJson = Uri.encode(Gson().toJson(place.favoritePlace))
    val context = LocalContext.current
    val painter =
        rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
            .data(place.favoritePlace?.image).size(
                Size.ORIGINAL).build())
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp))
    {
        Text(text = place.favoritePlace?.name.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Azul)
        Box() {
            Image(
                painter = painter,
                contentDescription = "Andy Rubin",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(15))
                    .clickable(onClick = {
                        println("JSON$placeJson")
                        navController.navigate(AppScreens.DetailScreen.route + "/" + placeJson)
                    })
            )
            Icon(imageVector = Icons.Filled.Favorite, "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(20.dp, 20.dp)
                    .background(Color.LightGray, RoundedCornerShape(5.dp))
                    .padding(5.dp)
                    .clickable(onClick = {
                        var response = onClickFavoriteIcon(place.favoritePlace!!)
                        when (response) {
                            true -> {
                                showMessage(context, "El lugar ha sido eliminado de favoritos")
                            }
                            false -> {
                                showMessage(context, "Error al eliminar de favoritos")
                            }
                        }
                    }),
                tint = Verde)
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
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 15.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "A continuación podrás observar tus lugares marcados como favoritos",
                textAlign = TextAlign.Center,fontSize = 19.sp, color = GrisOscuro
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp)
        ) {
            gridFavoritePlaces(favoriteList = favoriteList, navController)
        }
    }
}

private fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
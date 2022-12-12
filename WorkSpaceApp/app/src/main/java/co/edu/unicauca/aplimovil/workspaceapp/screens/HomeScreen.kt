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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.models.FavoritePlace
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.orm.SugarRecord
import com.orm.query.Condition
import com.orm.query.Select
import kotlin.reflect.typeOf

private var placeList: MutableList<Place> = ArrayList()
//private lateinit var currentUserEmail : String

@Composable
fun HomeScreen(navController: NavController) {
    placeList = SugarRecord.listAll(Place::class.java)
    //currentUserEmail = FirebaseAuth.getInstance().currentUser?.email!!
    Scaffold(
        content = { HomeBodyContent(navController = navController, placeList) })
}


@Composable
fun searchField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Row(modifier = Modifier.fillMaxWidth()) {
        //Icon(painter = painterResource(id = R.drawable.ic_baseline_search_24), contentDescription = null)
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30)),
            value = text,
            onValueChange = { text = it },
            label = { Text("Buscar") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Filled.FilterList, contentDescription = null) },
        )

    }
}


@Composable
fun categoriesSelector() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Categorías", fontWeight = FontWeight.SemiBold, fontSize = 28.sp, color = Azul)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Verde),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(text = "Todos", color = Blanco)
            }
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = GrisClaro),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(text = "Populares", color = GrisOscuro)
            }
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = GrisClaro),
                shape = RoundedCornerShape(40.dp)) {
                Text(text = "Más cercanos", color = GrisOscuro)
            }
        }
    }
}

private fun handleClickFavoriteIcon(place : Place): Int {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    if(currentUserEmail.equals(null)) return -1
    var response =  verifySelectedFavoritePlace(place)
    if(response == null){
        var newFavoritePlace = FavoritePlace(userEmail = currentUserEmail.toString(), favoritePlace = place)
        newFavoritePlace.save()
        return 0
    }
    var objFavoritePlace = SugarRecord.findById(FavoritePlace::class.java, response.id)
    objFavoritePlace.delete()
    return 1
}

private fun verifySelectedFavoritePlace(place: Place): FavoritePlace? {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
    return Select.from(FavoritePlace::class.java).
                    where(Condition.prop("favorite_place").eq(place.id.toString()),
                    Condition.prop("user_email").eq(currentUserEmail.toString())).first()
}

@Composable
fun cardPlace(place: Place, navController: NavController) {

    val placeJson = Uri.encode(Gson().toJson(place))
    val value = verifySelectedFavoritePlace(place) != null
    val favoritePlaceSelected = remember { mutableStateOf(value)}
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp))
    {
        Text(text = place.name.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Azul)
        Box() {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Andy Rubin",
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(15))
                    .clickable(onClick = {
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
                        var response = handleClickFavoriteIcon(place)
                        println("Respuesta" + response)
                        when (response) {
                            -1 -> {
                                showMessage(context, "Error al añadir a favoritos, por favor inicia sesión")
                            }
                            0 -> {
                                showMessage(context, "El lugar ha sido agregado a favoritos")
                                favoritePlaceSelected.value = !favoritePlaceSelected.value
                            }
                            1 -> {
                                showMessage(context, "El lugar ha sido eliminado de favoritos")
                                favoritePlaceSelected.value = !favoritePlaceSelected.value
                            }
                        }
                    }),
                tint = if(favoritePlaceSelected.value)Color.Red else Blanco)
        }
        Column() {
            Text(text = place.city.toString() + " • Colombia", fontSize = 12.sp, color = Azul)
            Text(text = place.address.toString(),
                fontSize = 12.sp,
                color = GrisOscuro,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun gridPlaces(placeList: MutableList<Place>, navController: NavController) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        content = {
            items(placeList) { place ->
                cardPlace(place = place, navController)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopFixedElements() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(20.dp, 20.dp)
    ) {
        searchField()
        categoriesSelector()
    }
}

@Composable
fun HomeBodyContent(navController: NavController, placeList: MutableList<Place>) {
    Column(modifier = Modifier.fillMaxHeight()) {
        TopFixedElements()
        Divider(color = GrisClaro, modifier = Modifier
            .fillMaxWidth()
            .height(4.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp)
        ) {
            gridPlaces(placeList = placeList, navController)
        }
    }

}

private fun showMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
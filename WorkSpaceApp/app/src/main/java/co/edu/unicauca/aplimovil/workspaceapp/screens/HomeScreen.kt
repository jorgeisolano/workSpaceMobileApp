package co.edu.unicauca.aplimovil.workspaceapp.screens


import android.content.Context
import android.net.Uri
import android.widget.Toast
import android.Manifest
import android.location.Location
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.models.FavoritePlace
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.*
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.orm.SugarRecord
import com.orm.query.Condition
import com.orm.query.Select
import kotlin.reflect.typeOf

private var placeList: MutableList<Place> = ArrayList()

@Composable
fun HomeScreen(navController: NavController) {
    placeList =SugarRecord.listAll(Place::class.java)
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
fun categoriesSelector(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var cercanos by remember { mutableStateOf(false) }
        val colorT = if (cercanos) GrisClaro else Verde
        val colorC = if (cercanos) Verde else GrisClaro
        val textT = if (cercanos) GrisOscuro else Blanco
        val textC = if (cercanos) Blanco else GrisOscuro
        Text(text = "Categorías", fontWeight = FontWeight.SemiBold, fontSize = 28.sp, color = Azul)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {cercanos=false },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorT),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(text = "Todos", color = textT)
            }
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = GrisClaro),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(text = "Populares", color = GrisOscuro)
            }
              Button(onClick = { cercanos = true },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorC),
                shape = RoundedCornerShape(40.dp)) {
                Text(text = "Más cercanos", color = textC)
            }
        }
        if (cercanos) {
            LocationPermissions(navController);
        }

    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissions(navController: NavController?) {
    val openDialog = remember { mutableStateOf(true) }
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val allPermissionsRevoked =
            permissionStates.permissions.size ==
                    permissionStates.revokedPermissions.size
        if (!allPermissionsRevoked) {
            if (isLocationEnabled()) {
                var location = rememberSaveable {
                    mutableStateOf(Location(null))
                }
                getLocation(onValueChange = {
                    location.value = it
                })
                //Text("Permisos dados")
                if (location.value.latitude != 0.0 && location.value.longitude != 0.0) {
                    var placeL = SugarRecord.listAll(Place::class.java)
                    var nearPlaceList = nearPlaces(location = location.value, placeL)
                    Column(modifier = Modifier.fillMaxHeight()) {
                        Divider(color = GrisClaro, modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 20.dp)
                        ) {
                            gridPlaces(placeList = nearPlaceList, navController!!)
                        }
                    }
                }
            } else {
                dialogMessage(openDialog = openDialog,
                    text = "Habilita la ubicación de tu dispositivo desde configuraciones",
                    navController)
            }

        } else if (permissionStates.shouldShowRationale) {
            //dialogMessage(openDialog = openDialog, text = "Por favor cambia la configuracion de la ubicacion a una más exacta")

        } else {
            if (permissionStates.revokedPermissions.size == 2) {
                dialogMessage(openDialog = openDialog,
                    text = "Para acceder a esta funcionalidad permite el uso de la ubicación desde la configuracion de tu dispositivo",
                    navController)

            }
        }

    }
}

fun nearPlaces(location: Location, listPlaces: MutableList<Place>): MutableList<Place> {
    var resPlaces: MutableList<Place> = mutableListOf()
    for (item in listPlaces) {
        var distance: FloatArray = floatArrayOf(0F,0F,0F);
        Location.distanceBetween(location.latitude,
            location.longitude,
            item.lat!!,
            item.long!!,
            distance)
        if (distance[0] < 10000.0) {
            resPlaces.add(item)
        }
    }
    return resPlaces
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
    val painter =
        rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
            .data(place.image).size(
                Size.ORIGINAL).build())
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp))
    {
        Text(text = place.name.toString(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Azul,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Box() {
            Image(
                painter = painter,
                contentDescription = "Andy Rubin",
                contentScale = ContentScale.Crop,
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
                tint = if (favoritePlaceSelected.value) Color.Red else Blanco)
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

@Composable
fun TopFixedElements(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(20.dp, 20.dp)
    ) {
        searchField()
        categoriesSelector(navController)
    }
}

@Composable
fun HomeBodyContent(navController: NavController, placeList: MutableList<Place>) {
    Column(modifier = Modifier.fillMaxHeight()) {
        TopFixedElements(navController)
        Divider(color = GrisClaro, modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))
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
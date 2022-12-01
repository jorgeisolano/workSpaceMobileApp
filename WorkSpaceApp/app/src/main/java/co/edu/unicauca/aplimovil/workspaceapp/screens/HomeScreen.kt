package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.content.res.Resources.Theme
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navArgument
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.*
import com.google.gson.Gson
import com.orm.SugarRecord

private var placeList: MutableList<Place> = ArrayList()


@Composable
fun HomeScreen(navController: NavController) {
    placeList = SugarRecord.listAll(Place::class.java)
    Scaffold(
        //topBar = { topBar()},
        content = { HomeBodyContent(navController = navController, placeList) })
}

@Composable
fun topBar() {
    TopAppBar(
        title = {
            Text(text = "Inicio")
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = Color.White,
        elevation = 10.dp
    )
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
            trailingIcon = { Icon(Icons.Filled.List, contentDescription = null) },
        )

    }
}


@Composable
fun categoriesSelector() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Categorías", fontWeight = FontWeight.SemiBold, fontSize = 28.sp, color = Azul)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Verde),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "Todos", color = Blanco)
            }
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = GrisClaro),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(text = "Populares", color = GrisOscuro)
            }
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = GrisClaro),
                shape = RoundedCornerShape(30.dp)) {
                Text(text = "Más cercanos", color = GrisOscuro)
            }
        }
    }
}

@Composable
fun cardPlace(place: Place, navController: NavController) {
    val placeJson = Uri.encode(Gson().toJson(place))
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
            .clickable(onClick = {
                navController.navigate(AppScreens.DetailScreen.route + "/" + placeJson)
                //navController.navigate(AppScreens.DetailScreen.route)
            }))
    {
        Text(text = place.name.toString(), fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Azul)
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Andy Rubin",
            modifier = Modifier
                .size(170.dp)
                .clip(RoundedCornerShape(15)) // clip to the circle shape
        )
        Column() {
            Text(text = place.city.toString() + " • Colombia", fontSize = 12.sp, color = Azul)
            Text(text = place.address.toString(), fontSize = 12.sp, color = GrisOscuro, maxLines = 1, overflow = TextOverflow.Ellipsis)
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
//    Column(
//        verticalArrangement = Arrangement.SpaceEvenly,
//        modifier = Modifier
//            .fillMaxHeight()
//            .padding(20.dp, 5.dp)) {
//    }

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
package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.models.Amenities
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.models.Schedule
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisOscuro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Verde
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.WorkSpaceAppTheme
import coil.compose.*
import coil.request.ImageRequest
import coil.size.Size
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun DetailScreen(navController: NavController, place: Place?){
    lateinit var objPlace:Place
    place?.let {
        objPlace = it
    }
        Scaffold(
            topBar = { DetailsTopBar(navController,objPlace.name!!) }
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                CardPhoto(objPlace.image!!,objPlace.city!!)
                PlaceDescription(objPlace)
                ReservationButton(navController)
            }

        }


}

/*Cambiar a nav cotroller a obligatorio*/
@Composable
fun DetailsTopBar(navController: NavController?,placeName:String){
    Column {
        TopAppBar(backgroundColor = Color.White){
            Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(33.dp)
                    .clickable { navController?.popBackStack() })
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = placeName , fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }

            /*modifier = Modifier.offset(x = 115.dp)*/
        }
    }

}

@Composable
fun CardPhoto(imageUrl:String,city:String){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)
        .paddingFromBaseline(120.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .size(Size.ORIGINAL)
                .build()
        )
        /*
        if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
            CircularProgressIndicator(color =Verde)
        }*/

       //if (painter.state is AsyncImagePainter.State.Success) {
            // This will be executed during the first composition if the image is in the memory cache.

            Image(
                painter = painter,
                contentDescription ="Imagen del coworking",
                modifier = Modifier
                    .clip(RoundedCornerShape(35.dp))
                    .aspectRatio(15f / 9f, false)
            )
        //}


        Text(text = "$city - Colombia", modifier = Modifier.paddingFromBaseline(top=30.dp), fontSize = 15.sp, color = GrisOscuro)
    }

}


@Composable
fun PlaceDescription(place: Place?){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {
        Text(place?.description!!,
            textAlign = TextAlign.Justify)

        Subtitles(texto = "Dirección")
        Text(text = place.address!!,modifier = Modifier.paddingFromBaseline(top=30.dp))
        Subtitles(texto = "Horario")
        Schedule(place.get_Schedules())
        Subtitles(texto = "Comodidades")
        Comodities(place.get_Amenities())
        Subtitles(texto = "Ubicación")
        Mymap(place.lat!!,place.long!!,place.name!!)

    }

}


@Composable
fun Schedule(listSchedule: MutableList<Schedule>){

        for (schedule in listSchedule){
            Row(modifier = Modifier.padding(top=8.dp)) {
                    Text(schedule.day!!)
                    Spacer(Modifier.size(20.dp))
                    Text(text = schedule.start_hour!! + " - " + schedule.end_hour!!)
            }

        }


}

@Composable
fun Comodities(listAmenities:MutableList<Amenities>){
    Column(modifier = Modifier.paddingFromBaseline(top=30.dp)) {
        for(amenity in listAmenities){
            Row() {
                Icon(
                    painter = painterResource(amenity.icon!!),
                    contentDescription = "Information",
                    modifier = Modifier.size(23.dp)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(amenity.name!!)
            }
            Spacer(Modifier.size(10.dp))
        }


    }
}

@Composable
fun Mymap(latitude:Double,longitude:Double,placeName: String){
    val marker = LatLng(latitude,longitude)
    val state = MarkerState(position = marker)
    val camaraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(marker,15f)
    }
    GoogleMap(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .padding(top = 20.dp),
        cameraPositionState=camaraPositionState){
        Marker(state=state, title = placeName)
    }
}

@Composable
fun ReservationButton(navController: NavController?){
    Button(onClick = { navController?.navigate(AppScreens.ReservationScreen.route) },
           colors = ButtonDefaults.buttonColors(backgroundColor = Verde, contentColor = Color.White),
           modifier = Modifier
        .fillMaxWidth()
        .paddingFromBaseline(top = 60.dp)
        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
        .clip(RoundedCornerShape(30.dp))) {
        Text(text = "Reservar", fontSize = 20.sp)
    }
}

@Composable
fun Subtitles(texto : String){
    Text(text = texto, modifier = Modifier.paddingFromBaseline(top=35.dp),fontWeight = FontWeight.Bold, fontSize = 20.sp)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewDetails() {
    WorkSpaceAppTheme {

        Scaffold(
            topBar = { DetailsTopBar(null,"null")}
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                CardPhoto("https://tinkko.com/wp-content/uploads/2021/04/SEDES_MILLA_DE_ORO.jpg","Popayan")
                PlaceDescription(Place("","","","",23.5,23.5,"",""))
                ReservationButton(null)
            }
        }

    }
}
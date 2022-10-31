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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.WorkSpaceAppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun DetailScreen(navController: NavController){

 Scaffold(
        topBar = { DetailsTopBar(navController)}
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            CardPhoto()
            PlaceDescription()
            ReservationButton(navController)
        }

    }

}

/*Cambiar a nav cotroller a obligatorio*/
@Composable
fun DetailsTopBar(navController: NavController?){
    Column() {
        TopAppBar(backgroundColor = Color.White){
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .clickable { navController?.popBackStack() })
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Tinkko", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }

            /*modifier = Modifier.offset(x = 115.dp)*/
        }
    }

}

@Composable
fun CardPhoto(){
    val painter = painterResource(R.drawable.place)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)
        .paddingFromBaseline(230.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painter,
            contentDescription = "Foto del lugar de coworking",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .weight(1f, fill = false)
                .aspectRatio(painter.intrinsicSize.width / painter.intrinsicSize.height)
                .clip(RoundedCornerShape(25.dp))
        )
        Text(text = "Bogota - Colombia", modifier = Modifier.paddingFromBaseline(top=130.dp), fontSize = 15.sp, color = Color.Gray)
    }

}


@Composable
fun PlaceDescription(){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start) {
        Text("En Tinkko encontrarás un espacio moderno pero profesional, con una comunidad dispuesta a ayudarte y facilitar muchos procesos que de otra forma generarían gastos para ti.",
            textAlign = TextAlign.Justify)

        Subtitles(texto = "Dirección")
        Text(text = "Edificio Ecotek Cl. 99 #10-57",modifier = Modifier.paddingFromBaseline(top=30.dp))
        Subtitles(texto = "Horario")
        Schedule()
        Subtitles(texto = "Comodidades")
        Comodities()
        Subtitles(texto = "Ubicación")
        Mymap()

    }

}


@Composable
fun Schedule(){
    Column(modifier = Modifier.paddingFromBaseline(top=30.dp)) {
        Row() {
            Text("Domingo\t\t\t\t\t")
            Text("Cerrado")
        }
        Row() {
            Text("Miercoles\t\t\t\t")
            Text("8 AM - 6 PM")
        }
    }

}

@Composable
fun Comodities(){
    Column(modifier = Modifier.paddingFromBaseline(top=30.dp)) {
        Row() {
            Icon(
                Icons.Default.Call,
                contentDescription = "Information",
                modifier = Modifier.size(23.dp)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Sala de conferencias")
        }
        Spacer(Modifier.size(10.dp))
        Row() {
            Icon(
                Icons.Default.Info,
                contentDescription = "Information",
                modifier = Modifier.size(23.dp)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Sevicio de alimentación")
        }
    }
}

@Composable
fun Mymap(){
    val marker = LatLng(4.6814882,-74.0457162)
    val state = MarkerState(position = marker)
    val camaraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(marker,15f)
    }
    GoogleMap(modifier = Modifier.fillMaxWidth().height(200.dp).padding(top=20.dp),
        cameraPositionState=camaraPositionState){
        Marker(state=state, title = "Marcador lugar")
    }
}

@Composable
fun ReservationButton(navController: NavController?){
    Button(onClick = { navController?.navigate(AppScreens.ReservationScreen.route) }, modifier = Modifier
        .fillMaxWidth()
        .paddingFromBaseline(top = 50.dp)
        .padding(start = 15.dp, end = 15.dp)
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
            topBar = { DetailsTopBar(null)}
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                CardPhoto()
                PlaceDescription()
                ReservationButton(null)
            }
        }

    }
}
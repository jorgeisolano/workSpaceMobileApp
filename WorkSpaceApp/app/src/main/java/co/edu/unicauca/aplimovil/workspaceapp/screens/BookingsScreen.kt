package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.models.Booking
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.models.Sesion
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Azul
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisClaro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisOscuro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.WorkSpaceAppTheme
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.firebase.auth.FirebaseAuth
import com.orm.SugarRecord
import com.orm.SugarRecord.find
import java.util.*


@Composable
fun BookingsScreen(navController: NavController) {
    var email=FirebaseAuth.getInstance().currentUser?.email
    var bookingList= listOf<Booking>()
    if(email!=null){
        bookingList = find(Booking::class.java, "USER_EMAIL = ?", email.toString())
    }
        Scaffold(
            topBar = { BookingsTopBar(navController = navController) }
        ) {
            Column {
                if(email==null){
//                    Column(modifier = Modifier
//                        .fillMaxWidth(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(
//                            text = "Debes iniciar sesión si deseas observar tus reservas realizadas",
//                            textAlign = TextAlign.Center,fontSize = 19.sp
//                        )
//                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, top = 15.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Debes iniciar sesión si deseas observar tus reservas realizadas",
                            textAlign = TextAlign.Center,fontSize = 19.sp, color = GrisOscuro
                        )
                    }
                }else{
                    if(bookingList.isEmpty()){
//                        Column(modifier = Modifier
//                            .fillMaxWidth(),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally) {
//                            Text(
//                                text = "Aqui podrás observar tus reservaciones realizadas",
//                                textAlign = TextAlign.Center,fontSize = 19.sp
//                            )
//                        }
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, top = 15.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Aqui podrás observar tus reservaciones realizadas",
                                textAlign = TextAlign.Center,fontSize = 19.sp, color = GrisOscuro
                            )
                        }
                    }else{
                        BookingBodyContent(bookingList)
                    }
                }

            }
        }


}

@Composable
fun BookingsTopBar(navController: NavController?) {
    Column {
        TopAppBar(backgroundColor = Color.White) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Reservas", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Azul)
            }

        }
    }
}

@Composable
fun BookingBodyContent(bookingList: List<Booking>) {
    InitialText()
    BookingList(bookingList = bookingList)
}

@Composable
fun BookingList( bookingList: List<Booking>) {
  LazyColumn(modifier = Modifier
      .fillMaxSize()
      .paddingFromBaseline(top = 40.dp)) {
        items(bookingList) { placeBooking ->
            bookingCards(placeBooking.place, placeBooking)
        }
    }
}

@Composable
fun InitialText() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 30.dp, end = 30.dp, top = 15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "A continuación podrás observar los espacios donde tienes reservas activas",
            textAlign = TextAlign.Center,fontSize = 19.sp, color = GrisOscuro
        )
    }
}

@Composable
fun bookingCards(place: Place, booking: Booking) {

    val painter =
        rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
            .data(place.image).size(
                Size.ORIGINAL).build())

    Column(modifier = Modifier
        .padding(top = 10.dp)
        .padding(start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.Start) {
        Text(text = place.name.toString(),
            modifier = Modifier.padding(start = 5.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp)

        Row(modifier = Modifier.padding(top = 10.dp)) {
            Image(
                painter = painter,
                contentDescription = "Imagen del coworking",
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(top = 12.dp)
                    .size(110.dp)
                    .clip(RoundedCornerShape(30))
            )
            Column {
                Text(text = "Ubicación",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp))
                Text(text = place.city.toString() + " - " + " Colombia - " + place.address.toString(),
                    modifier = Modifier.padding(start = 20.dp),fontSize = 13.sp)
                Text(text = "Información de reserva",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp))
                Text(text = "Fecha Entrada: " + booking.checkin.toString(),
                    modifier = Modifier.padding(start = 20.dp),fontSize = 13.sp)
                Text(text = "Fecha Salida: " +booking.checkout.toString(),
                    modifier = Modifier.padding(start = 20.dp),fontSize = 13.sp)
                Text(text ="Entrada: " + booking.timeCheckin.toString() + " - " + "Salida: "+ booking.timeCheckout.toString(),
                    modifier = Modifier.padding(start = 20.dp),fontSize = 13.sp)
                Text(text = booking.guest.toString() + " invitados",
                    modifier = Modifier.padding(start = 20.dp),fontSize = 13.sp)
            }

        }


    }
    Divider(color = GrisClaro, modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp)
        .height(5.dp))

}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewBookings() {
    WorkSpaceAppTheme {
        Scaffold(
            topBar = { BookingsTopBar(navController = null) }
        ) {
            BookingBodyContent(listOf())

        }

    }
}

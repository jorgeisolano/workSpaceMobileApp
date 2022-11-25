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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisClaro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.WorkSpaceAppTheme
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import java.util.*

@Composable
fun BookingsScreen(navController: NavController) {
    WorkSpaceAppTheme {
        Scaffold(
            topBar = { BookingsTopBar(navController = navController) }
        ) {
            Column() {
                BookingBodyContent()
            }
        }

    }
}

@Composable
fun BookingsTopBar(navController: NavController?) {
    Column {
        TopAppBar(backgroundColor = Color.White) {
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
                Text(text = "Reservas", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }

        }
    }
}

@Composable
fun BookingBodyContent() {
    InitialText()
    var place = Place("Lugaer sdf",
        "Popayan",
        "fsdfsdfs",
        "Cl. 85 #22A - 39",
        23.2,
        45.0,
        "https://tinkko.com/wp-content/uploads/2021/04/SEDES_MILLA_DE_ORO.jpg",
        "ciera")

    var listBooking: List<Booking> =
        listOf(Booking(Date(), Date(), 2, 2), Booking(Date(), Date(), 1, 1))
    BookingList(place, bookingList = listBooking)
}

@Composable
fun BookingList(place: Place, bookingList: List<Booking>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .paddingFromBaseline(top = 100.dp)) {
        items(bookingList) { placeBooking ->
            bookingCards(place, placeBooking)
        }
    }
}

@Composable
fun InitialText() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "A continuación podrás observar los espacios donde tienes reservas activas",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun bookingCards(place: Place, booking: Booking) {
    val painter =
        rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
            .data(place.image).size(
                Size.ORIGINAL).build())

    Column(modifier = Modifier.padding(top = 10.dp)
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
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(30))
            )
            Column {
                Text(text = "Ubicación",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp))
                Text(text = place.city.toString() + " - " + " Colombia " + place.address.toString(),
                    modifier = Modifier.padding(start = 20.dp),fontSize = 15.sp)
                Text(text = "Información de reserva",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp))
                Text(text = "Sep. 06 - 2022 · 8:00 AM - 11:00 AM",
                    modifier = Modifier.padding(start = 20.dp),fontSize = 15.sp)
                Text(text = booking.guest.toString() + " invitados",
                    modifier = Modifier.padding(start = 20.dp),fontSize = 15.sp)
            }

        }


    }
    Divider(color = GrisClaro, modifier = Modifier
        .fillMaxWidth().padding(top=20.dp)
        .height(5.dp))

}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewBookings() {
    WorkSpaceAppTheme {
        Scaffold(
            topBar = { BookingsTopBar(navController = null) }
        ) {
            BookingBodyContent()

        }

    }
}

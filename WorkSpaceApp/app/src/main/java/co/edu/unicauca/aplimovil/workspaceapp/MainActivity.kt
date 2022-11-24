package co.edu.unicauca.aplimovil.workspaceapp

import android.Manifest
import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.edu.unicauca.aplimovil.workspaceapp.models.*

import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppNavigation
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.screens.MapScreen
import co.edu.unicauca.aplimovil.workspaceapp.screens.components.BottomNavigationBar
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Blanco
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Verde
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.WorkSpaceAppTheme
import com.orm.*
import com.orm.SugarRecord.count
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            WorkSpaceAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                }
//            }
            if (SugarRecord.count<Place>(Place::class.java, null, null) <= 0) {
                loadData()
                //buscar(id)
            }
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    val navigationItems = listOf(
        AppScreens.HomeScreen,
        AppScreens.BookingsScreen,
        //AppScreens.MapScreen,
        AppScreens.FavoritesScreen,
        AppScreens.ProfileScreen
    )
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, items = navigationItems as List<AppScreens>)},
        floatingActionButton =  { MapFloatingButton(navController = navController)},
        floatingActionButtonPosition = FabPosition.End

    ) {
        AppNavigation(navController = navController)
    }
}

@Composable
fun MapFloatingButton(navController: NavController){
    ExtendedFloatingActionButton(
        contentColor = Blanco,
        backgroundColor = Verde,
        onClick = { navController.navigate(AppScreens.MapScreen.route) },
        icon = {Icon(Icons.Filled.Place, contentDescription = "Mapa")},
        text = { Text(text = "Mapa") },
        elevation = FloatingActionButtonDefaults.elevation(4.dp),
        )
}

fun loadData() {
    //Creacion del lugar
    val place = Place(
        "Tinkko",
        "Bogota",
        "En Tinkko encontrarás un espacio moderno pero profesional, con una comunidad dispuesta a ayudarte y facilitar muchos procesos que de otra forma generarían gastos para ti.",
        "Edificio Ecotek Cl. 99 #10-57 cerca del parque de la 93",
        4.6814882,
        -74.0457162,
        "https://tinkko.com/wp-content/uploads/2021/04/SEDES_MILLA_DE_ORO.jpg",
        "Cerrado los sabados"
    )
    val idPlace = place.save()
    //Creacion de los horarios
    val schedule = Schedule("Lunes", "8:00", "12:00")
    schedule.place = place
    schedule.save()
    val schedule2 = Schedule("Martes", "8:00", "12:00")
    schedule2.place = place
    schedule2.save()
    val schedule3 = Schedule("Miercoles", "8:00", "12:00")
    schedule3.place = place
    schedule3.save()
    //Creacion de las comodidades
    val amenitie = Amenities("Wifi", R.drawable.ic_baseline_wifi_24)
    amenitie.place = place
    amenitie.save()
    val amenitie2 = Amenities("Servicio de alimentación", R.drawable.ic_baseline_fastfood_24)
    amenitie2.place = place
    amenitie2.save()
    //Creación de un booking
    val booking = Booking(Date(2022, 11, 1), Date(2022, 11, 1), 3, 3)
    booking.place = place
    booking.save()

    //----------------------------------
    //Creacion del lugar
    val place2 = Place(
        "El caracol",
        "Popayan",
        "¡Bienvenido a nuestro cafe! Este espacio luminoso y acogedor constituye la planta baja de nuestra casa en el centro de Popayán, renovada con mucho cariño.",
        "Cl. 6 #2-31, Centro",
        2.4410834,
        -76.6029984,
        "https://media-cdn.tripadvisor.com/media/photo-p/15/67/ac/b4/welcome-to-caracol-cafe.jpg",
        "Cerrado los sabados"
    )
    place2.save()
    //Creacion de los horarios
    val schedulep2 = Schedule("Lunes", "8:00", "12:00")
    schedulep2.place = place2
    schedulep2.save()
    val schedulep3 = Schedule("Martes", "8:00", "12:00")
    schedulep3.place = place2
    schedulep3.save()
    val schedulep4 = Schedule("Miercoles", "8:00", "12:00")
    schedulep4.place = place2
    schedulep4.save()
    //Creacion de las comodidades
    val amenitiep2 = Amenities("Wifi", R.drawable.ic_baseline_wifi_24)
    amenitiep2.place = place2
    amenitiep2.save()
    val amenitiep3 = Amenities("Servicio de alimentación", R.drawable.ic_baseline_fastfood_24)
    amenitiep3.place = place2
    amenitiep3.save()

    //var sesion=Sesion("","","","","")
    //sesion.save()
}

fun buscar(id: Long) {
    val place = SugarRecord.findById(Place::class.java, id)
    for (element in place.get_Schedules()) {
        println(element.day)
    }
    for (item in place.get_Amenities()) {
        println(item.name)
    }
    for (item in place.get_Bookings()) {
        println(item.checkin)
    }

}



//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    WorkSpaceAppTheme {
//        MainScreen()
//    }
//}
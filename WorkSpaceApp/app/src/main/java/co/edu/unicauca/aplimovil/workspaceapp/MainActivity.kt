package co.edu.unicauca.aplimovil.workspaceapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.edu.unicauca.aplimovil.workspaceapp.models.*
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppNavigation
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.screens.components.BottomNavigationBar
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Blanco
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Verde
import com.orm.*

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
            }
            MainScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    // State of bottomBar, set state to false, if current page route is "car_details"
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navController = rememberNavController()
    val navigationItems = listOf(
        AppScreens.HomeScreen,
        AppScreens.BookingsScreen,
        //AppScreens.MapScreen,
        AppScreens.FavoritesScreen,
        AppScreens.ProfileScreen
    )
    Scaffold(
        bottomBar = {
            val currentRoute = currentRoute(navController = navController)
            val predicate: (AppScreens) -> Boolean = { it.route == currentRoute }
            if (navigationItems.any(predicate)) {
                BottomNavigationBar(navController = navController,
                    items = navigationItems as List<AppScreens>)
            }
        },
        floatingActionButton = {
            if(currentRoute(navController) == "home_screen"){
                MapFloatingButton(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        scaffoldState = scaffoldState

    ) {
        Column(modifier = Modifier.padding(bottom = 50.dp)) {
            AppNavigation(navController)
        }
    }
}

@Composable
public fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun MapFloatingButton(navController: NavController) {
    FloatingActionButton(
        contentColor = Blanco,
        backgroundColor = Verde,
        onClick = { navController.navigate(AppScreens.MapScreen.route) },
        elevation = FloatingActionButtonDefaults.elevation(4.dp),
    ){
        Icon(imageVector = Icons.Filled.MyLocation,"")
    }
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
    place.save()
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
    val booking = Booking("10-10-2022","10-10-2022","10:00","11:00",3,3)
    booking.userEmail="lauraich@unicauca.edu.co"
    booking.place=place
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

    //Creación de un booking
    val booking2 = Booking("11-10-2022","11-10-2022","10:00","11:00",3,3)
    booking2.userEmail="lauraich@unicauca.edu.co"
    booking2.place=place2
    booking2.save()


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
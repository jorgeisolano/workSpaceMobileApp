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
        "https://10619-2.s.cdn12.com/rests/original/109_502454224.jpg",
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

    //Creacion del lugar
    val place3 = Place(
        "Joshua Cafe",
        "Popayan",
        "¡Un lugar muy agradable, tranquilo, limpio y organizado. Con excelente atención y rápido servicio.",
        "Calle 15 Norte #15N-82",
        2.4879445,
        -76.5671511,
        "https://media-cdn.tripadvisor.com/media/photo-p/15/67/ac/b4/welcome-to-caracol-cafe.jpg",
        "Cerrado los Domingos"
    )
    place3.save()
    //Creacion de los horarios
    val schedulepj = Schedule("Lunes", "8:00", "12:00")
    schedulepj.place = place3
    schedulepj.save()
    val schedulep3j = Schedule("Martes", "8:00", "12:00")
    schedulep3j.place = place3
    schedulep3j.save()
    val schedulep4j = Schedule("Miercoles", "8:00", "12:00")
    schedulep4j.place = place3
    schedulep4j.save()
    //Creacion de las comodidades
    val amenitiep2j = Amenities("Wifi", R.drawable.ic_baseline_wifi_24)
    amenitiep2j.place = place3
    amenitiep2j.save()
    val amenitiep3j = Amenities("Servicio de alimentación", R.drawable.ic_baseline_fastfood_24)
    amenitiep3j.place = place3
    amenitiep3j.save()
    val place4 = Place(
        "Togoima",
        "Popayan",
        "¡Bienvenido a nuestro cafe! Este espacio luminoso y acogedor constituye la planta baja de nuestra casa en el norte de Popayán, renovada con mucho cariño.",
        "Cl. 74 Nte. #1-1619",
        2.4860492,
        -76.5648476,
        "https://scontent.fbog10-1.fna.fbcdn.net/v/t39.30808-6/231923377_1503745723307050_27012400248139844_n.jpg?stp=dst-jpg_s640x640&_nc_cat=109&ccb=1-7&_nc_sid=a26aad&_nc_ohc=mu30ln3RS3YAX9_e_q4&_nc_ht=scontent.fbog10-1.fna&oh=00_AfBlzVkykb5bUsCSORYWIG-HMqTqeyRu2jRtqN6ZkENpog&oe=639D0DF1",
        "Cerrado los Domingos"
    )
    place4.save()
    //Creacion de los horarios
    val schedulep2t = Schedule("Lunes", "8:00", "12:00")
    schedulep2t.place = place4
    schedulep2t.save()
    val schedulep3t = Schedule("Martes", "8:00", "12:00")
    schedulep3t.place = place4
    schedulep3t.save()
    val schedulep4t = Schedule("Miercoles", "8:00", "12:00")
    schedulep4t.place = place4
    schedulep4t.save()
    //Creacion de las comodidades
    val amenitiep2t = Amenities("Wifi", R.drawable.ic_baseline_wifi_24)
    amenitiep2t.place = place4
    amenitiep2t.save()
    val amenitiep3t = Amenities("Servicio de alimentación", R.drawable.ic_baseline_fastfood_24)
    amenitiep3t.place = place4
    amenitiep3t.save()

    val place4l = Place(
        "La Castellana Campestre",
        "Popayan",
        "¡Nuestro moderno salón de eventos se encuentra integrado con amplias zonas campestres.\n" +
                "La logística y la organización son el anclaje necesario para que un evento sea de alta calidad. Para ello coordinamos todos los detalles en comida, decoración, música y personal en atención de eventos.",
        "Tv 9A Norte, Popayán, Cauca",
        2.4860492,
        -76.5648476,
        "https://lacastellanacampestre.com/wp-content/uploads/2018/03/12138603_1689236561297928_6139824353403775332_o-1024x683.jpg",
        "Cerrado los Lunes"
    )
    place4l.save()
    //Creacion de los horarios
    val schedulep2tl = Schedule("Jueves", "8:00", "12:00")
    schedulep2tl.place = place4l
    schedulep2tl.save()
    val schedulep3tl = Schedule("Viernes", "8:00", "12:00")
    schedulep3tl.place = place4l
    schedulep3tl.save()
    val schedulep4tl = Schedule("Sabado", "8:00", "12:00")
    schedulep4tl.place = place4l
    schedulep4tl.save()
    //Creacion de las comodidades
    val amenitiep2tl = Amenities("Wifi", R.drawable.ic_baseline_wifi_24)
    amenitiep2tl.place = place4l
    amenitiep2tl.save()
    val amenitiep3tl = Amenities("Servicio de alimentación", R.drawable.ic_baseline_fastfood_24)
    amenitiep3tl.place = place4l
    amenitiep3tl.save()

    val place4la = Place(
        "ÁMBAR Entre Ideas y Café",
        "Popayan",
        "¡Un espacio para encontrarse en compañía de un buen café. Un espacio para trabajar, hacer negocios, disfrutar de buena compañía.",
        "Cra. 9 # 7 - 31",
        2.4313244,
        -76.6222583,
        "https://scontent.fbog11-1.fna.fbcdn.net/v/t1.6435-9/83029729_181396506599579_4105643033058344960_n.jpg?stp=dst-jpg_p526x296&_nc_cat=102&ccb=1-7&_nc_sid=730e14&_nc_ohc=40fH-hS3SYIAX9e1JwA&_nc_ht=scontent.fbog11-1.fna&oh=00_AfC-joz7DOzdWkVWNITlwdVO4mwEQczZ9XxrJFMMebv2qw&oe=63BF58CB",
        "Cerrado los Sabados"
    )
    place4la.save()
    //Creacion de los horarios
    val schedulep2tla = Schedule("Jueves", "8:00", "12:00")
    schedulep2tla.place = place4la
    schedulep2tla.save()
    val schedulep3tla = Schedule("Viernes", "8:00", "12:00")
    schedulep3tla.place = place4la
    schedulep3tla.save()
    val schedulep4tla = Schedule("Sabado", "8:00", "12:00")
    schedulep4tla.place = place4la
    schedulep4tla.save()
    //Creacion de las comodidades
    val amenitiep2tla = Amenities("Wifi", R.drawable.ic_baseline_wifi_24)
    amenitiep2tla.place = place4la
    amenitiep2tla.save()
    val amenitiep3tla = Amenities("Servicio de alimentación", R.drawable.ic_baseline_fastfood_24)
    amenitiep3tla.place = place4la
    amenitiep3tla.save()

    val place4lab = Place(
        "BORÉM",
        "Popayan",
        "¡Espacios muy cómodos para trabajar, un lugar genial para trabajar y un excelente servicio.!",
        "Cra. 11 #16-33, Comuna 1",
        2.4563412,
        -76.6168221,
        "https://scontent.fbog10-1.fna.fbcdn.net/v/t1.6435-9/145628066_285374173006634_9558969542468659_n.jpg?_nc_cat=101&ccb=1-7&_nc_sid=a26aad&_nc_ohc=_1zfNpKpDn0AX-1P_SS&_nc_ht=scontent.fbog10-1.fna&oh=00_AfB3YuWpPLDNHooIEyn6GVudF2J2WKRTSvyz7KRo9rVpIg&oe=63BF37A1",
        "Cerrado los Sabados y Domingo"
    )
    place4lab.save()
    //Creacion de los horarios
    val schedulep2tlab = Schedule("Jueves", "8:00", "12:00")
    schedulep2tlab.place = place4lab
    schedulep2tlab.save()
    val schedulep3tlab = Schedule("Viernes", "8:00", "12:00")
    schedulep3tlab.place = place4lab
    schedulep3tlab.save()
    val schedulep4tlab = Schedule("Sabado", "8:00", "12:00")
    schedulep4tlab.place = place4lab
    schedulep4tlab.save()
    //Creacion de las comodidades
    val amenitiep2tlab = Amenities("Wifi", R.drawable.ic_baseline_wifi_24)
    amenitiep2tlab.place = place4lab
    amenitiep2tlab.save()
    val amenitiep3tlab = Amenities("Servicio de alimentación", R.drawable.ic_baseline_fastfood_24)
    amenitiep3tlab.place = place4lab
    amenitiep3tlab.save()

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
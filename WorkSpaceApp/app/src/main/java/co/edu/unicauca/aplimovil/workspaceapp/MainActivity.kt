package co.edu.unicauca.aplimovil.workspaceapp

import android.Manifest
import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.core.app.ActivityCompat

import co.edu.unicauca.aplimovil.workspaceapp.models.Amenities
import co.edu.unicauca.aplimovil.workspaceapp.models.Booking

import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.models.Schedule
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppNavigation
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.WorkSpaceAppTheme
import com.orm.*
import com.orm.SugarRecord.count
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WorkSpaceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    if (SugarRecord.count<Place>(Place::class.java, null, null) <= 0) {
                        val id = loadData()
                        buscar(id)

                    }

                    AppNavigation()

                }
            }
        }
    }



}

fun loadData(): Long {
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
    val booking = Booking(Date(2022,11,1),Date(2022,11,1),3,3)
    booking.place=place
    booking.save()
    return idPlace
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



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkSpaceAppTheme {
        AppNavigation()
    }
}
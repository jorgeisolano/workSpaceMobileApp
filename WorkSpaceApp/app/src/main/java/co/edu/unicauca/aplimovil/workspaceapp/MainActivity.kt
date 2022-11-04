package co.edu.unicauca.aplimovil.workspaceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.models.Schedule
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppNavigation
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.WorkSpaceAppTheme
import com.orm.*

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
                    /*val place = Place("Jorge", "asd", "asd", "asd", "asd", "asd", "asd")
                    val idPlace = place.save()
                    val schedule = Schedule(idPlace.toInt(), "Lunes", "YA", "Mañana")
                    schedule.place = place
                    schedule.save()*/
                    val place = SugarRecord.findById(Place::class.java,26)
                    println("--------------------")
                    for(schedule in place.get_Schedules()){
                        println("aSD" + schedule.day)
                    }
                    AppNavigation()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkSpaceAppTheme {
        AppNavigation()
    }
}
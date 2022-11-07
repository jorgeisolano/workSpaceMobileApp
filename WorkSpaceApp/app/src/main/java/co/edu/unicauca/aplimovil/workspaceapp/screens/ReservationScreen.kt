package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.models.Sesion
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.orm.SugarRecord

@Composable
fun ReservationScreen(navController: NavController){
    Scaffold {
        ReservationBodyContent(navController)
    }
}
@Preview(showBackground = true)
@Composable
fun Preview(){

}
@Composable
fun ReservationBodyContent(navController: NavController){
    Column(){
        Text(text = "Screen Reservations")
        Button(onClick = { navController.navigate(route= AppScreens.LoginScreen.route)}) {
            Text(text = "Ir a Login")
        }
        Button(onClick = {  }) {
            Text(text = "Log Out")
        }
    }
}
fun LogOut(){
    var sesion:List<Sesion> = SugarRecord.find(Sesion::class.java,"email = ?",FirebaseAuth.getInstance().currentUser?.email)
    if(sesion.isNotEmpty()) {
        sesion[0].delete()
    }
    FirebaseAuth.getInstance().signOut()
}
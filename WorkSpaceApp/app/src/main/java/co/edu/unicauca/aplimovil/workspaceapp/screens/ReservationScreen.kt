package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.models.Sesion
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import com.orm.SugarRecord
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationScreen(navController: NavController){
    ReservationBodyContent(navController)

}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Preview(){
    ReservationBodyContent(null)
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationBodyContent(navController: NavController?){
    Scaffold (
        topBar = { TopBar(navController = navController, title = "Tinkko")}
    ){
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(30.dp))
        {
            Subtitles(texto = stringResource(id = R.string.description_label))
            TextGray(text = "Bogotá - Colombia")
            TextGray(text = "Edificio EcoTeck Cl.99 #10-57 cerca del parque de la 93")
            TextGray(text = stringResource(id = R.string.reservation_day_msg))
            Text(text = "Screen Reservations")
            Subtitles(texto = stringResource(id = R.string.checkin_label))
            val (value ,onValueChange) = rememberSaveable {mutableStateOf("") }
            DatePiker(value,onValueChange)
            Log.d("ACA","fECHA: $value")
            Subtitles(texto = stringResource(id = R.string.checkout_label))
            Button(onClick = { navController?.navigate(route= AppScreens.LoginScreen.route)}) {
                Text(text = "Ir a Login")
            }
            Button(onClick = {  }) {
                Text(text = "Log Out")
            }
        }
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePiker(value:String,onValueChange:(String)->Unit) {
    var localTime=LocalDateTime.now()
    val mContext = LocalContext.current
    var mYear: Int=0
    var mMonth: Int=0
    var mDay: Int = localTime.dayOfMonth
    val mCalendar = Calendar.getInstance()
    mCalendar.time = Date()
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    if(value==""){
        onValueChange("$mDay/${mMonth+1}/$mYear")
    }
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onValueChange("$mDayOfMonth/${mMonth+1}/$mYear")
        }, mYear, mMonth, mDay
    )
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            //Text(text = mDate.value, color = Color.Black)
            OutlinedTextField(
                value = value,

                readOnly = true,
                textStyle = TextStyle(fontSize = 20.sp,fontWeight = FontWeight.Bold),
                onValueChange = { onValueChange(it)},
                trailingIcon ={ Icon(imageVector = Icons.Filled.DateRange, contentDescription ="DatePiker", modifier = Modifier.clickable { mDatePickerDialog.show() }) },
                singleLine=true,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                shape = ShapeDefaults.ExtraLarge
            )
    }

}
@Composable
fun TextGray(text:String){
    Text(text = text,
        fontSize = 20.sp,
        color = Color.Gray
    )
}

fun LogOut(){
    var sesion:List<Sesion> = SugarRecord.find(Sesion::class.java,"email = ?",FirebaseAuth.getInstance().currentUser?.email)
    if(sesion.isNotEmpty()) {
        sesion[0].delete()
    }
    FirebaseAuth.getInstance().signOut()
}
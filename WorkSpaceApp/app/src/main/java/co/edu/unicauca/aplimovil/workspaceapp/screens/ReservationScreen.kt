package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.models.Booking
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Azul
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisClaro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisOscuro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Verde
import com.google.firebase.auth.FirebaseAuth
import com.orm.SugarRecord
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationScreen(navController: NavController, place: Place?){
    lateinit var objPlace:Place
    place?.let {
        objPlace = it
    }
    Log.d("ACA","Id: "+objPlace.id)
    ReservationBodyContent(navController,objPlace)

}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Preview(){
    ReservationBodyContent(null,null)
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationBodyContent(navController: NavController?,place: Place?){
    Scaffold (
        topBar = {
            if (place != null) {
                ReservationTopBar(navController = navController, placeName = place.name!!)
            }
        }
    ){
        Column(verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp, 0.dp))
        {
            Subtitles(texto = stringResource(id = R.string.description_label))
            TextGray(text = place?.city!!)
            TextGray(text = place?.address!!)
            TextGray(text = stringResource(id = R.string.reservation_day_msg))
            Subtitles(texto = stringResource(id = R.string.checkin_label))
            val (valueIn ,onValueChangeIn) = rememberSaveable {mutableStateOf("") }
            DatePiker(valueIn,onValueChangeIn)
            Subtitles(texto = stringResource(id = R.string.checkout_label))
            val (valueOut ,onValueChangeOut) = rememberSaveable {mutableStateOf("") }
            DatePiker(valueOut,onValueChangeOut)
            val (timeValueIn ,onTimeValueChangeIn) = rememberSaveable {mutableStateOf(value="Seleccione hora") }
            val (timeValueOut ,onTimeValueChangeOut) = rememberSaveable {mutableStateOf(value="Seleccione hora") }

            Row(modifier = Modifier.fillMaxWidth()) {
                val context= LocalContext.current
                val width: Int = context.resources.configuration.screenWidthDp
                val (numShairsIn ,numShairsOut) = rememberSaveable {mutableStateOf(value=0) }
                Column(modifier = Modifier.width(((width-60)/2).dp)) {
                    Subtitles(texto = "Hora Entrada")
                    Spacer(modifier = Modifier.size(12.dp) )
                    TimePiker(timeValueIn,onTimeValueChangeIn)
                    Spacer(modifier = Modifier.size(12.dp) )
                    Subtitles(texto = stringResource(id = R.string.shair_label))
                    Spacer(modifier = Modifier.size(12.dp) )
                    selectorNumerico(numShairsIn,numShairsOut)
                    Spacer(modifier = Modifier.size(40.dp) )
                    ButtonCancelar(navController)
                }
                Spacer(modifier = Modifier.size(18.dp) )
                Column(modifier = Modifier.width(((width-60)/2).dp)) {
                    Subtitles(texto = "Hora Salida")
                    Spacer(modifier = Modifier.size(12.dp) )
                    TimePiker(timeValueOut,onTimeValueChangeOut)
                    Spacer(modifier = Modifier.size(12.dp) )
                    Subtitles(texto = stringResource(id = R.string.guests_label))
                    Spacer(modifier = Modifier.size(12.dp) )
                    val (numGuestIn ,numGuestOut) = rememberSaveable {mutableStateOf(value=0) }
                    selectorNumerico(numGuestIn,numGuestOut)
                    var booking=Booking()
                    booking.place=place!!
                    booking.checkin=valueIn
                    booking.checkout=valueOut
                    booking.timeCheckin=timeValueIn
                    booking.timeCheckout=timeValueOut
                    booking.guest=numGuestIn
                    booking.seats=numShairsIn
                    Spacer(modifier = Modifier.size(40.dp) )
                    ButtonReservar(navController, booking)
                }
            }
        }
    }
}

@Composable
fun ReservationTopBar(navController: NavController?,placeName:String){
    Column {
        androidx.compose.material.TopAppBar(backgroundColor = Color.White) {
            androidx.compose.material.Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(33.dp)
                    .clickable { navController?.popBackStack() })
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp, end = 25.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = placeName, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Azul)
            }
            /*modifier = Modifier.offset(x = 115.dp)*/
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selectorNumerico(value:Int,onValueChange:(Int)->Unit){
    Column() {
        OutlinedTextField(
            value = value.toString(),
            readOnly = true,
            textStyle = TextStyle(fontSize = 20.sp,fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = GrisOscuro),
            onValueChange = {
                onValueChange(it.toInt())
            },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.do_not_disturb_on), contentDescription ="Restar", modifier = Modifier
                .clickable { if (value != 0) onValueChange(value - 1) }
                .size(25.dp)) },
            trailingIcon ={ Icon(painter = painterResource(id = R.drawable.add_circle), contentDescription ="Aumentar", modifier = Modifier
                .clickable { onValueChange(value + 1) }
                .size(25.dp)) },
            singleLine=true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            shape = ShapeDefaults.Small
        )
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
                textStyle = TextStyle(fontSize = 16.sp,fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = GrisOscuro),
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
fun reservar(navController: NavController?,booking: Booking){
    if(FirebaseAuth.getInstance().currentUser?.email!=null){
        booking.userEmail=FirebaseAuth.getInstance().currentUser?.email!!
        booking.save()
        navController?.navigate(route= AppScreens.BookingsScreen.route)
    }else{
        navController?.navigate(route= AppScreens.LoginScreen.route)
    }
}
@Composable
fun ButtonReservar(navController: NavController?,booking: Booking){
        Button(
            onClick = { reservar(navController, booking) },
            colors = ButtonDefaults.buttonColors(containerColor = Verde, contentColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp)),

            ) {

            Text(text = stringResource(id = R.string.reserve_label), fontSize = 20.sp, color=Color.White, fontWeight = FontWeight.Bold)
        }
}
@Composable
fun ButtonCancelar(navController: NavController?){
        Button(onClick = { navController?.popBackStack() }, modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp)),
            colors=ButtonDefaults.buttonColors(containerColor = GrisClaro, contentColor = Azul)
        ) {
            Text(text = stringResource(id = R.string.cancel_label), fontWeight = FontWeight.Bold,fontSize = 20.sp, color= Azul)
        }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePiker(value:String,onValueChange:(String)->Unit){
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    val mDatePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            onValueChange("$mHour:$mMinute")
        }, mHour, mMinute, true
    )
    OutlinedTextField(
        value = value,
        readOnly = true,
        textStyle = TextStyle(fontSize = 13.sp,fontWeight = FontWeight.Bold, color = GrisOscuro),
        onValueChange = { onValueChange(it)},
        trailingIcon ={ Icon(painter = painterResource(id = R.drawable.schedule_24px) , contentDescription ="DatePiker", modifier = Modifier
            .clickable { mDatePickerDialog.show() }
            .size(32.dp)) },
        singleLine=true,
        shape = ShapeDefaults.Small
    )
}
@Composable
fun TextGray(text:String){
    Text(text = text,
        fontSize = 15.sp,
        color = GrisOscuro
    )
}


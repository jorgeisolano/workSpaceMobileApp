package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.models.Place
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


private var lati:Double=0.0
private var longi:Double=0.0

@Composable
fun MapScreen(navController: NavController) {
    Scaffold(topBar = { MapTopBar(navController) }) {
        MapBodyContent(navController = navController)
    }
}


@Composable
fun MapTopBar(navController: NavController?) {
    Column() {
        TopAppBar(backgroundColor = Color.White) {
            androidx.compose.material.Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(33.dp)
                    .clickable { navController?.popBackStack() })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 14.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Mapa", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }

        }
    }
}

@Composable
fun MapBodyContent(navController: NavController?) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Encuentra espacios de trabajo cercanos!",
            modifier = Modifier.padding(top = 10.dp)
        )
        checkLocationPermissions(navController)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun checkLocationPermissions(navController: NavController?) {
    val openDialog = remember { mutableStateOf(true) }
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
          val allPermissionsRevoked =
              permissionStates.permissions.size ==
                      permissionStates.revokedPermissions.size
          if (!allPermissionsRevoked) {
              if(isLocationEnabled()){
                  var location =rememberSaveable {
                      mutableStateOf(Location(null))
                  }
                  getLocation(onValueChange = {
                      location.value =it })
                  //Text("Permisos dados")
                  if(location.value.latitude!=0.0 && location.value.longitude!=0.0){
                      var placeList: MutableList<Place>
                      placeList=listOf<Place>(Place("dasd","fsdf","fsdfsd","sdfs",2.4419417,-76.6092564,"fsd"),Place("","","","",2.4416987,-76.6060809,"")) as MutableList<Place>
                      //placeList = nearPlaces(location = location.value,placeList)
                      Mapa(lat = location.value.latitude, long = location.value.longitude,placeList,navController!!)
                  }
              }else{
                  dialogMessage(openDialog = openDialog, text = "Habilita la ubicación de tu dispositivo desde configuraciones",navController)
              }

          } else if (permissionStates.shouldShowRationale) {
              //dialogMessage(openDialog = openDialog, text = "Por favor cambia la configuracion de la ubicacion a una más exacta")

          } else {
              if(permissionStates.revokedPermissions.size==2){
                  dialogMessage(openDialog = openDialog, text = "Para acceder a esta funcionalidad permite el uso de la ubicación desde la configuracion de tu dispositivo",navController)

              }
          }

    }
}

@Composable
fun getLocation(onValueChange:(Location)->Unit){
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)

    if (ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            LocalContext.current,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            (LocalContext.current as Activity?)!!,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            101
        )
    }

        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY,null).addOnSuccessListener {
            onValueChange(it)
        }


}
    @Composable
    fun isLocationEnabled():Boolean{
        val mLocationManager = getSystemService(LocalContext.current,LocationManager::class.java) as LocationManager

        // Checking GPS is enabled
        val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return mGPS
    }


@Composable
fun Mapa(lat:Double,long:Double,nearPlaces:MutableList<Place>,navController: NavController){
    lati=lat
    longi=long
    val location = LatLng(lat,long)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15F)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize().padding(top=15.dp),
        cameraPositionState = cameraPositionState
    ) {
       Marker(state=MarkerState(position = location),icon= BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN), title = "Te encuentras aquí")
       nearPlaces.forEach { 
           place ->
           Marker(
               state= MarkerState(position = LatLng(place.lat!!,place.long!!)),
               title = place.name,
               snippet = "Conocer más",
               onInfoWindowClick = { navController.navigate(route = AppScreens.DetailScreen.route)}
           )
       }
    }

}

@Composable
fun dialogMessage(openDialog:MutableState<Boolean>,text:String,navController: NavController?){

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onCloseRequest.
                openDialog.value = false
            },
            title = {
                Text(text = "Permisos de Ubicación")
            },
            text = {
                Text(
                    text
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        navController?.popBackStack()
                        openDialog.value = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

fun nearPlaces(location:Location,listPlaces:MutableList<Place>):MutableList<Place>{
    var resPlaces :MutableList<Place> = mutableListOf()
    listPlaces.add(Place("dasd","fsdf","fsdfsd","sdfs",2.4419417,-76.6092564,"fsd"))
    for (item in listPlaces){
        var loc = Location(null)
        loc.latitude= item.lat!!
        loc.longitude=item.long!!
        if(location.distanceTo(loc)<10000.0){
            resPlaces.add(item)
        }
    }
    return resPlaces
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    Scaffold(topBar = { MapTopBar(null) }) {
        MapBodyContent(navController = null)
    }
}

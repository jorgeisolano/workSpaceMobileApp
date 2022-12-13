package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Azul
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisOscuro
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.*
import com.google.android.gms.location.Priority.PRIORITY_BALANCED_POWER_ACCURACY
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.*
import com.orm.SugarRecord
import kotlinx.coroutines.launch


private var lati: Double = 0.0
private var longi: Double = 0.0

@Composable
fun MapScreen(navController: NavController) {
    Scaffold(topBar = { MapTopBar(navController) }) {
        MapBodyContent(navController = navController)
    }
}


@Composable
fun MapTopBar(navController: NavController?) {
    Column {
        TopAppBar(backgroundColor = Color.White){
            Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .size(33.dp)
                    .clickable { navController?.popBackStack() })
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 14.dp, end = 25.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Mapa" , fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Azul)
            }
            /*modifier = Modifier.offset(x = 115.dp)*/
        }
    }
}

@Composable
fun MapBodyContent(navController: NavController?) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "¡Encuentra espacios de trabajo cercanos!",
            textAlign = TextAlign.Center,fontSize = 19.sp, color = GrisOscuro
        )
        checkLocationPermissions(navController)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkLocationPermissions(navController: NavController?) {
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
            if (isLocationEnabled()) {
                var location = rememberSaveable {
                    mutableStateOf(Location(null))
                }
                getLocation(onValueChange = {
                    location.value = it
                })
                //Text("Permisos dados")
                if (location.value.latitude != 0.0 && location.value.longitude != 0.0) {
                    var placeList = SugarRecord.listAll(Place::class.java)
                    //placeList = nearPlaces(location = location.value,placeList)
                    Mapa(lat = location.value.latitude,
                        long = location.value.longitude,
                        placeList,
                        navController!!)
                }
            } else {
                dialogMessage(openDialog = openDialog,
                    text = "Habilita la ubicación de tu dispositivo desde configuraciones",
                    navController)
            }

        } else if (permissionStates.shouldShowRationale) {
            //dialogMessage(openDialog = openDialog, text = "Por favor cambia la configuracion de la ubicacion a una más exacta")

        } else {
            if (permissionStates.revokedPermissions.size == 2) {
                dialogMessage(openDialog = openDialog,
                    text = "Para acceder a esta funcionalidad permite el uso de la ubicación desde la configuracion de tu dispositivo",
                    navController)

            }
        }

    }
}

@Composable
fun getLocation(onValueChange: (Location) -> Unit) {
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
    //PRIORITY_HIGH_ACCURACY
    fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
        .addOnSuccessListener {
            onValueChange(it)
        }


}

@Composable
fun isLocationEnabled(): Boolean {
    val mLocationManager =
        getSystemService(LocalContext.current, LocationManager::class.java) as LocationManager

    // Checking GPS is enabled
    val mGPS = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    return mGPS
}


@Composable
fun Mapa(lat: Double, long: Double, nearPlaces: MutableList<Place>, navController: NavController) {
    lati = lat
    longi = long
    val location = LatLng(lat, long)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 15F)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        cameraPositionState = cameraPositionState
    ) {

        Marker(state = MarkerState(position = location),
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN),
            title = "Te encuentras aquí")
        lateinit var placeJson: String
        for (num in 0 until nearPlaces.size) {
            val coroutine = rememberCoroutineScope()
            Marker(
                state = MarkerState(position = LatLng(nearPlaces[num].lat!!,
                    nearPlaces[num].long!!)),
                title = nearPlaces[num].name,
                snippet = "Conocer más",
                onInfoWindowClick = {
                    placeJson = Uri.encode(Gson().toJson(nearPlaces[num]))
                    coroutine.launch { navController.navigate(route = AppScreens.DetailScreen.route + "/" + placeJson) }
                }

            )

        }
    }

}

@Composable
fun dialogMessage(openDialog: MutableState<Boolean>, text: String, navController: NavController?) {

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
                        navController?.popBackStack()
                        openDialog.value = false
                    }
                ) {
                    Text("Cerrar")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MapPreview() {
    Scaffold(topBar = { MapTopBar(null) }) {
        MapBodyContent(navController = null)
    }
}

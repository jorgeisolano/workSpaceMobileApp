package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

var latitud:Double=0.0
var longitud:Double=0.0

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
            modifier = Modifier.padding(top = 5.dp)
        )
        checkLocationPermissions()


    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun checkLocationPermissions() {

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
            var location =rememberSaveable {
                mutableStateOf(Location(null))
            }
            getLocation(onValueChange = {
                location.value =it })
            //Text("Permisos dados")
            println(location.value.latitude)
            println(location.value.longitude)
            if(location.value.latitude!=0.0 && location.value.longitude!=0.0){
                mapa(lat = location.value.latitude, long = location.value.longitude)
            }
        } else if (permissionStates.shouldShowRationale) {
            Text(text = "Se necesita la ubicación exacta")
        } else {

            Text("La aplicacion necesita los permisos")
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun getLocation(onValueChange:(Location)->Unit){
    var lat:Double=0.0
    var long:Double=0.0
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)
    fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY,null).addOnSuccessListener {
        onValueChange(it)
    }

}

@Composable
fun mapa(lat:Double,long:Double){
    println("lat: " + lat)
    println("Long:" + long)
    val location = LatLng(lat,long)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 5f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = location),
            title = "Tu ubicación",
            snippet = "Aqui te encuentras"
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

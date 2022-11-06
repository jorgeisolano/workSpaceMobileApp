package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        )/*
        val marker = LatLng(4.6814882,-74.0457162)
        val state = MarkerState(position = marker)
        val camaraPositionState = rememberCameraPositionState{
            position = CameraPosition.fromLatLngZoom(marker,15f)
        }
        GoogleMap(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 20.dp),
            cameraPositionState=camaraPositionState){
            Marker(state=state, title = "Marcador lugar")
        }
    */
        checkLocationPermissions()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun checkLocationPermissions() {

    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
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
            Text("Permisos dados")
        } else if (permissionStates.shouldShowRationale) {
            Text(text = "Se necesita la ubicación exacta")
        } else {

            Text("La aplicacion necesita los permisos")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MapPreview() {
    Scaffold(topBar = { MapTopBar(null) }) {
        MapBodyContent(navController = null)
    }
}

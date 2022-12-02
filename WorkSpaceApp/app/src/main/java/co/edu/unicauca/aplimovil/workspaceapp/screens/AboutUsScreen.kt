package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R

@Composable
fun AboutUs(navController: NavController){
    AboutUsBodyContent(navController)
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAboutUs() {
    AboutUsBodyContent(null)
}
@Composable
fun AboutUsBodyContent(navController: NavController?){
    Scaffold (
        topBar = { TopBar(navController = navController, title = stringResource(id = R.string.aboutus_label))}
    ){
        Column(modifier = Modifier
            .padding(start = 30.dp, end = 30.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Title(texto = stringResource(id = R.string.AppName))
            Spacer(modifier = Modifier.size(15.dp))
            Text(text = stringResource(id = R.string.descriptionApp), modifier = Modifier.paddingFromBaseline(top=35.dp), textAlign = TextAlign.Center,fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Spacer(modifier = Modifier.size(15.dp))
            Subtitles(texto = "Desarrollado por:")
            Text(text = "Laura Isabel Chaparro Navia", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(text = "lauraich@unicauca.edu.co",fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(text = "Jorge Ivan Solano Papamija",fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(text = "jorgeivan@unicauca.edu.co", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(text = "Juan Fernando Campo Mosquera", fontWeight = FontWeight.Normal, fontSize = 20.sp)
            Text(text = "juancamm@unicauca.edu.co", fontWeight = FontWeight.Normal, fontSize = 20.sp)
        }
    }
}

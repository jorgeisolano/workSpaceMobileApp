package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navDeepLink
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun InitialScreen(navController:NavController){
    /*Scaffold {
        BodyContent(navController)
    }*/
    Scaffold (
        bottomBar = { IniciarButtomBar(navController = navController) }
    ){
        bodyContentInitialScreen(navController)

    }
    
}

@Preview(showBackground = true)
@Composable
fun previewInitialScrem(){
    bodyContentInitialScreen(null)
}
@Composable
fun bodyContentInitialScreen(navController:NavController?){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 30.dp, end = 30.dp, top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Box( modifier = Modifier
            .height(300.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.workspace)
                .build(),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Profile")
        }
        Spacer(modifier = Modifier.size(50.dp))
        Title(texto = stringResource(id = R.string.ideal_place_label))
        Text(
            stringResource(id=R.string.discover_label),
            fontSize = 30.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun IniciarButtomBar(navController: NavController?){

    BottomAppBar(containerColor = colorResource(id = com.firebase.ui.auth.R.color.fui_transparent)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val context = LocalContext.current
            Button(onClick = { navController?.navigate(route=AppScreens.HomeScreen.route) }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp)
                .clip(RoundedCornerShape(30.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.login),
                    contentColor = Color.White)
            ) {
                Text(text = stringResource(id = R.string.start_button),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White)
            }

        }
    }
}
@Composable
fun Title(texto : String){
    Text(text = texto, modifier = Modifier.paddingFromBaseline(top=35.dp),fontWeight = FontWeight.Bold, fontSize = 30.sp)
}
//@Composable
//fun BodyContent(navController:NavController){
//    Column(){
//        Text(text = "Screen Inicial")
//        Button(onClick = { navController.navigate(route=AppScreens.HomeScreen.route)}) {
//            Text(text = "Ir a Home")
//        }
//    }
//}


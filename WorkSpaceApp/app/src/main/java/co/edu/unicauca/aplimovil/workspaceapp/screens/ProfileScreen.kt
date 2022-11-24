package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.models.Sesion
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.FirebaseAuth
import com.orm.SugarRecord

@Composable
fun ProfileScreen(navController: NavController){
    ProfileBodyContent(navController)
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProfile(){
    ProfileBodyContent(null)
}
@Composable
fun ProfileBodyContent(navController: NavController?){
    var currentUser=FirebaseAuth.getInstance().currentUser?.email
    var sesion: MutableList<Sesion>? = null
    if(currentUser!=null){
        sesion = SugarRecord.find(Sesion::class.java,"email = ?", currentUser)
    }


    Scaffold (
        topBar = { TopBar(navController = navController, title = stringResource(id = R.string.profile_label))}
    ){
        Column(modifier = Modifier
            .padding(30.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            if(currentUser!=null){
                profileInfo(sesion = sesion?.get(0) ?: null)
                ButtonLogOut(navController = navController)
            }else{
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                    Subtitles(texto = stringResource(id = R.string.no_authenticated_label))
                    Spacer(modifier = Modifier.size(30.dp) )
                    ButtonLoggin(navController)
                }

            }
            
            
        }
    }
}
@Composable
fun profileInfo(sesion:Sesion?){
    Box( modifier = Modifier
        .height(200.dp)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        if(sesion?.profileUrl==""){
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.profile)
                .transformations(CircleCropTransformation())
                .crossfade(2000)
                .build(),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Profile")
        }else{
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(sesion?.profileUrl)
                .transformations(CircleCropTransformation())
                .crossfade(2000)
                .build(),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Profile")

        }
    }
    Spacer(modifier = Modifier.size(10.dp) )
    if(sesion?.name!=""){
        Subtitles(texto = sesion?.name!!)
    }
    Spacer(modifier = Modifier.size(10.dp) )
    Subtitles(texto = "juancamm@unicauca.edu.co")
    Spacer(modifier = Modifier.size(30.dp) )
}
fun LogOut(navController: NavController?){
    var sesion = SugarRecord.find(Sesion::class.java,"email = ?", FirebaseAuth.getInstance().currentUser?.email)

    if(sesion.isNotEmpty()) {
        sesion[0].delete()
    }
    FirebaseAuth.getInstance().signOut()
    navController?.navigate(route= AppScreens.ProfileScreen.route)
}
@Composable
fun ButtonLoggin(navController: NavController?){
    Button(onClick = { navController?.navigate(route= AppScreens.LoginScreen.route) }, modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(30.dp)),
        colors= ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.login), contentColor = Color.White)
    ) {
        Text(text = stringResource(id = R.string.loggin_buttom), fontWeight = FontWeight.Bold,fontSize = 20.sp, color= Color.White)
    }
}
@Composable
fun ButtonLogOut(navController: NavController?){
    Button(onClick = { LogOut(navController) }, modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(30.dp)),
        colors= ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.login), contentColor = Color.White)
    ) {
        Text(text = stringResource(id = R.string.logout_button), fontWeight = FontWeight.Bold,fontSize = 20.sp, color= Color.White)
    }
}

package co.edu.unicauca.aplimovil.workspaceapp.screens

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.edu.unicauca.aplimovil.workspaceapp.R
import co.edu.unicauca.aplimovil.workspaceapp.models.Sesion

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginScreen(navController: NavController){
    Scaffold (topBar = {
        TopBar(navController,"¡Bienvenido!")
    }){
        LoginBodyContent(navController)

    }

}

@Composable
fun LoginBodyContent(navController: NavController?){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){

            loginBody(navController)



    }
}
@Preview(showBackground = true)
@Composable
fun PreviewContent(){
    LoginBodyContent(null)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController?, title: String){
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold, fontSize = 22.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Localized description"
                )
            }
        }

    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginBody(navController: NavController?){
    Column(modifier = Modifier
        .padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(id=R.string.information_please_label),
            fontSize = 20.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.size(50.dp))
        var textCorreo by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 7)))
        }

        OutlinedTextField(
            value = textCorreo,
            onValueChange = { textCorreo = it },
            label = { Text(stringResource(R.string.email_or_phone_label)) },
            placeholder={Text(stringResource(R.string.email_or_phone_label))},
            leadingIcon={Icon(imageVector = Icons.Default.Email, contentDescription ="Email")},
            singleLine=true,
            keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email,imeAction=ImeAction.Next),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = ShapeDefaults.ExtraLarge
        )
        var textPassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue("", TextRange(0, 7)))
        }
        OutlinedTextField(
            value = textPassword,
            onValueChange = { textPassword = it },
            label = { Text(stringResource(R.string.password_label)) },
            placeholder={Text(stringResource(R.string.password_label))},
            visualTransformation=PasswordVisualTransformation(),
            leadingIcon={Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password")},
            singleLine=true,
            keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Password,imeAction= ImeAction.Done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = ShapeDefaults.ExtraLarge
        )
        Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End) {
            Text(modifier=Modifier
                .padding(end = 17.dp),
                text=stringResource(id=R.string.reset_password),
                fontSize = 20.sp,
                textAlign= TextAlign.End,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.size(50.dp))
        LoginButton(navController,textCorreo,textPassword)
    }

}
@Composable
fun LoginButton(navController: NavController?,email:TextFieldValue,password: TextFieldValue){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val context = LocalContext.current
        Button(onClick = { authEmailAndPassword(email.text,password.text,context as Activity,navController) }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(30.dp)),
            colors=ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.login), contentColor = Color.White)
        ) {
            Text(text = stringResource(id = R.string.loggin_buttom), fontWeight = FontWeight.Bold,fontSize = 20.sp, color=Color.White)
        }
        Spacer(modifier = Modifier.size(25.dp))
        Text(
            stringResource(id=R.string.o_sigin_txt),
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.size(25.dp))

        val responseLauncher= rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
            finalLogin(it,navController)
        }
        Button(onClick = { loginWithGoogle(context as Activity, responseLauncher) }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(30.dp)),
            colors=ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.loginGoogle), contentColor = Color.White)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.googleicon),
                contentDescription = "icono Google",
                modifier= Modifier
                    .size(31.dp)
                    .padding(end = 5.dp)
            )
            Text(text = stringResource(id = R.string.google_button), fontSize = 20.sp, color=Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

fun finalLogin(it: ActivityResult, navController:NavController?) {
    if(it.resultCode==RESULT_OK){
        val task=GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account=task.getResult(ApiException::class.java)
            if(account!=null){
                val credencial =GoogleAuthProvider.getCredential(account.idToken,null)
                FirebaseAuth.getInstance().signInWithCredential(credencial).addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.d("ACA","Se autentico ${account.email}")
                        Log.d("ACA","Se autentico ${account.displayName}")
                        Log.d("ACA","Se autentico ${account.photoUrl}")
                        var sesion= Sesion(email = account.email, name = account.displayName, profileUrl = account.photoUrl.toString())
                        sesion.save()
                        navController?.popBackStack()
                    }
                }
            }
        }catch (e:ApiException){
            Log.d("ACA","error ${e.message}")
        }


    }

}

fun authEmailAndPassword(email:String,password: String,activity: Activity,navController:NavController? ){
    if(email.isNotEmpty() && password.isNotEmpty()){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Log.d("ACA","iNICIO ${it.result.user?.email}")
                    var sesion= Sesion(email = it.result.user?.email)
                    sesion.save()
                    //showAlert(activity,"Bienvenido ${it.result.user?.email}","Estás de vuelta")
                    navController?.popBackStack()
                }else{
                    showAlert(activity,"Se ha producido un error autenticando al usuario","Error")
                }
            }
    }

}

fun showAlert(activity:Activity,msg:String,title:String){
    var bulder= AlertDialog.Builder(activity)
    bulder.setTitle(title)
    bulder.setMessage(msg)
    bulder.setPositiveButton("Aceptar",null)
    val dialog:AlertDialog=bulder.create()
    dialog.show()
}
fun loginWithGoogle(
    activity: Activity,
    responseLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
){
    val googleConf=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(activity.getString(R.string.default_web_client_id))
        .requestEmail()
        .requestProfile()
        .build()
    val googleClient=GoogleSignIn.getClient(activity,googleConf)
    googleClient.signOut()
    responseLauncher.launch(googleClient.signInIntent)

}



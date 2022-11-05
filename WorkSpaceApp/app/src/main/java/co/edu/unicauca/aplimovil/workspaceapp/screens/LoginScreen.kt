package co.edu.unicauca.aplimovil.workspaceapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.colorResource
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
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun LoginScreen(navController: NavController){
    val nav=navController
    Scaffold {
        LoginBodyContent(navController)

    }
}

@Composable
fun LoginBodyContent(navController: NavController?){
    TopBar(navController)
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column(){
            loginBody()
            Spacer(modifier = Modifier.size(50.dp))
            LoginButton(navController)
        }

    }
}
@Preview(showBackground = true)
@Composable
fun PreviewContent(){
    LoginBodyContent(null)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController?){
    CenterAlignedTopAppBar(
        title = {
            Text(
                "¡Bienvenido!",
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
fun loginBody(){

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
                .padding(start = 15.dp, end = 15.dp)
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
                .padding(start = 15.dp, end = 15.dp)
        )
        Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End) {
            Text(modifier=Modifier
                .padding(end = 15.dp),
                text=stringResource(id=R.string.reset_password),
                fontSize = 20.sp,
                textAlign= TextAlign.End,
                color = Color.Black
            )
        }

    }

}
@Composable
fun LoginButton(navController: NavController?){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { navController?.popBackStack() }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(30.dp)),
            colors=ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.login), contentColor = Color.White)
        ) {
            Text(text = stringResource(id = R.string.loggin_buttom), fontSize = 20.sp, color=Color.White)
        }
        Spacer(modifier = Modifier.size(25.dp))
        Text(
            stringResource(id=R.string.o_sigin_txt),
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.size(25.dp))
        Button(onClick = { navController?.popBackStack() }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(30.dp)),
            colors=ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.loginGoogle), contentColor = Color.White)
        ) {
            Text(text = stringResource(id = R.string.google_button), fontSize = 20.sp, color=Color.White)
        }
    }
}



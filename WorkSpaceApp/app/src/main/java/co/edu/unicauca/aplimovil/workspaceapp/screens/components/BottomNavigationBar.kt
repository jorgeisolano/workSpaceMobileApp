package co.edu.unicauca.aplimovil.workspaceapp.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Blanco
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.GrisOscuro
import co.edu.unicauca.aplimovil.workspaceapp.ui.theme.Verde

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items : List<AppScreens>
){
    val currentRoute = currentRoute(navController = navController)

    BottomNavigation(backgroundColor = Blanco) {
        items.forEach{ screen->
            BottomNavigationItem(
                icon = { screen.icon?.let { Icon(imageVector = it, contentDescription = screen.title) } },
                label = { screen.title?.let { Text(it) } },
                selected = currentRoute == screen.route ,
                selectedContentColor = Verde,
                unselectedContentColor = GrisOscuro,
                onClick = {
                    navController.navigate(screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState=true
                        }
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true
            )
        }
    }


}

@Composable
private fun currentRoute(navController: NavHostController) : String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

package co.edu.unicauca.aplimovil.workspaceapp.screens.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import co.edu.unicauca.aplimovil.workspaceapp.navigation.AppScreens

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items : List<AppScreens>
){
    val currentRoute = currentRoute(navController = navController)
    BottomNavigation() {
        items.forEach{ screen->
            BottomNavigationItem(
                icon = { screen.icon?.let { Icon(imageVector = it, contentDescription = screen.title) } },
                label = { screen.title?.let { Text(it) } },
                selected = currentRoute == screen.route ,
                onClick = {
                          navController.navigate(screen.route){
                              popUpTo(navController.graph.findStartDestination().id){
                                  saveState=true
                              }
                              launchSingleTop = true
                          }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController) : String?{
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
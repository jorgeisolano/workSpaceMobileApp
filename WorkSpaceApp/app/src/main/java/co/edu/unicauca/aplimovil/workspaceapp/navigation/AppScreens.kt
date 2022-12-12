package co.edu.unicauca.aplimovil.workspaceapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppScreens(
    val route: String,
    val title: String?,
    val icon: ImageVector?
    ){
    object InitialScreen: AppScreens("initial_screen","as",Icons.Filled.Home)
    object HomeScreen: AppScreens("home_screen","Inicio",Icons.Filled.Home)
    object DetailScreen : AppScreens("detail_screen","asd",Icons.Filled.Lock)
    object ReservationScreen : AppScreens("reservation_screen","asd",Icons.Filled.Lock)
    object MapScreen : AppScreens("map_screen","Mapa",Icons.Filled.Place)
    object LoginScreen : AppScreens("login_screen","asd",Icons.Filled.Lock)
    object BookingsScreen : AppScreens("bookings_screen","Reservas",Icons.Filled.Check)
    object FavoritesScreen : AppScreens("favorites_screen","Favoritos",Icons.Filled.Favorite)
    object ProfileScreen : AppScreens("profile_screen","Perfil",Icons.Filled.Person)
    object AboutUsScreen : AppScreens("aboutus_screen","Perfil",Icons.Filled.Info)
}

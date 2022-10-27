package co.edu.unicauca.aplimovil.workspaceapp.navigation

sealed class AppScreens(val route: String){
    object InitialScreen: AppScreens("initial_screen")
    object HomeScreen: AppScreens("home_screen")
    object DetailScreen : AppScreens("detail_screen")
    object ReservationScreen : AppScreens("reservation_screen")
    object MapScreen : AppScreens("map_screen")
    object LoginScreen : AppScreens("login_screen")

}

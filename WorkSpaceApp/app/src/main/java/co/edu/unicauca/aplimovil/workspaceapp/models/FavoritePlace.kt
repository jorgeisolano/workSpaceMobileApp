package co.edu.unicauca.aplimovil.workspaceapp.models

import com.google.firebase.auth.FirebaseAuth
import com.orm.SugarRecord

class FavoritePlace : SugarRecord() {

    lateinit var userEmail : String;
    lateinit var favoritePlace: Place;

    fun get_FavoritePlaces(): MutableList<FavoritePlace>{
        return find(FavoritePlace::class.java,"userEmail = ?",userEmail)
    }
}
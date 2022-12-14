package co.edu.unicauca.aplimovil.workspaceapp.models

import com.google.firebase.auth.FirebaseAuth
import com.orm.SugarRecord

class FavoritePlace: SugarRecord {

    var userEmail : String? = null;
    var favoritePlace: Place?  = null;

    constructor(){}

    constructor(userEmail: String, favoritePlace: Place) {
        this.userEmail = userEmail
        this.favoritePlace = favoritePlace
    }

    fun getFavoritePlaces(pUserEmail : String): MutableList<FavoritePlace>{
        return find(FavoritePlace::class.java,"user_Email = ?", pUserEmail)
    }

}
package co.edu.unicauca.aplimovil.workspaceapp.models

import androidx.compose.material.icons.Icons
import com.orm.SugarRecord

class Amenities : SugarRecord {

    var name : String? = null;
    var icon : Int? = null;
    lateinit var place: Place;

    constructor(){}

    constructor(name: String?, icon: Int?) {
        this.name = name
        this.icon = icon
    }
}
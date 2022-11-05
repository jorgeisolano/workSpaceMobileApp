package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord

class Amenities : SugarRecord {

    var name : String? = null;
    var icon : String? = null;
    lateinit var place: Place;

    constructor(){}

    constructor(name: String?, icon: String?) {
        this.name = name
        this.icon = icon
    }
}
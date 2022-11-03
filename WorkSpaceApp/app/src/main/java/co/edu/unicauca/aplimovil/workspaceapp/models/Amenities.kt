package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord

class Amenities : SugarRecord {

    var name : String? = null;
    var icon : String? = null;

    constructor(name: String?, icon: String?) {
        this.name = name
        this.icon = icon
    }
}
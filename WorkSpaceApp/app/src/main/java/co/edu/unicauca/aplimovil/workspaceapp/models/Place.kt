package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord


class Place(val id : Int, val name : String, val city : String,
val description : String, val address : String, val lat : String,
val long : String, val image : String) : SugarRecord<Place>() {


}
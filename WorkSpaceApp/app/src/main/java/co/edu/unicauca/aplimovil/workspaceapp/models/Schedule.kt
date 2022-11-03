package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord
import com.orm.dsl.Ignore

class Schedule : SugarRecord {
    var idPlace : Int? = null
    var day : String? = null;
    var start_hour : String? = null;
    var end_hour : String? = null;
    @Ignore
    var place : Place? = null

    constructor(){}

    constructor(idPlace:Int?, day: String?, start_hour: String?, end_hour: String?) {
        this.idPlace = idPlace
        this.day = day
        this.start_hour = start_hour
        this.end_hour = end_hour
    }
}
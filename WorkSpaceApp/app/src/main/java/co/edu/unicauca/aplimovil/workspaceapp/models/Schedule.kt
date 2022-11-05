package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord
import com.orm.dsl.Ignore

class Schedule : SugarRecord {
    var day : String? = null;
    var start_hour : String? = null;
    var end_hour : String? = null;
    lateinit var place: Place;

    constructor(){}

    constructor( day: String?, start_hour: String?, end_hour: String?) {
        this.day = day
        this.start_hour = start_hour
        this.end_hour = end_hour
    }
}
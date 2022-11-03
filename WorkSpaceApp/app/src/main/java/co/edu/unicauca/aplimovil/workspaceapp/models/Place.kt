package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord
import com.orm.dsl.Ignore


class Place : SugarRecord{

    var id : Number? = null;
    var name : String? = null;
    var city : String? = null;
    var description : String? = null;
    var address : String? = null;
    var lat : String? = null;
    var long : String? = null;
    var image : String? = null;
    @Ignore
    var schedules : MutableList<Schedule> = ArrayList()

    fun get_Schedules(): MutableList<Schedule> {
        return find(Schedule::class.java,"id_Place = ?",this.id.toString())
    }
    constructor(){}

    constructor(name : String,  city : String,
                description : String,  address : String, lat : String,
                long : String,  image : String)  {
        this.name=name
        this.city=city
        this.description=description
        this.address=address
        this.lat=lat
        this.long=long
        this.image=image
    }
}
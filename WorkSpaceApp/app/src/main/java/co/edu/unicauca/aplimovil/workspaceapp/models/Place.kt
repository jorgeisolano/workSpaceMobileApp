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
    var extra : String? = null;

    fun get_Schedules(): MutableList<Schedule> {
        return find(Schedule::class.java,"place= ?",this.id.toString())
    }
    fun get_Amenities(): MutableList<Amenities> {
        return find(Amenities::class.java,"place= ?",this.id.toString())
    }
    fun get_Bookings(): MutableList<Booking> {
        return find(Booking::class.java,"place= ?",this.id.toString())
    }
    constructor(){}

    constructor(name : String,  city : String,
                description : String,  address : String, lat : String,
                long : String,  image : String,extra : String)  {
        this.name=name
        this.city=city
        this.description=description
        this.address=address
        this.lat=lat
        this.long=long
        this.image=image
        this.extra=extra
    }
}
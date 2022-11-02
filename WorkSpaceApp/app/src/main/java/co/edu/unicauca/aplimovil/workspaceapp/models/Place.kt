package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord


class Place : SugarRecord{

    var id: Int? = null;
    var name : String? = null;
    var city : String? = null;
    var description : String? = null;
    var address : String? = null;
    var lat : String? = null;
    var long : String? = null;
    var image : String? = null;

    constructor(id : Int, name : String,  city : String,
                description : String,  address : String, lat : String,
                long : String,  image : String)  {
        this.id = id
        this.name=name
        this.city=city
        this.description=description
        this.address=address
        this.lat=lat
        this.long=long
        this.image=image
    }
}
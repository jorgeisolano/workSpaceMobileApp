package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord
import java.util.Date

class Booking : SugarRecord {

    var checkin : String? = null;
    var checkout : String? = null;
    var timeCheckin : String? = null;
    var timeCheckout : String? = null;
    var seats : Int? = null;
    var guest : Int? = null;

    lateinit var userEmail:String;
    lateinit var place: Place;

    constructor(){}
    constructor(
        checkin: String?,
        checkout: String?,
        timeCheckin: String?,
        timeCheckout: String?,
        seats: Int?,
        guest: Int?
    )  {
        this.checkin = checkin
        this.checkout = checkout
        this.timeCheckin = timeCheckin
        this.timeCheckout = timeCheckout
        this.seats = seats
        this.guest = guest
    }


}
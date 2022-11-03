package co.edu.unicauca.aplimovil.workspaceapp.models

import com.orm.SugarRecord
import java.util.Date

class Booking : SugarRecord {

    var checkin : Date? = null;
    var checkout : Date? = null;
    var seats : Int? = null;
    var guest : Int? = null;

    constructor(checkin: Date?, checkout: Date?, seats: Int?, guest: Int?) : super() {
        this.checkin = checkin
        this.checkout = checkout
        this.seats = seats
        this.guest = guest
    }
}
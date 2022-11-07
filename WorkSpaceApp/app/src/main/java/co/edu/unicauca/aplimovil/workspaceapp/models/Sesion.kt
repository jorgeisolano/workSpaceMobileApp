package co.edu.unicauca.aplimovil.workspaceapp.models
import com.orm.SugarRecord
class Sesion: SugarRecord {
    var correo:String?=null
    var password:String?=null

    constructor(correo: String?, password: String?) : super() {
        this.correo = correo
        this.password = password
    }
}
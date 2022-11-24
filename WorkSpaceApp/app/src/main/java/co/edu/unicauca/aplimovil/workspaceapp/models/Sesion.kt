package co.edu.unicauca.aplimovil.workspaceapp.models
import co.edu.unicauca.aplimovil.workspaceapp.R
import com.orm.SugarRecord
class Sesion: SugarRecord {
    var email:String?=null
    var profileUrl:String?=""
    var name:String?=""
    constructor(){}
    constructor(
        email: String?,
        profileUrl: String?=null,
        name:String?=null
    ) {
        this.email = email
        this.profileUrl = profileUrl
        this.name=name
    }
}
package ma.ensaf.tp1_calcul.Modul

class user {

    var userName: String = ""
        get() = field
        set(value) {
            field = value
        }
    fun userName(displayName: String?) {
        this.userName = userName
    }



    constructor(userName: String) {
        this.userName = userName
    }

    constructor()
}

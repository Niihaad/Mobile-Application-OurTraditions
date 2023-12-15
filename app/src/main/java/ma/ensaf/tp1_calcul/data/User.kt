package ma.ensaf.tp1_calcul.data

data class User(
    var name:  String,
    var first_name: String,
    var email: String,
    var password:  String,
    var oldPassword: String,
    var role: String
){
    var id : Int=-1
    constructor(id: Int, name:String,first_name:String,email:String, password: String, oldPassword: String,role: String)
            :this(name,first_name,email,password,oldPassword,role){
       this.id=id
    }
}


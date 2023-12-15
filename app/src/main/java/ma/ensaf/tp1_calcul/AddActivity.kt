package ma.ensaf.tp1_calcul
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import ma.ensaf.tp1_calcul.data.User

class AddActivity : AppCompatActivity() {
    lateinit var db : CalculDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        var nom : TextInputEditText = findViewById(R.id.nom_id)
        var viewUser : TextView = findViewById(R.id.viewUser)
        var prenom : TextInputEditText = findViewById(R.id.prenom_id)
        var Add_user2 : Button = findViewById(R.id.Add_user2)
        var password : TextInputEditText = findViewById(R.id.password_id)
        var email : TextInputEditText = findViewById(R.id.email_id)
        db = CalculDatabase(this)
        val role = intent.getStringExtra("role")
        if(role=="admin"){
            viewUser.visibility=View.VISIBLE
        }
        viewUser.setOnClickListener({
            val intent =  Intent(this,ViewUsers::class.java)
            startActivity(intent)
        })
        Add_user2.setOnClickListener {
            if (nom.text?.isBlank() == true || prenom.text?.isBlank() == true || password.text?.isBlank() == true || email.text?.isBlank() == true) {
                showSnackbarRed("Remplir tous les champs!")
            } else {
                val user = User(nom.text.toString(),prenom.text.toString(),email.text.toString(),password.text.toString(),"","normalUser")
                var isInsereted = db.addAuth(user)
                if(isInsereted)
               {
                showSnackbarWithUndo("Création d'un nouveau utilisateur avec succès")
                {
                    val userAdded =
                        db.removeUser(email.text.toString(),password.text.toString()) // Supprimer l'utilisateur en fonction de l'e-mail
                    if (userAdded) {
                        showSnackbar("Création d'utilisateur annulée")
                    } else {
                        showSnackbar("Échec de l'annulation")
                    }
                }
                   Handler(Looper.getMainLooper()).postDelayed({
                       val intent = Intent(this, MainActivity_first::class.java)
                       startActivity(intent)
                   }, 2000)
                }
                else{
                   showSnackbarRed("Échec de création")
               }
            }
        }
    }
    private fun showSnackbarRed(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, R.color.colorRed)) // Couleur du texte en rouge
            .setDuration(Snackbar.LENGTH_LONG)
            .show()

    }
    private fun showSnackbar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showSnackbarWithUndo(message: String, action: () -> Unit) {
        val rootView = findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        snackbar.setAction("UNDO") { action() }
        snackbar.show()
    }
}

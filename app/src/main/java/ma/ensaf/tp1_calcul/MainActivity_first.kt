package ma.ensaf.tp1_calcul

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
class MainActivity_first : AppCompatActivity() {
    lateinit var db : CalculDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_first)
        var add_button: FloatingActionButton
        var add_person : FloatingActionButton
        var remove_person : FloatingActionButton
        var email : TextInputEditText =findViewById(R.id.email)
        var pwd : TextInputEditText =findViewById(R.id.pwd)
        var valider : Button = findViewById(R.id.Valider);
        var layout1: LinearLayout
        var layout2: LinearLayout
        var rolee : String =""
        db=CalculDatabase(this)
        remove_person =findViewById(R.id.remove_User)
        add_button = findViewById(R.id.add_page)
        layout1 = findViewById(R.id.lin1)
        layout2 = findViewById(R.id.lin2)
        add_person=findViewById(R.id.add_User)

        add_button.setOnClickListener(View.OnClickListener {
            if (email.text?.isBlank() != true && pwd.text?.isBlank() != true) {
                var role = db.findRole(email.text.toString(), pwd.text.toString())

                rolee=role
             if(role=="admin")
            {
            if(layout1.visibility!=View.VISIBLE) {
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.VISIBLE
            }
            else{
                layout1.visibility = View.GONE
                layout2.visibility = View.GONE
            }}
            else if(role=="normalUser"){
                if(layout1.visibility!=View.VISIBLE) {
                    layout1.visibility = View.VISIBLE
                }
                else{
                    layout1.visibility = View.GONE
                }
            }else{
                 showSnackbar("Cet utilisateur n'existe pas!")
             }
            }
            else{
                showSnackbar("Remplir les deux champs")
            }
       })
        add_person.setOnClickListener({
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("role", rolee)
            startActivity(intent)
        })
        remove_person.setOnClickListener({
            val intent =  Intent(this,RemoveUser::class.java)
            startActivity(intent)
        })

        valider.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
      }

    private fun showSnackbar(message: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, R.color.colorRed)) // Couleur du texte en rouge
            .setDuration(Snackbar.LENGTH_LONG)
            .show()

    }
}
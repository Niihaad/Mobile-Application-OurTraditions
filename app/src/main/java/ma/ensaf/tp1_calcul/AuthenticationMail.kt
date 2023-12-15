package ma.ensaf.tp1_calcul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.StateSet.TAG
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class AuthenticationMail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication_mail)

        var email : TextInputEditText =findViewById(R.id.email)
        var pwd : TextInputEditText =findViewById(R.id.pwd)
        var valider : Button = findViewById(R.id.Valider);
        var auth: FirebaseAuth=FirebaseAuth.getInstance()

        valider.setOnClickListener {
            if(email.equals("") || pwd.equals(""))
            {
                Toast.makeText(this,"Remplir tous les champs",Toast.LENGTH_LONG).show()
            }
            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) {task ->

                    if (task.isSuccessful) {

                        Log.d(TAG, "createUserWithEmailAndPassword:success")
                        Toast.makeText(
                            baseContext,
                            "Authentication succes: ",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Authentification échouée
                        Log.w(TAG, "createUserWithEmailAndPassword:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed: " + task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
}
}
    }
}
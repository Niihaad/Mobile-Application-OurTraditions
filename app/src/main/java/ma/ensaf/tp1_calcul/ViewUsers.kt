package ma.ensaf.tp1_calcul

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class ViewUsers : AppCompatActivity() {
    lateinit var db : CalculDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_users)
        db=CalculDatabase(this)
        val cursor = db.readAllData()
        val dataTextView : TextView = findViewById(R.id.dataTextView)
        try {
        if (cursor != null) {
            val dataStringBuilder = StringBuilder()
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndexOrThrow("id")
                val nameIndex = cursor.getColumnIndexOrThrow("name")
                val firstNameIndex = cursor.getColumnIndexOrThrow("first_name")
                val emailIndex = cursor.getColumnIndexOrThrow("email")
                do {
                    val id = cursor.getInt(idIndex)
                    val name = cursor.getString(nameIndex)
                    val firstName = cursor.getString(firstNameIndex)
                    val email = cursor.getString(emailIndex)

                    dataStringBuilder.append("ID: $id\n")
                    dataStringBuilder.append("Name: $name\n")
                    dataStringBuilder.append("First Name: $firstName\n")
                    dataStringBuilder.append("Email: $email\n\n")
                } while (cursor.moveToNext())
            }
            cursor.close()
            dataTextView.text = dataStringBuilder.toString()
        }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ViewUsers", "Erreur lors de la récupération des données de la base de données: ${e.message}")
        }
    }
}
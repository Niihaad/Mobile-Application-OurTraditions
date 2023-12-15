package ma.ensaf.tp1_calcul

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Map_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_page)
        val fragment : Fragment = Map_Fragment()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit()
    }
}
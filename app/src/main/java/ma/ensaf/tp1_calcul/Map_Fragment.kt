package ma.ensaf.tp1_calcul

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.google.android.libraries.maps.model.MarkerOptions


class Map_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_map_, container, false)
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.MY_MAP) as SupportMapFragment

        val ensaFesLocation = LatLng(34.03329849, -5.00000000)
        val ensaTetouanLocation = LatLng(35.56232, -5.36443)
        val ensaHouceimaLocation =  LatLng(35.250000,-3.933333)
        val ensaTangerLocation =  LatLng(35.759465,-5.833954)
        supportMapFragment.getMapAsync { googleMap ->
            // Centrer la carte sur les ENSA
            val bounds = LatLngBounds.builder()
                .include(ensaFesLocation)
                .include(ensaTetouanLocation)
                .include(ensaTangerLocation)
                .include(ensaHouceimaLocation)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 15))
            val markerOptionsFes = MarkerOptions().position(ensaFesLocation).title("ENSA Fès")
            val markerOptionsTetouan = MarkerOptions().position(ensaTetouanLocation).title("ENSA Tetouan")
            val markerOptionsTanger=MarkerOptions().position(ensaHouceimaLocation).title("ENSA Houceima")
            val markerOptionsHouceima=MarkerOptions().position(ensaTangerLocation).title("ENSA Tanger")
            googleMap.addMarker(markerOptionsFes)
            googleMap.addMarker(markerOptionsTetouan)
            googleMap.addMarker(markerOptionsHouceima)
            googleMap.addMarker(markerOptionsTanger)
        }

            return view
        }
}
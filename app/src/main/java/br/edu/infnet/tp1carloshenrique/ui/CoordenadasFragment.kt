package br.edu.infnet.tp1carloshenrique.ui

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import br.edu.infnet.tp1carloshenrique.R
import kotlinx.android.synthetic.main.fragment_coordenadas.*
import java.io.File
import java.io.FileOutputStream
import java.util.*


class CoordenadasFragment : Fragment(), LocationListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_coordenadas, container, false)

        val btnAcompanharEntrega = view.findViewById<Button>(R.id.btnRastrear)
        val btnRegistrar = view.findViewById<Button>(R.id.btnRegistrar)
        val btnLerRegistro = view.findViewById<Button>(R.id.btnLerRegistro)


        setNavegation(btnLerRegistro);

        var coordenadas = setCoordenadas(btnAcompanharEntrega);
        storeCoordenadas(btnRegistrar)


        return view
    }

    private fun storeCoordenadas(btnRegistrar: Button?) {
        btnRegistrar?.setOnClickListener{
            if (Environment.getExternalStorageState()== Environment.MEDIA_MOUNTED) {
                val arquivo = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), Calendar.getInstance().time.toString()+".txt")
                val fileOutput = FileOutputStream(arquivo)

                val coordenadasLocalizada = getCoordenadas()

                val coordenadas = "Latitude ${coordenadasLocalizada.get("Latitude")}, Longitude: ${coordenadasLocalizada.get("Longitude")}";
                fileOutput.write(coordenadas.toByteArray());
                fileOutput.close();
            }
        }
    }

    private fun setCoordenadas(btnAcompanharEntrega: Button?): Map<String, String> {
        var coordenadas: Map<String, String> = mapOf()

        btnAcompanharEntrega?.setOnClickListener {
            coordenadas = getCoordenadas();
        }

        return coordenadas;
    }

    private fun getCoordenadas(): Map<String, String> {
        try {
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            var location: Location? = null
            var latitude = 0.0
            var longitude = 0.0


            if (gpsEnable) {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        2000L,
                        0f,
                        this
                )
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                if(location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }

            } else {
                println("ERRO AO OBTER DADOS DO GPS")
            }

            var labelValorLatitude = lblLatitudeValue
            var labelValorLongitude = lblLongitudeValue

            labelValorLatitude.setText(latitude.toString())
            labelValorLongitude.setText(longitude.toString())

            return mapOf("Latitude" to labelValorLatitude.toString(), "Longitude" to labelValorLongitude.toString())

        } catch (e: SecurityException) {
            println("ERRO: IMPOSSÍVEL ACESSAR LOCALIZACAO PELA REDE")
            //throw RuntimeException(e.message)
            return mapOf()
        }
    }

    private fun setNavegation(btnLerRegistro: Button?) {
        btnLerRegistro?.setOnClickListener{
            findNavController().navigate(R.id.registrosFragment)
        }
    }

    override fun onLocationChanged(location: Location) {

    }
}
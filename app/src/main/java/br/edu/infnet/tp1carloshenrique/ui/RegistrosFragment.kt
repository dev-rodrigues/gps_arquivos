package br.edu.infnet.tp1carloshenrique.ui

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import br.edu.infnet.tp1carloshenrique.R
import java.io.File

class RegistrosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registros, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val arquivo = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "teste.txt")
            val texto = listOf(arquivo.name)

            val arquivos = view.findViewById<ListView>(R.id.list_arquivos)
            val arrayAdapter: ArrayAdapter<*> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, texto)
            arquivos.adapter = arrayAdapter
        }


    }
}
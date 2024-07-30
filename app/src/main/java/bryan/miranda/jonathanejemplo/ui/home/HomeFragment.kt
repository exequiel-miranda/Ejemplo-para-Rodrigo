package bryan.miranda.jonathanejemplo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import bryan.miranda.jonathanejemplo.databinding.FragmentHomeBinding
import bryan.miranda.jonathanejemplo.iniciarsesion
import bryan.miranda.jonathanejemplo.modelo.ClaseConexion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        fun traerID(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet =
                statement?.executeQuery("SELECT UUID_rol FROM tbRolesUsuarios WHERE nombre_rol = 'Administrador'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_rol")
            }
            return uuidRol
        }




        CoroutineScope(Dispatchers.IO).launch {
            val txtcorreoiniciarval = iniciarsesion.variablesLogin.valorRolUsuario
            val RolUsuarioMainActivity = traerID()
            withContext(Dispatchers.Main) {
                if (txtcorreoiniciarval == RolUsuarioMainActivity) {
                    binding.btnUno.visibility = View.VISIBLE
                    binding.btnDos.visibility = View.VISIBLE
                } else {
                    binding.btnUno.visibility = View.GONE
                    binding.btnDos.visibility = View.GONE
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
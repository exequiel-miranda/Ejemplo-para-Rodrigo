package bryan.miranda.jonathanejemplo

//import bryan.miranda.jonathanejemplo.iniciarsesion.variablesLogin.valorRolUsuario
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import bryan.miranda.jonathanejemplo.databinding.ActivityMainBinding
import bryan.miranda.jonathanejemplo.modelo.ClaseConexion
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        fun traerID(): String? {
            var uuidRol: String? = null
            val objConexion = ClaseConexion().cadenaConexion()
            val statement = objConexion?.createStatement()
            val resulSet =
                statement?.executeQuery("SELECT UUID_rol FROM tbRolesUsuarios WHERE nombre_rol = 'Dueno Mascota'")!!

            if (resulSet.next()) {
                uuidRol = resulSet.getString("UUID_rol")
                println("este es $uuidRol")
            }
            return uuidRol
        }


        CoroutineScope(Dispatchers.IO).launch {
            val txtcorreoiniciarval = iniciarsesion.variablesLogin.valorRolUsuario
            val RolUsuarioMainActivity = traerID()
            if (txtcorreoiniciarval == RolUsuarioMainActivity) {
                navView.menu.findItem(R.id.nav_slideshow).isVisible = true
            } else {
                navView.menu.findItem(R.id.nav_slideshow).isVisible = false
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
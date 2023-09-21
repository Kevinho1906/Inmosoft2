package com.example.inmosoft2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.inmosoft2.databinding.ActivityVistaPrincipalBinding
import com.squareup.picasso.Picasso

class vista_principal : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVistaPrincipalBinding
    lateinit var txtUser: TextView
    lateinit var txtCorreo: TextView
    lateinit var imgFotoPerfil: ImageView
    //private var idUser:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVistaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarVistaPrincipal.toolbar)

        binding.appBarVistaPrincipal.toolbar
            .setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_vista_principal)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio, R.id.nav_cerrar_sesion, R.id.nav_ayuda, R.id.nav_vista_detalle_proyecto, R.id.nav_modificar_usuario
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        txtUser = binding.navView.getHeaderView(0).findViewById(R.id.txtUser)
        txtCorreo =binding.navView.getHeaderView(0).findViewById(R.id.txtCorreo)
        imgFotoPerfil = binding.navView.getHeaderView(0).findViewById(R.id.imgFotoPerfil)

        txtUser.setText(intent.getStringExtra("nombre"))
        txtCorreo.setText(intent.getStringExtra("correo"))

        var url = "http://192.168.137.46:8000/media/"+intent.getStringExtra("foto")
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.usuario_icono)
            .error(R.drawable.usuario_icono)
            .into(imgFotoPerfil)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.vista_principal, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_vista_principal)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    public fun obtenerIdUsuario(): Int {
        val sharedPreferences = getSharedPreferences("MiAppPref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("idUsuario", -1) // -1 es un valor predeterminado en caso de que no se encuentre el ID
    }
}
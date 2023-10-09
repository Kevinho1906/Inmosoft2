package com.example.inmosoft2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inmosoft2.databinding.ActivityVistaPrincipalBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


class vista_principal : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVistaPrincipalBinding
    lateinit var txtUser: TextView
    lateinit var txtCorreo: TextView
    lateinit var imgFotoPerfil: ImageView

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
                R.id.nav_inicio, R.id.nav_cerrar_sesion,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        txtUser = binding.navView.getHeaderView(0).findViewById(R.id.txtUser)
        txtCorreo =binding.navView.getHeaderView(0).findViewById(R.id.txtCorreo)
        imgFotoPerfil = binding.navView.getHeaderView(0).findViewById(R.id.imgFotoPerfil)

        txtUser.setText(intent.getStringExtra("nombre"))
        txtCorreo.setText(intent.getStringExtra("correo"))
        var idUser = intent.getStringExtra("idUser")


        var url = "https://inmosoft.pythonanywhere.com/media/"+intent.getStringExtra("foto")
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
    override fun onBackPressed() {
        // No hacer nada o mostrar un mensaje al usuario
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_vista_principal)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
package com.example.opportunity_pdm


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment

import com.example.opportunity_pdm.databinding.ActivityMainBinding
import com.example.recyclerviewkotlin.AdapterClass
import com.example.recyclerviewkotlin.DataClass
import com.example.recyclerviewkotlin.DetailActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.Locale


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {







    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arrayAdapter: ArrayAdapter<*>



        setTheme(R.style.Theme_Opportunity_pdm)



        bottomNavigationView = findViewById(R.id.botton_navigation)


        val items = listOf(
            "Grand Tunis", "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa",
            "Jendouba", "Kairouan", "Kasserine", "Kébili", "Le Kef", "Mahdia", "La Manouba",
            "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
            "Tataouine", "Tozeur", "Zaghouan"
        )

        val jobs = arrayOf(
            "Software Developer",
            "Développeur d'applications mobiles",
            "Développeur d'interface utilisateur mobile",
            "Ingénieur en test mobile",
            "Développeur d'applications hybrides",
            "Développeur d'applications iOS",
            "Développeur d'applications Android",
            "Concepteur d'expérience utilisateur mobile",
            "Architecte d'applications mobiles",
            "Responsable du développement mobile",
            "Spécialiste en optimisation des performances mobiles"
        )





        arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jobs)







       val adapter = ArrayAdapter(this, R.layout.lieu, items) // Utilisation de ArrayAdapter<String>

        binding.autoCompleteTextView.setAdapter(adapter)

        val items1 = listOf("temps plains", "temps partiel", "contrat", "travail temporaire")
        val adapter1 = ArrayAdapter(this, R.layout.type_de_contrat, items1)


        binding.autoCompleteTextView2.setAdapter(adapter1)

        bottomNavigationView.setOnItemReselectedListener {menuItem ->

            when(menuItem.itemId){
                R.id.botton_home ->{
                    replaceFragment(HomeFragment())
                    true
                }


                else -> false
             }


        }






        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        replaceFragment(HomeFragment())
    }


    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()
            R.id.nav_share -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShareFragment()).commit()
            R.id.nav_about -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AboutFragment()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }





    class MainActivity : AppCompatActivity() {

        private lateinit var recyclerView: RecyclerView
        private lateinit var dataList: ArrayList<DataClass>
        lateinit var imageList: Array<Int>
        lateinit var titleList:Array<String>
        lateinit var descList: Array<String>
        lateinit var detailImageList: Array<Int>
        private lateinit var myAdapter: AdapterClass
        private lateinit var searchView: SearchView
        private lateinit var searchList: ArrayList<DataClass>

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            imageList = arrayOf(
                R.drawable.ic_list,
                R.drawable.ic_checkbox,
                R.drawable.ic_image,
                R.drawable.ic_toggle,
                R.drawable.ic_date,
                R.drawable.ic_rating,
                R.drawable.ic_time,
                R.drawable.ic_text,
                R.drawable.ic_edit,
                R.drawable.ic_camera)

            titleList = arrayOf(
                "ListView",
                "CheckBox",
                "ImageView",
                "Toggle Switch",
                "Date Picker",
                "Rating Bar",
                "Time Picker",
                "TextView",
                "EditText",
                "Camera")
            recyclerView = findViewById(R.id.recyclerView)



            detailImageList = arrayOf(
                R.drawable.list_detail,
                R.drawable.check_detail,
                R.drawable.image_detail,
                R.drawable.toggle_detail,
                R.drawable.date_detail,
                R.drawable.rating_detail,
                R.drawable.time_detail,
                R.drawable.text_detail,
                R.drawable.edit_detail,
                R.drawable.camera_detail)


            searchView = findViewById(R.id.search)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)

            dataList = arrayListOf<DataClass>()
            searchList = arrayListOf<DataClass>()
            getData()

            searchView.clearFocus()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchList.clear()
                    val searchText = newText!!.toLowerCase(Locale.getDefault())
                    if (searchText.isNotEmpty()){
                        dataList.forEach{
                            if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)) {
                                searchList.add(it)
                            }
                        }
                        recyclerView.adapter!!.notifyDataSetChanged()
                    } else {
                        searchList.clear()
                        searchList.addAll(dataList)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                    return false
                }

            })

            myAdapter = AdapterClass(searchList)
            recyclerView.adapter = myAdapter

            myAdapter.onItemClick = {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("android", it)
                startActivity(intent)
            }

        }

        private fun getData(){
            for (i in imageList.indices){
                val dataClass = DataClass(imageList[i], titleList[i], descList[i], detailImageList[i])
                dataList.add(dataClass)
            }
            searchList.addAll(dataList)
            recyclerView.adapter = AdapterClass(searchList)
        }
    }






}
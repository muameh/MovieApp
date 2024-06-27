package com.mehmetBaloglu.mymovieapp_v1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.mehmetBaloglu.mymovieapp_v1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = destination.id != R.id.loginFragment
        }

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeTab -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.userTab -> {
                    navController.navigate(R.id.userFragment)
                    true
                }

                R.id.searchTab -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }

                R.id.exploreTab -> {
                    navController.navigate(R.id.exploreFragment)
                    true
                }

                else -> false
            }
        }
    }
}


/*
PROJE NASIL OLACAK ?

1- FİREBASE AUTH İLE BENİ LOGİN LOGUP SAYFASI KARŞILAYACAK
2- BOTTOM MENU OLACAK BUNLAR HOME/FAVOURİTES/SEARCH ÖGELERİ OLACAK
3- HOME'DA OLACAKLAR ->
4- FAV'A KAYDEDİLENLER ROOM A DEĞİL FİREBASE'E KAYDEDİLECEK
5- CARD A TIKLANINCA FİLMİN DETAYLARINA GÖTÜRECEK


yapılacaklar
-- silmeye tıklanınca anında ekrandan kaybolmuyor

*/
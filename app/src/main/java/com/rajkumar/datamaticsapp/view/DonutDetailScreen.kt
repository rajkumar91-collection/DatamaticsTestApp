package com.rajkumar.datamaticsapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rajkumar.datamaticsapp.R
import com.rajkumar.datamaticsapp.databinding.ActivityDonutDetailBinding
import com.rajkumar.datamaticsapp.model.Topping

class DonutDetailScreen : AppCompatActivity() {

    private lateinit var binding : ActivityDonutDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding =   DataBindingUtil.setContentView(this,R.layout.activity_donut_detail)
       val toppings = intent.extras?.get("toppings")
        val donutName =intent.extras?.get("donutName")
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.txtName.text = buildString {
        append(donutName)
        append(getString(R.string.below_string))
    }
        var text = ""
        if(toppings is Array<*>) {
            var sep = ""
            for (topping in toppings) {
                if(topping is Topping) {
                   text = text + sep +  "> "+topping.type
                }
                sep  = "\n\n"
            }
        }
        binding.txtToppings.text = text





    }

    override fun onSupportNavigateUp(): Boolean {
       onBackPressed()
        return true
    }
}
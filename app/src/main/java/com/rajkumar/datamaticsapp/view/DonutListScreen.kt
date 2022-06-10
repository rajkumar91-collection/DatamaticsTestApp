package com.rajkumar.datamaticsapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rajkumar.datamaticsapp.DonutsApplication
import com.rajkumar.datamaticsapp.R
import com.rajkumar.datamaticsapp.databinding.ActivityHomeScreenBinding
import com.rajkumar.datamaticsapp.model.Donut
import com.rajkumar.datamaticsapp.model.Topping
import com.rajkumar.datamaticsapp.model.api.Status
import com.rajkumar.datamaticsapp.view.adapter.DonutListAdapter
import com.rajkumar.datamaticsapp.view.adapter.IRecyclerClickListener
import com.rajkumar.datamaticsapp.viewmodel.DonutListViewModel
import com.rajkumar.datamaticsapp.viewmodel.DonutViewModelFactory


class DonutListScreen : AppCompatActivity(), IRecyclerClickListener<Array<Topping>> {
    private lateinit var binding: ActivityHomeScreenBinding
    private val mMovieListViewModel by lazy {
        ViewModelProvider(this, getViewModelFactory()).get(DonutListViewModel::class.java)
    }

    private fun getViewModelFactory(): ViewModelProvider.Factory {
        return DonutViewModelFactory((application as DonutsApplication).repository)
    }


private var mAdapter: DonutListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        initRecyclerView();
        subscribeObservers();
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getDonutsApi()

    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun subscribeObservers() {
        mMovieListViewModel.getDonuts()
            .observe(this) {
                if (it != null) {
                    if (it.data != null) {
                        when (it.status) {
                            Status.PROGRESS -> {
                            }
                            Status.ERROR -> {

                                mAdapter?.setDonuts(it.data as ArrayList<Donut>?)
                                Toast.makeText(
                                    this@DonutListScreen,
                                    it.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                //adaptor no data
                            }
                            Status.SUCCESS -> {
                                mAdapter?.setDonuts(it.data as ArrayList<Donut>?)
                            }
                            else -> {}
                        }
                    }

                }
            }


    }


    private fun getDonutsApi() {
        binding.rvDonuts.smoothScrollToPosition(0)
        mMovieListViewModel.getDonutList()


    }

    private fun initRecyclerView() {
        mAdapter = DonutListAdapter(this)
        binding.rvDonuts.layoutManager = LinearLayoutManager(this)
        binding.rvDonuts.adapter = mAdapter
    }




    override fun onClick(donutName : String?,toppings: Array<Topping>) {
        val intent = Intent(this, DonutDetailScreen::class.java)
        intent.putExtra("toppings", toppings)
        intent.putExtra("donutName",donutName)
        startActivity(intent)
    }


}
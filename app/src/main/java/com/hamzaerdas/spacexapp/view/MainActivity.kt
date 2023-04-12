package com.hamzaerdas.spacexapp.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hamzaerdas.spacexapp.R
import com.hamzaerdas.spacexapp.adapter.SpaceXAdapter
import com.hamzaerdas.spacexapp.databinding.ActivityMainBinding
import com.hamzaerdas.spacexapp.model.Launch
import com.hamzaerdas.spacexapp.util.Constants.MAIN_BG
import com.hamzaerdas.spacexapp.util.changeBackground
import com.hamzaerdas.spacexapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    @Inject
    lateinit var adapter: SpaceXAdapter
    private lateinit var dataList: List<Launch>
    private lateinit var searchView: SearchView

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainBgImage.changeBackground(MAIN_BG, binding, 1)

        getData()
        recyclerViewInitialize()
        observeData()
        searchView()
        swipeRefresh()
    }

    private fun getData() {
        viewModel.getAllData()
    }

    private fun recyclerViewInitialize() {
        binding.spaceXRecyclerView.setHasFixedSize(true)
        binding.spaceXRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        binding.spaceXRecyclerView.adapter = adapter
    }

    private fun observeData() {
        viewModel.allLaunches.observe(this@MainActivity) {
            it?.let {
                dataList = it
                adapter.updateList(dataList)
            }
        }

        viewModel.yearToLaunches.observe(this@MainActivity) {
            it?.let {
                dataList = it
                adapter.updateList(dataList)
            }
        }

        viewModel.loadingData.observe(this@MainActivity) {
            it?.let {
                if (it) {
                    binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
                    binding.includeErrorLayout.errorLayout.visibility = View.GONE
                    binding.spaceXRecyclerView.visibility = View.GONE
                    binding.searchView.visibility = View.GONE
                } else {
                    binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                }
            }
        }

        viewModel.errorData.observe(this@MainActivity) {
            it?.let {
                if (it) {
                    binding.includeErrorLayout.errorLayout.visibility = View.VISIBLE
                    binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                    binding.spaceXRecyclerView.visibility = View.GONE
                    binding.searchView.visibility = View.GONE
                } else {
                    binding.includeErrorLayout.errorLayout.visibility = View.GONE
                    binding.spaceXRecyclerView.visibility = View.VISIBLE
                    binding.searchView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun itemSelect(year: Int) {
        Toast.makeText(this@MainActivity, year.toString(), Toast.LENGTH_SHORT).show()
        viewModel.getToDate(year)
        observeData()

    }

    private fun sortData(sort: String) {
        if (sort == "low") {
            adapter.updateList(dataList.sortedBy { it.launchDateUtc })
        } else {
            adapter.updateList(dataList.sortedByDescending { it.launchDateUtc })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.byAsc -> {
                sortData("low")
            }
            R.id.byDesc -> {
                sortData("high")
            }
            R.id.refresh -> {
                getData()
            }
            R.id.year_2006 -> {
                itemSelect(2006)
            }
            R.id.year_2007 -> {
                itemSelect(2007)
            }
            R.id.year_2008 -> {
                itemSelect(2008)
            }
            R.id.year_2009 -> {
                itemSelect(2009)
            }
            R.id.year_2010 -> {
                itemSelect(2010)
            }
            R.id.year_2011 -> {
                itemSelect(2011)
            }
            R.id.year_2012 -> {
                itemSelect(2012)
            }
            R.id.year_2013 -> {
                itemSelect(2013)
            }
            R.id.year_2014 -> {
                itemSelect(2014)
            }
            R.id.year_2015 -> {
                itemSelect(2015)
            }
            R.id.year_2016 -> {
                itemSelect(2016)
            }
            R.id.year_2017 -> {
                itemSelect(2017)
            }
            R.id.year_2018 -> {
                itemSelect(2018)
            }
            R.id.year_2019 -> {
                itemSelect(2019)
            }
            R.id.year_2020 -> {
                itemSelect(2020)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchView() {
        searchView = binding.searchView
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterData(p0)
                return true
            }
        })

        binding.spaceXRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    searchView.clearFocus()
                }
            }
        })
    }

    private fun filterData(p0: String?) {
        p0?.let {
            val filteredList = ArrayList<Launch>()
            for (item in dataList) {
                if (item.missionName.lowercase(Locale.ROOT).contains(p0)) {
                    filteredList.add(item)
                }
            }

            if (filteredList.isEmpty()) {
                adapter.updateList(listOf())
                Toast.makeText(this@MainActivity, "No data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.updateList(filteredList)
            }
        }

    }

    private fun swipeRefresh() {
        binding.swipeRefreshMain.setOnRefreshListener {
            binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
            binding.includeErrorLayout.errorLayout.visibility = View.GONE
            binding.spaceXRecyclerView.visibility = View.GONE
            binding.searchView.visibility = View.GONE
            viewModel.getAllData()
            binding.swipeRefreshMain.isRefreshing = false
        }
    }

}
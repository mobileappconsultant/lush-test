package com.example.falcon9launches.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.falcon9launches.adapters.FalconLaunchAdapter
import com.example.falcon9launches.databinding.ActivityMainBinding
import com.example.falcon9launches.ui.viewmodels.FalconLaunchesViewModel
import com.example.falcon9launches.ui.viewmodels.InitialState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FalconLaunchesActivity : AppCompatActivity() {

    private val viewModel by viewModels<FalconLaunchesViewModel>()
    lateinit var falconRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    lateinit var activityMainBinding: ActivityMainBinding
    private val falconListAdapter = FalconLaunchAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        falconRecyclerView = activityMainBinding.falconList
        progressBar = activityMainBinding.loadingView
        errorTextView = activityMainBinding.listError
        falconRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@FalconLaunchesActivity)
            adapter = falconListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.falconLaunchList.observe(this, { result ->
            result.let { list ->
                falconListAdapter.updateFalconLaunch(list)
            }
        })

        viewModel.uiState.observe(this, Observer {
            when(it){
                InitialState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                InitialState.Success -> {
                    progressBar.visibility = View.GONE
                }
                InitialState.Error -> {
                    errorTextView.text = viewModel.errorMessage.value
                    progressBar.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                }
                else -> {
                    // Do nothing
                }
            }
        })
    }
}
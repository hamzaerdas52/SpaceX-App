package com.hamzaerdas.spacexapp.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.hamzaerdas.spacexapp.R
import com.hamzaerdas.spacexapp.databinding.ActivityDetailBinding
import com.hamzaerdas.spacexapp.model.LaunchDetail
import com.hamzaerdas.spacexapp.util.changeBackground
import com.hamzaerdas.spacexapp.util.detailAnimation
import com.hamzaerdas.spacexapp.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private var number: Int = 0
    private lateinit var launchDetail: LaunchDetail

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        intentInitialize()
        getData()
        observeData()
        goBack()

        detailAnimation(binding)
    }

    private fun intentInitialize() {
        intent = intent
        number = intent.getIntExtra("number", 1)
    }

    private fun getData() {
        viewModel.getLaunch(number)
    }

    private fun observeData() {
        viewModel.launch.observe(this) {
            it?.let {
                it.forEach {
                    launchDetail = it
                    isLaunchSuccess()
                    buttonsToWeb()
                    binding.detailBgImage.changeBackground(it.links.missionPatchSmall, binding, 4)
                    binding.launchDetail = it
                }
            }
        }

        viewModel.loadingData.observe(this@DetailActivity) {
            it?.let {
                if (it) {
                    binding.detailLoadingLayout.loadingLayout.visibility = View.VISIBLE
                    binding.detailErrorLayout.errorLayout.visibility = View.GONE
                    binding.detailBgImage.visibility = View.GONE
                    binding.detailLayout.visibility = View.GONE
                } else {
                    binding.detailLoadingLayout.loadingLayout.visibility = View.GONE
                    binding.detailBgImage.visibility = View.VISIBLE
                    binding.detailLayout.visibility = View.VISIBLE
                }
            }
        }

        viewModel.errorData.observe(this@DetailActivity) {
            it?.let {
                if (it) {
                    binding.detailErrorLayout.errorLayout.visibility = View.VISIBLE
                    binding.detailLoadingLayout.loadingLayout.visibility = View.GONE
                } else {
                    binding.detailErrorLayout.errorLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun isLaunchSuccess() {
        if (launchDetail.launchSuccess) {
            binding.isSuccess.text = "Success"
            binding.isSuccess.setBackgroundResource(R.color.isSuccessTrue)
            if(launchDetail.details != null){
                binding.detailTextView.visibility = View.VISIBLE
                binding.detailText.visibility = View.VISIBLE
                binding.detailText.text = launchDetail.details
            }
        } else {
            binding.isSuccess.text = "Failure"
            binding.isSuccess.setBackgroundResource(R.color.isSuccessFalse)
            binding.failureTextView.visibility = View.VISIBLE
            binding.failureText.visibility = View.VISIBLE
            binding.failureText.text = "${launchDetail.launchFailureDetails?.reason ?: ""}\nTime: ${launchDetail.launchFailureDetails?.time}"
        }
    }

    private fun goBack() {
        binding.detailToolbar.goBackIcon.setOnClickListener {
            finish()
        }
    }

    private fun buttonsToWeb() {
        binding.mapButton.setOnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("http://www.google.com/maps/search/${launchDetail.launchSite.siteNameLong}")
            )
            startActivity(viewIntent)
        }

        binding.youtubeButton.setOnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse(launchDetail.links.videoLink)
            )
            startActivity(viewIntent)
        }

        binding.wikipediaButton.setOnClickListener {
            val viewIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse(launchDetail.links.wikipedia)
            )
            startActivity(viewIntent)
        }

    }
}
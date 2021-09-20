package com.example.falcon9launches.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.falcon9launches.utils.DateUtil
import com.example.falcon9launches.R
import com.example.falcon9launches.databinding.ItemFalconBinding
import com.example.falcon9launches.models.FalconLaunchModel

class FalconLaunchAdapter(private var falcons: ArrayList<FalconLaunchModel>): RecyclerView.Adapter<FalconLaunchAdapter.ViewHolder>() {

    fun updateFalconLaunch(updatedFalcons: List<FalconLaunchModel>) {
        falcons.clear()
        falcons.addAll(updatedFalcons)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFalconBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(falcons[position])
    }

    override fun getItemCount() = falcons.size


    class ViewHolder(binding: ItemFalconBinding): RecyclerView.ViewHolder(binding.root) {
        private val launchName = binding.launchName
        private val launchImage = binding.launchImage
        private val launchDate = binding.launchDate
        private val launchStatus = binding.launchStatusIcon

        fun bind(launch: FalconLaunchModel) {
            launchName.text = launch.name
            launchDate.text = DateUtil().formatDate(launch.dateUtc.toString())
            Glide.with(itemView.context)
                .load(launch.badge_image)
                .error(R.drawable.ic_no_image)
                .placeholder(R.drawable.ic_loader)
                .centerInside()
                .into(launchImage)
            if (launch.success == true){
                launchStatus.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_success))
            }else launchStatus.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.ic_failure))
        }

    }


}
package com.rtchubs.arfixture.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.models.SubBook
import com.rtchubs.arfixture.databinding.ItemMedicineSpecialistBinding
import com.rtchubs.arfixture.util.DataBoundListAdapter

class SubDoctorsListAdapter(
    appExecutors: AppExecutors,
    var itemCallback: ((SubBook) -> Unit)? = null
) : DataBoundListAdapter<SubBook, ItemMedicineSpecialistBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<SubBook>() {
        override fun areItemsTheSame(oldItem: SubBook, newItem: SubBook): Boolean {
            return oldItem?.id == newItem?.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: SubBook,
            newItem: SubBook
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): ItemMedicineSpecialistBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_medicine_specialist, parent, false
        )

    override fun bind(binding: ItemMedicineSpecialistBinding, position: Int) {
        val item = getItem(position)
        item.profImage?.let { binding.ivImage.setImageResource(it) }
        item.name?.let { binding.tvDoctorName.text = it }
        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}
package com.rtchubs.arfixture.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.models.SubBook
import com.rtchubs.arfixture.databinding.ItemTopSpecialistBinding
import com.rtchubs.arfixture.util.DataBoundListAdapter

class TopSpecialistAdapter(
    appExecutors: AppExecutors,
    var itemCallback: ((SubBook) -> Unit)? = null
) : DataBoundListAdapter<SubBook, ItemTopSpecialistBinding>(
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

    override fun createBinding(parent: ViewGroup): ItemTopSpecialistBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_top_specialist, parent, false
        )

    override fun bind(binding: ItemTopSpecialistBinding, position: Int) {
        val item = getItem(position)
        item.profImage?.let { binding.ivImage.setImageResource(it) }
        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}
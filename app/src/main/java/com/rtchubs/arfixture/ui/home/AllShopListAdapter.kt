package com.rtchubs.arfixture.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rtchubs.arfixture.AppExecutors
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.AllShopListItemBinding
import com.rtchubs.arfixture.models.LevelWiseShops
import com.rtchubs.arfixture.models.Merchant

import com.rtchubs.arfixture.models.PaymentMethod
import com.rtchubs.arfixture.util.DataBoundListAdapter

class AllShopListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((Merchant) -> Unit)? = null
) : DataBoundListAdapter<LevelWiseShops, AllShopListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<LevelWiseShops>() {
        override fun areItemsTheSame(oldItem: LevelWiseShops, newItem: LevelWiseShops): Boolean {
            return oldItem.level?.id == newItem.level?.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: LevelWiseShops,
            newItem: LevelWiseShops
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    // Properties
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    val onClicked = MutableLiveData<PaymentMethod>()
    override fun createBinding(parent: ViewGroup): AllShopListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_all_shops, parent, false
        )
    }


    override fun bind(binding: AllShopListItemBinding, position: Int) {
        val allShopItem = getItem(position)
        binding.level = allShopItem.level?.name

        val shopListAdapter = ShopListAdapter(
            appExecutors
        ) { item ->
            itemCallback?.invoke(item)
        }

        binding.rvShopList.adapter = shopListAdapter

        shopListAdapter.submitList(allShopItem.shops)
    }
}
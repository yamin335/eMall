package com.qpay.customer.ui.add_payment_methods

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.qpay.customer.AppExecutors
import com.qpay.customer.R
import com.qpay.customer.models.Book
import com.qpay.customer.models.SubBook
import com.qpay.customer.databinding.ItemDoctorsListBinding
import com.qpay.customer.databinding.LayoutBankListRowBinding

import com.qpay.customer.models.Bank
import com.qpay.customer.models.PaymentMethod
import com.qpay.customer.models.payment_account_models.BankOrCard
import com.qpay.customer.util.DataBoundListAdapter

class BankOrCardListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((BankOrCard) -> Unit)? = null

) : DataBoundListAdapter<BankOrCard, LayoutBankListRowBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<BankOrCard>() {
        override fun areItemsTheSame(oldItem: BankOrCard, newItem: BankOrCard): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: BankOrCard,
            newItem: BankOrCard
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    // Properties
    private val viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    val onClicked = MutableLiveData<PaymentMethod>()
    override fun createBinding(parent: ViewGroup): LayoutBankListRowBinding {
        val binding: LayoutBankListRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_bank_list_row, parent, false
        )
        return binding
    }


    override fun bind(binding: LayoutBankListRowBinding, position: Int) {
        val item = getItem(position)
        binding.model = item

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }

        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.logo.setImageResource(R.drawable.engineering_logo)
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        // binding.tvCardNumber.text = item.title
        // Glide.with(binding.ivIcon.context).load(R.drawable.plus).into(binding.ivIcon)
    }


}
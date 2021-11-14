package com.mallzhub.customer.ui.shops

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.ShopDetailsProductListFragmentBinding
import com.mallzhub.customer.models.Merchant
import com.mallzhub.customer.models.Product
import com.mallzhub.customer.ui.common.BaseFragment
import com.mallzhub.customer.ui.home.ProductListAdapter
import com.mallzhub.customer.ui.home.ProductListViewModel
import com.mallzhub.customer.util.showSuccessToast
import com.mallzhub.customer.util.showWarningToast

private const val MERCHANT = "merchant"

class ShopDetailsProductListFragment :
    BaseFragment<ShopDetailsProductListFragmentBinding, ProductListViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_shop_details_product_list
    override val viewModel: ProductListViewModel by viewModels {
        viewModelFactory
    }

    private var merchant: Merchant? = null

    lateinit var productListAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            merchant = it.getSerializable(MERCHANT) as Merchant
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        orderMerchant = OrderMerchant(merchant?.id, merchant?.name, merchant?.user_name,
//            merchant?.password, merchant?.shop_name, merchant?.mobile,
//            merchant?.lat, merchant?.long, merchant?.whatsApp,
//            merchant?.email, merchant?.address, merchant?.shop_address,
//            merchant?.shop_logo, merchant?.thumbnail, merchant?.isActive,
//            merchant?.shopping_mall_id, merchant?.shopping_mall_level_id,
//            merchant?.created_at, merchant?.updated_at)

        viewModel.toastWarning.observe(viewLifecycleOwner, Observer {
            it?.let { message ->
                showWarningToast(requireContext(), message)
                viewModel.toastWarning.postValue(null)
            }
        })

        viewModel.toastSuccess.observe(viewLifecycleOwner, Observer {
            it?.let { message ->
                showSuccessToast(requireContext(), message)
                viewModel.toastSuccess.postValue(null)
            }
        })

        productListAdapter = ProductListAdapter(
            appExecutors,
            object : ProductListAdapter.ProductListActionCallback {
                override fun addToFavorite(item: Product) {
                    viewModel.addToFavorite(item)
                }

                override fun addToCart(item: Product) {
                    viewModel.addToCart(item, 1)
                }

            }) { item ->
            setFragmentResult("goToProductDetails", bundleOf("product" to item))
        }

        viewDataBinding.rvProductList.layoutManager = StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL)
        viewDataBinding.rvProductList.adapter = productListAdapter

        viewModel.productListResponse.observe(viewLifecycleOwner, Observer { productList ->
            if (productList.size < 7) {
                productListAdapter.submitList(productList)
            } else {
                productList.subList(0, 6)
            }
        })

        merchant?.let {
            viewModel.getProductList(it)
        }

        viewDataBinding.moreProduct.setOnClickListener {
            merchant?.let { merchant ->
                setFragmentResult("fromProductList", bundleOf("merchant" to merchant))
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param merchant Selected Merchant.
         * @return A new instance of fragment 'ShopDetailsProductListFragment'.
         */

        //var orderMerchant: OrderMerchant? = null

        @JvmStatic
        fun newInstance(merchant: Merchant) =
            ShopDetailsProductListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(MERCHANT, merchant)
                }
            }
    }
}
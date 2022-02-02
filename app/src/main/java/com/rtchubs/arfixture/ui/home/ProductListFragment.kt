package com.rtchubs.arfixture.ui.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.rtchubs.arfixture.BR
import com.rtchubs.arfixture.R
import com.rtchubs.arfixture.databinding.ProductListFragmentBinding
import com.rtchubs.arfixture.models.Product
import com.rtchubs.arfixture.sceneform.HomeActivity
import com.rtchubs.arfixture.ui.common.BaseFragment
import com.rtchubs.arfixture.util.*
import com.rtchubs.arfixture.util.AppConstants.KEY_FILE_NAME
import com.rtchubs.arfixture.util.AppConstants.KEY_FILE_PATH
import com.rtchubs.arfixture.util.AppConstants.arModelsFolder
import com.rtchubs.arfixture.util.AppConstants.downloadFolder
import kotlinx.coroutines.launch
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.progress.ProgressMonitor
import java.io.File

class ProductListFragment : BaseFragment<ProductListFragmentBinding, ProductListViewModel>(),
    PermissionListener {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_product_list
    override val viewModel: ProductListViewModel by viewModels {
        viewModelFactory
    }

    lateinit var productCategoryAdapter: ProductCategoryListAdapter
    lateinit var productListAdapter: ProductListAdapter
    val args: ProductListFragmentArgs by navArgs()

    override fun onPermissionGranted() {
        val url = "https://arfixture.s3.ap-southeast-1.amazonaws.com/ar_models.zip"
        val filePath = FileUtils.getLocalStorageFilePath(
            requireContext(),
            downloadFolder
        )
        val fileName = url.split("/").last()
        if (File("${filePath}/${fileName}").exists()) {
            val arModelsFolderPath = FileUtils.getLocalStorageFilePath(
                requireContext(),
                arModelsFolder
            )
            val list = if (fileName.contains(".")) fileName.split(".") else listOf(fileName)
            val arModelsFileName = list[0]
            if (File("${arModelsFolderPath}/${arModelsFileName}").exists()) {
                goToARView(arModelsFolderPath, arModelsFileName)
            } else {
                lifecycleScope.launch {
                    unzipARModels(filePath, fileName, arModelsFolderPath, arModelsFileName)
                }
            }
        } else {
            viewModel.downloadFileFromUrl(url, filePath, fileName)
        }
    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        showErrorToast(requireContext(), "Unable to show AR View! Please allow required permissions.")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

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

        viewDataBinding.toolbar.title = args.merchant.name

        productCategoryAdapter = ProductCategoryListAdapter(
            appExecutors
        ) { item ->
            //viewDataBinding.imageUrl = item
        }

        viewDataBinding.rvProductCategory.adapter = productCategoryAdapter

        productCategoryAdapter.submitList(listOf("T-Shirt", "Shoes", "Pants", "Hats", "Shorts", "Shocks", "Sunglasses"))

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
            navController.navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsNavGraph(item))
        }

        //viewDataBinding.rvProductList.addItemDecoration(GridRecyclerItemDecorator(2, 40, true))
        viewDataBinding.rvProductList.layoutManager = StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL)
        viewDataBinding.rvProductList.adapter = productListAdapter

        viewModel.productListResponse.observe(viewLifecycleOwner, Observer { productList ->
            productListAdapter.submitList(productList)
            HomeActivity.productList = productList as ArrayList<Product>
        })

        viewModel.showHideProgress.observe(viewLifecycleOwner, Observer { pair ->
            val shouldShowProgress = pair.first
            val progress = pair.second
            if (shouldShowProgress) {
                viewDataBinding.progressView.visibility = View.VISIBLE
                viewDataBinding.loader.progress = progress
                viewDataBinding.progress.text = "$progress%"
                if (progress == 100) viewDataBinding.progressView.visibility = View.GONE
            } else {
                viewDataBinding.progressView.visibility = View.GONE
            }
        })

        viewModel.fileDownloadResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response == null) return@Observer
            if (response.isSuccessful) {
                val arModelsFolderPath = FileUtils.getLocalStorageFilePath(
                    requireContext(),
                    arModelsFolder
                )
                val list = if (response.fileName.contains(".")) response.fileName.split(".") else listOf(response.fileName)
                val arModelsFileName = list[0]
                if (File("${arModelsFolderPath}/${arModelsFileName}").exists()) {
                    goToARView(arModelsFolderPath, arModelsFileName)
                } else {
                    lifecycleScope.launch {
                        unzipARModels(response.filePath, response.fileName, arModelsFolderPath, arModelsFileName)
                    }
                }
            } else {
                showErrorToast(requireContext(), "Unable to show AR View!")
            }
            viewModel.fileDownloadResponse.postValue(null)
        })

        viewDataBinding.btnARView.setOnClickListener {
            TedPermission.with(requireContext())
                .setPermissionListener(this)
                .setDeniedMessage(getString(R.string.if_you_reject_these_permission_the_app_wont_work_perfectly))
                .setPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).check()
        }

        viewModel.getProductList(args.merchant)
    }

    private fun goToARView(filePath: String, fileName: String) {
        navigateTo(ProductListFragmentDirections.actionProductListFragmentToProductARViewFragment(filePath, fileName))
//        val arIntent = Intent(requireActivity(), HomeActivity::class.java)
//        arIntent.putExtra(KEY_FILE_PATH, filePath)
//        arIntent.putExtra(KEY_FILE_NAME, fileName)
//        startActivity(arIntent)
//        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun unzipARModels(inputFilePath: String, inputFilename: String, outputFilePath: String, outputFileName: String) {
        viewModel.showHideProgress.postValue(Pair(true, 0))
        try {
            val zipFile = ZipFile(File(inputFilePath, inputFilename))
//            if (zipFile.isEncrypted) {
//                zipFile.setPassword(password)
//            }
            val progressMonitor: ProgressMonitor = zipFile.progressMonitor

            zipFile.isRunInThread = true
            zipFile.extractAll(outputFilePath)

            while (progressMonitor.state != ProgressMonitor.State.READY) {
                viewModel.showHideProgress.postValue(Pair(true, progressMonitor.percentDone))
                //viewDataBinding.progressBar.progress = progressMonitor.percentDone
                //println("Percentage done: " + progressMonitor.percentDone)
                //println("Current file: " + progressMonitor.fileName)
                //println("Current task: " + progressMonitor.currentTask)
                //Thread.sleep(100)
            }

            when (progressMonitor.result) {
                ProgressMonitor.Result.SUCCESS -> {
                    viewModel.showHideProgress.postValue(Pair(false, 100))
                    showSuccessToast(requireContext(), "Unzipping SUCCESS!")
                    goToARView(outputFilePath, outputFileName)
                }
                ProgressMonitor.Result.ERROR -> {
                    viewModel.showHideProgress.postValue(Pair(false, 100))
                    showErrorToast(requireContext(), "Unzipping FAILED!")
                }
                ProgressMonitor.Result.CANCELLED -> {
                    viewModel.showHideProgress.postValue(Pair(false, 100))
                    showErrorToast(requireContext(), "Unzipping CANCELLED!")
                }
                else -> {
                    viewModel.showHideProgress.postValue(Pair(false, 100))
                    showErrorToast(requireContext(), "Unzipping FAILED!")
                }
            }
        } catch (e: ZipException) {
            e.printStackTrace()
            showErrorToast(requireContext(), "Unzipping FAILED!")
            viewModel.showHideProgress.postValue(Pair(false, 100))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_list, menu)

        val menuItem = menu.findItem(R.id.menu_cart)
        val actionView = menuItem.actionView
        val badge = actionView.findViewById<TextView>(R.id.badge)
        badge.text = viewModel.cartItemCount.value?.toString()
        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

        viewModel.cartItemCount.observe(viewLifecycleOwner, Observer {
            it?.let { value ->
                if (value < 1) {
                    badge.visibility = View.INVISIBLE
                    return@Observer
                } else {
                    badge.visibility = View.VISIBLE
                    badge.text = value.toString()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }

            R.id.menu_cart -> {
                navController.navigate(ProductListFragmentDirections.actionProductListFragmentToCartNavGraph())
            }
        }

        return true
    }
}
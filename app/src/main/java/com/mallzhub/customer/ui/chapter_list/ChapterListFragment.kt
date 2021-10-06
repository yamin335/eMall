package com.mallzhub.customer.ui.chapter_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.customer.BR
import com.mallzhub.customer.R
import com.mallzhub.customer.databinding.FragmentChapterListBinding
import com.mallzhub.customer.ui.common.BaseFragment

class ChapterListFragment : BaseFragment<FragmentChapterListBinding, ChapterListViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_chapter_list
    override val viewModel: ChapterListViewModel by viewModels { viewModelFactory }

    lateinit var chapterListAdapter: ChapterListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(viewDataBinding.toolbar)

        chapterListAdapter = ChapterListAdapter(appExecutors) {
//            navController.navigate(
//                //ChapterListFragmentDirections.actionChapterListToVideoPlay("vedio_file")
//                //ChapterListFragmentDirections.actionChapterListToWebView()
//            )
        }

        viewDataBinding.rvChapterList.adapter = chapterListAdapter
        chapterListAdapter.submitList(viewModel.chapterListData)
    }
}
package com.rtchubs.edokanpat.ui.login

import androidx.lifecycle.ViewModel
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.prefs.PreferencesHelper
import javax.inject.Inject

class ViewPagerViewModel @Inject constructor(private val preferencesHelper: PreferencesHelper) :
    ViewModel() {
    val slideDataList = listOf<SlideData>(
        SlideData(R.drawable.slider_image_1, "Learn with online tutor", "A Complete Solution for e-learning"),
        SlideData(R.drawable.slider_image_1, "Learn with online tutor", "A Complete Solution for e-learning"),
        SlideData(R.drawable.slider_image_1, "Learn with online tutor", "A Complete Solution for e-learning"),
        SlideData(R.drawable.slider_image_1, "Learn with online tutor", "A Complete Solution for e-learning"),
        SlideData(R.drawable.slider_image_1, "Learn with online tutor", "A Complete Solution for e-learning")
    )

    inner class SlideData(
        var slideImage: Int,
        var textTitle: String,
        var descText: String
    )
}
package com.profs.languageapp.data.utils

import com.profs.languageapp.R
import com.profs.languageapp.domain.model.Language
import com.profs.languageapp.domain.model.Page
import javax.inject.Inject

class Constants @Inject constructor() {

    fun getLanguages() = listOf(
        Language("en", R.string.lang_english),
        Language("ru", R.string.lang_russian),
        Language("zh", R.string.lang_chinese),
        Language("be", R.string.lang_belarus),
        Language("kk", R.string.lang_kazakh)
    )

    fun getOnboardingPages() = listOf(
        Page(
            R.drawable.onboarding_1,
            R.drawable.slider_1,
            R.string.onb_1_1,
            R.string.onb_1_2,
            R.string.next
        ),
        Page(
            R.drawable.onboarding_2,
            R.drawable.slider_2,
            R.string.onb_2_1,
            R.string.onb_2_2,
            R.string.more
        ),
        Page(
            R.drawable.onboarding_3,
            R.drawable.slider_3,
            R.string.onb_3_1,
            R.string.onb_3_2,
            R.string.choose_language
        ),
    )
}
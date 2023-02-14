package com.basedagger.common.base

interface BaseActivityView {
    fun toolbarVisibility(visible: Boolean)
    fun resetToolbar()
    fun setCustomToolbar(menuToolbar: Int, title: String? = null)
    fun setCustomOnBackPressed(listner: () -> Boolean)
    fun resetOnBackPressed()
    fun changeLanguage(language: String)
}
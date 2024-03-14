package com.general.common.util

import android.graphics.Color
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.general.common.R
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import java.util.Locale

@BindingAdapter("textHTML")
fun TextView.setHTMLText(value: String?) {
    value?.let {
        HTMLUtils.setTextViewHTML(
            context,
            this,
            value
        )
    }
}

@BindingAdapter(value = ["imageUrl", "isRounded", "errorImage"], requireAll = false)
fun AppCompatImageView.setGlideImage(
    imageUrl: String?,
    isRounded: Boolean = false,
    errorImage: Int? = R.color.color_primary
) {
    val radius = resources.getDimensionPixelSize(R.dimen.dimen_small)
    if (imageUrl.isNullOrEmpty()) {
        setImageDrawable(
            ContextCompat.getDrawable(
                context,
                errorImage ?: R.color.color_primary
            )
        )
        return
    }
//    if (imageUrl.substringAfterLast(".").equals("svg", true)) {
//        GlideToVectorYou
//            .init()
//            .with(this)
//            .load(Uri.parse(imageUrl), imageView)
//        return
//    }
    Glide
        .with(context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply {
            if (isRounded) transform(RoundedCorners(radius))
        }
        .error(errorImage)
        .placeholder(R.drawable.bg_rounded)
        .into(this)
}

@BindingAdapter("setProfile")
fun AppCompatImageView.setProfileImage(
    profile: String?
) {
    if (profile.isNullOrEmpty()) {
        Glide
            .with(context)
            .load(R.color.color_primary)
            .transition(DrawableTransitionOptions.withCrossFade())
            .circleCrop()
            .placeholder(android.R.color.black)
            .error(R.drawable.ic_no_image)
            .into(this)
        return
    }
//    if (profile.substringAfterLast(".").equals("svg", true)) {
//        GlideToVectorYou
//            .init()
//            .with(getParentActivity())
//            .requestBuilder
//            .load(Uri.parse(it))
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .circleCrop()
//            .error(R.drawable.ic_no_image)
//            .placeholder(android.R.color.black)
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
//            .skipMemoryCache(true)
//            .into(this)
//        return
//    }
    Glide
        .with(context)
        .load(profile)
        .transition(DrawableTransitionOptions.withCrossFade())
        .circleCrop()
        .placeholder(android.R.color.black)
        .error(R.drawable.ic_no_image)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .into(this)
}

/**
 * @param dateString (required)
 * @param inFormat (required)
 * @param outFormat (required)
 * @param locale default (optional)
 * @param isOrdinal (optional)
 * */
@BindingAdapter(
    value = ["dateString", "inFormat", "outFormat", "locale", "isOrdinal"],
    requireAll = false
)
fun TextView.bindDate(
    dateString: String?,
    inFormat: String?,
    outFormat: String,
    locale: Locale?,
    isOrdinal: Boolean?
) {
    text = dateString?.let {
        DateUtils.getDateStringWithFormat(
            it,
            inFormat ?: DateUtils.DEFAULT_TIME_FORMAT,
            outFormat,
            locale,
            isOrdinal
        )
    }
}

@BindingAdapter("showSkeleton")
fun SkeletonLayout.showSkeleton(displayed: Boolean) {
    if (displayed) showSkeleton()
    else showOriginal()
}

@BindingAdapter(
    value = ["showLoadingButton", "textButtonLoading", "progressButtonColor"],
    requireAll = false
)
fun AppCompatButton.setLoadingButton(
    showLoadingButton: Boolean,
    textButtonLoading: String,
    @ColorRes progressButtonColor: Int? = null
) {
    if (showLoadingButton) showProgress {
        progressColor = progressButtonColor ?: Color.WHITE
    } else hideProgress(textButtonLoading)
}

@BindingAdapter("underline")
fun SearchView.setUnderline(
    @ColorRes colorUnderline: Int?
) {
    colorUnderline?.let {
        val searchplate =
            findViewById<View>(androidx.appcompat.R.id.search_plate)
        searchplate.background.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                it, BlendModeCompat.SRC_ATOP
            )
    }
}

@BindingAdapter("loadText")
fun WebView.setLoadText(
    textHtml: String?
) {
    textHtml?.let {
        this.loadData(
            it,
            "text/html",
            "UTF-8"
        )
    }
}

@BindingAdapter("textPrice")
fun TextView.setPriceText(value: Double?) {
    text =
        CurrencyUtils.simpleCovertCurrency(context, value.toString())
}
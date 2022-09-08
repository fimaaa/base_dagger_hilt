package com.example.basedagger.utill

import android.content.Context
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.basedagger.R
import com.example.basedagger.databinding.BasedialogAlertBinding
import com.example.basedagger.databinding.CustomTwobuttonDialogBinding
import com.example.basedagger.extension.gone
import com.example.basedagger.extension.visible
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk27.coroutines.onClick

object DialogUtils {

    data class DefaultDialogData(
        val title: String,
        val desc: String,
        val txtButton1: String,
        val txtButton2: String,
        val txtLink: String,
        val iconVisibility: Boolean
    )

    fun showDialogAlert(
        context: Context,
        title: String,
        desc: String,
        listener: () -> Unit = {}
    ) {
        val view = context.layoutInflater.inflate(R.layout.basedialog_alert, null as ViewGroup?)
        val binding = BasedialogAlertBinding.bind(view)
        with(binding) {
            titleDialog.text = title
            descDialog.text = desc

            val builder = AlertDialog.Builder(context)
            builder.setView(root)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded)

            ivCloseBasedialogalert.onClick {
                dialog.dismiss()
            }
            dialog.setOnDismissListener {
                listener.invoke()
            }
        }
    }

    fun showDefaultDialog(
        context: Context,
        defaultData: DefaultDialogData,
        listenerBtn1: () -> Unit,
        listenerBtn2: (() -> Unit)? = null,
        listenerLink: (() -> Unit)? = null
    ) {
        val view = context.layoutInflater.inflate(R.layout.custom_twobutton_dialog, null as ViewGroup?)
        val binding = CustomTwobuttonDialogBinding.bind(view)
        with(binding) {
            titleDialog.text = defaultData.title
            descDialog.text = defaultData.desc
            btn1Dialog.text = defaultData.txtButton1
            imgDialog.isVisible = defaultData.iconVisibility

            btn2Dialog.text = defaultData.txtButton2
            if (defaultData.txtLink.isEmpty()) {
                linkDialog.gone()
            } else {
                linkDialog.visible()
                linkDialog.text = defaultData.txtLink
            }

            linkDialog.onClick {
                listenerLink?.invoke()
            }
            val builder = AlertDialog.Builder(context)
            builder.setView(root)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded)
            btn2Dialog.onClick {
                listenerBtn2?.invoke()
                dialog.dismiss()
            }
            btn1Dialog.setOnClickListener {
                listenerBtn1.invoke()
                dialog.dismiss()
            }
        }
    }
}
package com.basedagger.common.utill

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.basedagger.common.R
import com.basedagger.common.databinding.BasedialogAlertBinding
import com.basedagger.common.databinding.CustomTwobuttonDialogBinding
import com.basedagger.common.extension.gone
import com.basedagger.common.extension.visible

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
        val view =
            LayoutInflater.from(context).inflate(R.layout.basedialog_alert, null as ViewGroup?)
        val binding = BasedialogAlertBinding.bind(view)
        with(binding) {
            titleDialog.text = title
            descDialog.text = desc

            val builder = AlertDialog.Builder(context)
            builder.setView(root)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded)

            ivCloseBasedialogalert.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setOnDismissListener {
                listener.invoke()
            }
        }
    }

    fun showAlertDialog(
        mContext: Context,
        message: String? = null,
        title: String = mContext.getString(R.string.error_default),
        onDismissListener: (() -> Unit)? = null
    ) {
        val layoutInflater: LayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = BasedialogAlertBinding.inflate(layoutInflater).apply {
            titleDialog.text = title
            descDialog.text = message ?: mContext.getString(R.string.error_default)
        }
        val builder = AlertDialog.Builder(mContext)
        builder.setView(binding.root)
        onDismissListener?.let { listener ->
            builder.setOnDismissListener {
                listener.invoke()
            }
        }
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(true)
        binding.ivCloseBasedialogalert.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showDefaultDialog(
        context: Context,
        defaultData: DefaultDialogData,
        listenerBtn1: () -> Unit,
        listenerBtn2: (() -> Unit)? = null,
        listenerLink: (() -> Unit)? = null
    ) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_twobutton_dialog, null as ViewGroup?)
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

            linkDialog.setOnClickListener {
                listenerLink?.invoke()
            }
            val builder = AlertDialog.Builder(context)
            builder.setView(root)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded)
            btn2Dialog.setOnClickListener {
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
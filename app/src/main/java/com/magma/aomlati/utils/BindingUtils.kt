/*
 * Created by Ali Al-Sheekh Jassem on 4/21/21 9:16 PM
 * Copyright (c) 2021 . All rights reserved .
 * Last modified 4/21/21 8:37 PM
 */
package com.magma.aomlati.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.textfield.TextInputLayout
import com.magma.aomlati.R
import com.magma.aomlati.model.Title
import com.magma.aomlati.utils.StringRuleUtil.StringRule
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object BindingUtils {
    private const val TAG = "BindingUtils"

    @JvmStatic
    @BindingAdapter("validation", "errorMsg")
    fun setErrorEnable(
        textInputLayout: TextInputLayout,
        stringRule: StringRule,
        errorMsg: String?
    ) {
        if (textInputLayout.editText != null)
            textInputLayout.editText!!.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    val text = textInputLayout.editText!!.text.toString()
                    val notEmpty =
                        textInputLayout.context.getString(R.string.app_name)
                    when {
                        text.isEmpty() -> textInputLayout.error =
                            notEmpty
                        stringRule.validate(
                            textInputLayout.editText!!.text
                        ) -> textInputLayout.error = errorMsg
                        else -> textInputLayout.error = null
                    }
                }
            })
    }

    @JvmStatic
    @BindingAdapter("confirm_password", "errorMsg")
    fun setErrorEnable(
        textInputLayoutConfirmPassword: TextInputLayout,
        edtPassword: Editable,
        errorMsg: String?
    ) {
        if (textInputLayoutConfirmPassword.editText != null) textInputLayoutConfirmPassword.editText!!.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    val text = editable.toString()
                    Log.d(TAG, "CCCP afterTextChanged: $text")
                    Log.d(TAG, "CCCP afterTextChanged: $errorMsg")
                    val notEmpty =
                        textInputLayoutConfirmPassword.context.getString(R.string.app_name)
                    when {
                        text.isEmpty() -> textInputLayoutConfirmPassword.error = notEmpty
                        text != edtPassword.toString() ->
                            textInputLayoutConfirmPassword.error = errorMsg
                        else -> textInputLayoutConfirmPassword.error = null
                    }
                }
            })
    }

    @JvmStatic
    @BindingAdapter("image_url")
    fun setImageUrl(imageView: ImageView, imageUri: String?) {
        Log.d(TAG, "setImageHostUrl: $imageUri")
        Glide.with(imageView.context)
            .load(imageUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("image_drawable")
    fun setImageRes(imageView: ImageView, imageDrawable: Drawable) {
        Log.d(TAG, "setImageRes: $imageDrawable")
        Glide.with(imageView)
            .load(imageDrawable)
            .fitCenter().into(imageView)
    }

    @JvmStatic
    @BindingAdapter("text_translate")
    fun setTextTranslate(textView: TextView, title: Title?) {
        Log.d(TAG, "setTextTranslate: $title")
        if (title == null) return
        val lang = Locale.getDefault().language
        Log.d(TAG, "setTextTranslate: $lang")
        when (lang) {
            "en" -> textView.text = title.en
            "ar" -> textView.text = title.ar
            "tr" -> textView.text = title.tr
        }
    }

    @JvmStatic
    @BindingAdapter("text_time")
    fun setTextTime(textView: TextView, date: String?) {
        Log.d(TAG, "DDD text date: $date")
        if (date == null) return
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        //format result time to wanted format like : 6:00 AM
        val formatOut = SimpleDateFormat("hh:mm a", Locale.getDefault())
        formatOut.timeZone = TimeZone.getDefault()
        try {
            val date1 = format.parse(date)
            if (date1 != null) textView.text = formatOut.format(date1)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
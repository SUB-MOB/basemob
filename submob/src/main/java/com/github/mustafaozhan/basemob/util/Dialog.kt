/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
@file:Suppress("unused")

package com.github.mustafaozhan.basemob.util

import android.app.Activity
import android.app.AlertDialog
import com.github.mustafaozhan.basemob.R

@Suppress("LongParameterList")
fun showDialog(
    activity: Activity,
    title: String,
    message: String,
    positiveButton: String,
    cancelable: Boolean = true,
    function: (() -> Unit)? = null
) {
    if (!activity.isFinishing) {
        val dialog = AlertDialog
            .Builder(activity, R.style.AlertDialogCustom)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButton) { _, _ -> function?.invoke() }

        if (cancelable) {
            dialog
                .setCancelable(cancelable)
                .setNegativeButton(activity.getString(android.R.string.cancel), null)
        }

        dialog.show()
    }
}

@Suppress("LongParameterList")
fun showDialog(
    activity: Activity,
    title: Int,
    message: Int,
    positiveButton: Int,
    cancelable: Boolean = true,
    function: (() -> Unit)? = null
) {
    if (!activity.isFinishing) {
        val dialog = AlertDialog
            .Builder(activity, R.style.AlertDialogCustom)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle(activity.getString(title))
            .setMessage(activity.getString(message))
            .setPositiveButton(activity.getText(positiveButton)) { _, _ -> function?.invoke() }

        if (cancelable) {
            dialog
                .setCancelable(cancelable)
                .setNegativeButton(activity.getString(android.R.string.cancel), null)
        }
        dialog.show()
    }
}

@Suppress("LongParameterList")
fun showSingleChoiceDialog(
    activity: Activity,
    title: String,
    items: Array<String>,
    selectedIndex: Int = 1,
    choiceAction: ((Int) -> Unit)? = null
) {
    if (!activity.isFinishing) {
        AlertDialog
            .Builder(activity, R.style.AlertDialogCustom)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle(title)
            .setSingleChoiceItems(items, selectedIndex) { _, which ->
                choiceAction?.invoke(which)
            }.show()
    }
}

@Suppress("LongParameterList")
fun showSingleChoiceDialog(
    activity: Activity,
    title: Int,
    items: Array<String>,
    selectedIndex: Int = 1,
    choiceAction: ((Int) -> Unit)? = null
) {
    if (!activity.isFinishing) {
        AlertDialog
            .Builder(activity, R.style.AlertDialogCustom)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle(activity.getString(title))
            .setSingleChoiceItems(items, selectedIndex) { _, which ->
                choiceAction?.invoke(which)
            }.show()
    }
}

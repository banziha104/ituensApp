package com.lyj.ituensapp.core.permission

import android.content.Context
import androidx.appcompat.app.AlertDialog

interface PermissionDialogBuilder {
    fun buildAlertDialog(context: Context, positiveEvent : DialogCallBack, negativeEvent : DialogCallBack): AlertDialog.Builder
}
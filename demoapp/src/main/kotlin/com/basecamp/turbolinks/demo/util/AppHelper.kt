package com.basecamp.turbolinks.demo.util

import android.app.AlertDialog
import android.content.Context
import com.basecamp.turbolinks.demo.R

@Suppress("ConstantConditionIf")
fun verifyServerIpAddress(context: Context) {
    if (Constants.IP_ADDRESS == "x.x.x.x") {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.server_ip_warning))
            setMessage(context.getString(R.string.server_ip_warning_message))
            setPositiveButton(R.string.server_ip_warning_button) { _, _ -> }
        }.create().show()
    }
}
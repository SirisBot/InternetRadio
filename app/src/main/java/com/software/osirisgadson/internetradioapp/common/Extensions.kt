package com.software.osirisgadson.internetradioapp.common

import android.content.Context
import android.widget.Toast


fun Any.toast(context: Context) {
    Toast.makeText(context, this.toString(), Toast.LENGTH_LONG).show()
}
package io.github.tuguzt.flexibleproject.data.datasource.local

import android.content.Context
import io.github.tuguzt.flexibleproject.data.datasource.local.model.MyObjectBox
import io.objectbox.BoxStore

class ObjectBox(context: Context) {
    val store: BoxStore = MyObjectBox.builder()
        .androidContext(context.applicationContext)
        .build()
}

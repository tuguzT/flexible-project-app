package io.github.tuguzt.flexibleproject.data

import android.content.Context
import io.github.tuguzt.flexibleproject.data.model.MyObjectBox
import io.github.tuguzt.flexibleproject.data.model.UserEntity
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.Admin
import io.objectbox.kotlin.boxFor

class DatabaseClient(context: Context) {
    private val boxStore: BoxStore = MyObjectBox.builder()
        .androidContext(context)
        .build()
        .apply {
            if (BuildConfig.DEBUG) {
                val admin = Admin(this)
                admin.start(context)
            }
        }

    internal val userBox: Box<UserEntity> = boxStore.boxFor()
}

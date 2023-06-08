package io.github.tuguzt.flexibleproject.view.utils

import android.content.Context
import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import java.util.Locale

fun Context.setLocale(language: Language) {
    val locale = when (language) {
        Language.English -> Locale("en")
        Language.Russian -> Locale("ru")
    }
    Locale.setDefault(locale)

    val resources = resources
    val configuration = resources.configuration
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
    // TODO solve deprecation issues
}

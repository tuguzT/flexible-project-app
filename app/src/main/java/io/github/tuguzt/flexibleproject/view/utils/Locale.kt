package io.github.tuguzt.flexibleproject.view.utils

import android.content.Context
import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import java.util.Locale

fun Context.setLocale(language: Language) {
    val locale = when (language) {
        Language.English -> Locale("en")
        Language.Russian -> Locale("ru")
    }
    val resources = resources
    val configuration = resources.configuration

    val localeNotFound = configuration.locales.indexOf(locale) == -1
    if (!localeNotFound) return

    Locale.setDefault(locale)
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
    // TODO solve deprecation issues
}

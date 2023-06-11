package io.github.tuguzt.flexibleproject.view.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import io.github.tuguzt.flexibleproject.R
import io.github.tuguzt.flexibleproject.domain.model.settings.Language
import io.github.tuguzt.flexibleproject.domain.model.settings.Theme
import io.github.tuguzt.flexibleproject.domain.model.user.Role
import io.github.tuguzt.flexibleproject.domain.model.project.Visibility as ProjectVisibility
import io.github.tuguzt.flexibleproject.domain.model.workspace.Visibility as WorkspaceVisibility

fun Role.toTranslatedString(context: Context): String =
    when (this) {
        Role.User -> context.getString(R.string.user)
        Role.Moderator -> context.getString(R.string.moderator)
        Role.Administrator -> context.getString(R.string.administrator)
    }

fun WorkspaceVisibility.toTranslatedString(context: Context): String =
    when (this) {
        WorkspaceVisibility.Public -> context.getString(R.string.visibility_public)
        WorkspaceVisibility.Private -> context.getString(R.string.visibility_private)
    }

fun ProjectVisibility.toTranslatedString(context: Context): String =
    when (this) {
        ProjectVisibility.Public -> context.getString(R.string.visibility_public)
        ProjectVisibility.Private -> context.getString(R.string.visibility_private)
    }

fun Theme.toTranslatedString(context: Context): String =
    when (this) {
        Theme.System -> context.getString(R.string.theme_system)
        Theme.Light -> context.getString(R.string.theme_light)
        Theme.Dark -> context.getString(R.string.theme_dark)
    }

fun Language.toTranslatedString(context: Context): String =
    when (this) {
        Language.English -> context.getString(R.string.language_english)
        Language.Russian -> context.getString(R.string.language_russian)
    }

@Composable
@ReadOnlyComposable
fun Role.toTranslatedString(): String = toTranslatedString(context = LocalContext.current)

@Composable
@ReadOnlyComposable
fun WorkspaceVisibility.toTranslatedString(): String =
    toTranslatedString(context = LocalContext.current)

@Composable
@ReadOnlyComposable
fun ProjectVisibility.toTranslatedString(): String =
    toTranslatedString(context = LocalContext.current)

@Composable
@ReadOnlyComposable
fun Theme.toTranslatedString(): String = toTranslatedString(context = LocalContext.current)

@Composable
@ReadOnlyComposable
fun Language.toTranslatedString(): String = toTranslatedString(context = LocalContext.current)

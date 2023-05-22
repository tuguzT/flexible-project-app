package io.github.tuguzt.flexibleproject.domain.model.filter

import kotlin.text.Regex

data class Regex(val regex: Regex) : Filter<CharSequence> {
    override fun satisfies(input: CharSequence): Boolean = regex matches input
}

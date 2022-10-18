package io.github.tuguzt.flexibleproject.viewmodel.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mu.KotlinLogging
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    companion object {
        private val logger = KotlinLogging.logger {}
    }
}

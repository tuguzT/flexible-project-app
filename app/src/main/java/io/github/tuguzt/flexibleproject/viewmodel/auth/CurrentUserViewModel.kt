package io.github.tuguzt.flexibleproject.viewmodel.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.model.user.UserId
import javax.inject.Inject

@HiltViewModel
class CurrentUserViewModel @Inject constructor() : ViewModel() {
    val currentUserId = UserId("timur")
}

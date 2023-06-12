package io.github.tuguzt.flexibleproject.viewmodel.basic.project

import androidx.lifecycle.viewModelScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.tuguzt.flexibleproject.domain.usecase.project.DeleteProject
import io.github.tuguzt.flexibleproject.domain.usecase.project.FilterProjects
import io.github.tuguzt.flexibleproject.viewmodel.StoreViewModel
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.State
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStoreProvider
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    projects: FilterProjects,
    delete: DeleteProject,
    storeFactory: StoreFactory,
) : StoreViewModel<Intent, State, Label>() {
    override val provider = ProjectStoreProvider(
        projects = projects,
        delete = delete,
        storeFactory = storeFactory,
        coroutineContext = viewModelScope.coroutineContext,
    )
}

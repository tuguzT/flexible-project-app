package io.github.tuguzt.flexibleproject.viewmodel.basic.project.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import io.github.tuguzt.flexibleproject.domain.model.BaseException
import io.github.tuguzt.flexibleproject.domain.model.Result
import io.github.tuguzt.flexibleproject.domain.model.filter.Equal
import io.github.tuguzt.flexibleproject.domain.model.project.Project
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectFilters
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectId
import io.github.tuguzt.flexibleproject.domain.model.project.ProjectIdFilters
import io.github.tuguzt.flexibleproject.domain.usecase.project.DeleteProject
import io.github.tuguzt.flexibleproject.domain.usecase.project.FilterProjects
import io.github.tuguzt.flexibleproject.viewmodel.StoreProvider
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Intent
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.Label
import io.github.tuguzt.flexibleproject.viewmodel.basic.project.store.ProjectStore.State
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProjectStoreProvider(
    private val projects: FilterProjects,
    private val delete: DeleteProject,
    private val storeFactory: StoreFactory,
    private val coroutineContext: CoroutineContext,
) : StoreProvider<Intent, State, Label> {
    override fun provide(): ProjectStore =
        object :
            ProjectStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = ProjectStore::class.simpleName,
                initialState = State(project = null, loading = true),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Message {
        object Loading : Message
        data class Loaded(val project: Project) : Message
        data class NotFound(val id: ProjectId) : Message
        data class ProjectDeleted(val project: Project) : Message
        object Error : Message
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Message, Label>(
        mainContext = coroutineContext,
    ) {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.Load -> load(intent.id)
                Intent.Delete -> delete(state = getState())
            }

        private fun load(id: ProjectId) {
            dispatch(Message.Loading)
            scope.launch {
                val filters = ProjectFilters(id = ProjectIdFilters(eq = Equal(id)))
                val projectsFlow = when (val result = projects.projects(filters)) {
                    is Result.Success -> result.data
                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is FilterProjects.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                        return@launch
                    }
                }
                projectsFlow.collect { projects ->
                    val project = projects.firstOrNull()
                    if (project == null) {
                        dispatch(Message.NotFound(id))
                        publish(Label.NotFound(id))
                        return@collect
                    }
                    dispatch(Message.Loaded(project))
                }
            }
        }

        private fun delete(state: State) {
            dispatch(Message.Loading)
            val project = state.project ?: run {
                dispatch(Message.Error)
                publish(Label.UnknownError)
                return
            }
            scope.launch {
                when (val result = delete.delete(project.id)) {
                    is Result.Success -> {
                        @Suppress("NAME_SHADOWING")
                        val project = result.data
                        dispatch(Message.ProjectDeleted(project))
                        publish(Label.ProjectDeleted(project))
                    }

                    is Result.Error -> {
                        dispatch(Message.Error)
                        when (val error = result.error) {
                            is DeleteProject.Exception.NoProject -> {
                                dispatch(Message.NotFound(project.id))
                                publish(Label.NotFound(project.id))
                            }

                            is DeleteProject.Exception.Repository -> when (error.error) {
                                is BaseException.LocalStore -> publish(Label.LocalStoreError)
                                is BaseException.NetworkAccess -> publish(Label.NetworkAccessError)
                                is BaseException.Unknown -> publish(Label.UnknownError)
                            }
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Message> {
        override fun State.reduce(msg: Message): State =
            when (msg) {
                Message.Loading -> copy(loading = true)
                is Message.Loaded -> copy(project = msg.project, loading = false)
                is Message.NotFound -> copy(project = null, loading = false)
                is Message.ProjectDeleted -> copy(project = null, loading = false)
                Message.Error -> copy(loading = false)
            }
    }
}

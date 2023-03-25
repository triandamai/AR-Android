package app.trian.tudu.feature.inputNote

import androidx.lifecycle.SavedStateHandle
import app.trian.tudu.base.BaseViewModel
import app.trian.tudu.data.domain.task.GetDetailTaskUseCase
import app.trian.tudu.data.domain.task.UpdateTaskNoteUseCase
import app.trian.tudu.data.utils.Response
import app.trian.tudu.feature.inputNote.InputNoteEvent.SetTaskNote
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDetailTaskUseCase: GetDetailTaskUseCase,
    private val updateTaskNoteUseCase: UpdateTaskNoteUseCase
) : BaseViewModel<InputNoteState, InputNoteEvent>(InputNoteState()) {
    init {
        handleActions()
        getDetailTask(getTaskId())
    }

    private fun getTaskId() = savedStateHandle.get<String>(InputNote.keyArgs).orEmpty()

    private fun getDetailTask(taskId: String) = async {
        getDetailTaskUseCase(taskId)
            .collect {
                when (it) {
                    is Response.Error -> showSnackbar("Failed to load task")
                    Response.Loading -> Unit
                    is Response.Result -> commit {
                        copy(
                            taskName = it.data.taskName,
                            taskId = it.data.taskId,
                            taskNote = it.data.taskNote
                        )
                    }
                }
            }
    }

    private fun updateTaskNote() = async {
        with(uiState.value) {
            updateTaskNoteUseCase(
                taskId = getTaskId(),
                taskNote = taskNote
            ).collect {
                when (it) {
                    is Response.Error -> {
                        showSnackbar("Failed to save")
                        commit {
                            copy(
                                isUpdateNote = false,
                                showDialogBackConfirmation = false
                            )
                        }
                    }

                    Response.Loading -> Unit
                    is Response.Result -> {
                        showSnackbar("Success update note!")
                        commit {
                            copy(
                                isUpdateNote = false,
                                showDialogBackConfirmation = false
                            )
                        }
                    }
                }
            }
        }
    }

    override fun handleActions() = onEvent {
        when (it) {
            is SetTaskNote -> commit {
                copy(
                    taskNote = it.note,
                    isUpdateNote = true
                )
            }

            InputNoteEvent.CheckBackPressed -> if (uiState.value.isUpdateNote) {
                commit {
                    copy(
                        showDialogBackConfirmation = true
                    )
                }
            } else {
                navigateUp()
            }

            InputNoteEvent.SubmitNote -> updateTaskNote()
            InputNoteEvent.GetDetailTask -> getDetailTask(getTaskId())
        }
    }

}
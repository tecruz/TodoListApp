package com.tecruz.todolistapp.ui.add_edit_todo

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.tecruz.todolistapp.data.FakeTodoRepository
import com.tecruz.todolistapp.data.Todo
import com.tecruz.todolistapp.util.MainDispatcherRule
import com.tecruz.todolistapp.util.UiEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddEditTodoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: AddEditTodoViewModel
    private lateinit var repository: FakeTodoRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        repository = FakeTodoRepository()
    }

    @Test
    fun `onEvent OnSaveClick saves todo and pops back stack`() = runTest {
        savedStateHandle = SavedStateHandle(mapOf("todoId" to -1))
        viewModel = AddEditTodoViewModel(repository, savedStateHandle)

        viewModel.onEvent(AddEditTodoEvent.OnTitleChange("Test"))

        viewModel.uiEvent.test {
            viewModel.onEvent(AddEditTodoEvent.OnSaveClick)
            assertEquals(UiEvent.PopBackStack, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent OnSaveClick shows snackbar when title is blank`() = runTest {
        savedStateHandle = SavedStateHandle(mapOf("todoId" to -1))
        viewModel = AddEditTodoViewModel(repository, savedStateHandle)

        viewModel.uiEvent.test {
            viewModel.onEvent(AddEditTodoEvent.OnSaveClick)
            val expected = UiEvent.ShowSnackbar(
                message = "Title cannot be empty",
                action = "Ok"
            )
            assertEquals(expected, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init loads todo and sets title and description`() = runTest {
        val todo = Todo(id = 1, title = "Test", description = "Description", isDone = false)
        repository.insertTodo(todo)
        savedStateHandle = SavedStateHandle(mapOf("todoId" to 1))

        viewModel = AddEditTodoViewModel(repository, savedStateHandle)

        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Test", viewModel.title)
        assertEquals("Description", viewModel.description)
    }

    @Test
    fun `onEvent OnTitleChange updates title`() {
        savedStateHandle = SavedStateHandle(mapOf("todoId" to -1))
        viewModel = AddEditTodoViewModel(repository, savedStateHandle)

        viewModel.onEvent(AddEditTodoEvent.OnTitleChange("New Title"))

        assertEquals("New Title", viewModel.title)
    }

    @Test
    fun `onEvent OnDescriptionChange updates description`() {
        savedStateHandle = SavedStateHandle(mapOf("todoId" to -1))
        viewModel = AddEditTodoViewModel(repository, savedStateHandle)

        viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange("New Description"))

        assertEquals("New Description", viewModel.description)
    }
}
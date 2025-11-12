package com.tecruz.todolistapp.ui.todo_list

import app.cash.turbine.test
import com.tecruz.todolistapp.data.FakeTodoRepository
import com.tecruz.todolistapp.data.Todo
import com.tecruz.todolistapp.util.MainDispatcherRule
import com.tecruz.todolistapp.util.Routes
import com.tecruz.todolistapp.util.UiEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TodoListViewModel
    private lateinit var repository: FakeTodoRepository

    @Before
    fun setUp() {
        repository = FakeTodoRepository()
        viewModel = TodoListViewModel(repository)
    }

    @Test
    fun `onEvent OnDeleteTodoClick deletes todo from repository`() = runTest {
        val todo = Todo(id = 1, title = "Test", description = "", isDone = false)
        repository.insertTodo(todo)

        viewModel.onEvent(TodoListEvent.OnDeleteTodoClick(todo))

        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

        assertNull(repository.getTodoById(1))
    }

    @Test
    fun `onEvent OnDeleteTodoClick deletes todo and shows snackbar`() = runTest {
        val todo = Todo(id = 1, title = "Test", description = "", isDone = false)
        repository.insertTodo(todo)

        viewModel.uiEvent.test {
            viewModel.onEvent(TodoListEvent.OnDeleteTodoClick(todo))
            val expected = UiEvent.ShowSnackbar(
                message = "Todo deleted",
                action = "Undo"
            )
            assertEquals(expected, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent OnDoneChange updates todo`() = runTest {
        val todo = Todo(id = 1, title = "Test", description = "", isDone = false)
        repository.insertTodo(todo)
        viewModel.onEvent(TodoListEvent.OnDoneChange(todo, true))
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(true, repository.getTodoById(1)?.isDone)
    }

    @Test
    fun `onEvent OnUndoDeleteClick inserts todo`() = runTest {
        val todo = Todo(id = 1, title = "Test", description = "", isDone = false)
        repository.insertTodo(todo)
        viewModel.onEvent(TodoListEvent.OnDeleteTodoClick(todo))
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(todo, repository.getTodoById(1))
    }

    @Test
    fun `onEvent OnTodoClick navigates to edit screen`() = runTest {
        val todo = Todo(id = 1, title = "Test", description = "", isDone = false)
        repository.insertTodo(todo)

        viewModel.uiEvent.test {
            viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
            val expected = UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${todo.id}")
            assertEquals(expected, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent OnAddTodoClick navigates to add screen`() = runTest {
        viewModel.uiEvent.test {
            viewModel.onEvent(TodoListEvent.OnAddTodoClick)
            val expected = UiEvent.Navigate(Routes.ADD_EDIT_TODO)
            assertEquals(expected, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}

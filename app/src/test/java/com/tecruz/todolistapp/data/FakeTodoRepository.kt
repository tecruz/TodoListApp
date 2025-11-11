package com.tecruz.todolistapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTodoRepository : TodoRepository {

    private val todos = mutableListOf<Todo>()
    private val todosFlow = MutableStateFlow<List<Todo>>(emptyList())

    override fun getTodos(): Flow<List<Todo>> {
        return todosFlow
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return todos.find { it.id == id }
    }

    override suspend fun insertTodo(todo: Todo) {
        val existingIndex = todos.indexOfFirst { it.id == todo.id }
        if (existingIndex != -1) {
            todos[existingIndex] = todo
        } else {
            todos.add(todo)
        }
        todosFlow.value = todos.toList()
    }

    override suspend fun deleteTodo(todo: Todo) {
        todos.remove(todo)
        todosFlow.value = todos.toList()
    }
}
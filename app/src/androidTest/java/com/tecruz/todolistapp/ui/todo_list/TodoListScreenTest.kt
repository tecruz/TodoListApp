package com.tecruz.todolistapp.ui.todo_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.tecruz.todolistapp.MainActivity
import com.tecruz.todolistapp.data.TodoRepository
import com.tecruz.todolistapp.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class TodoListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: TodoRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun fab_navigatesToAddTodoScreen() {
        composeRule.onNodeWithContentDescription("Add Todo").performClick()
        composeRule.onNodeWithText("Title").assertIsDisplayed()
    }
}
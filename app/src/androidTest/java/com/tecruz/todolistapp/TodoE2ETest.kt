package com.tecruz.todolistapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.tecruz.todolistapp.data.TodoDatabase
import com.tecruz.todolistapp.di.TestAppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(TestAppModule::class)
class TodoE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var database: TodoDatabase

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        runBlocking {
            database.clearAllTables()
        }
    }

    @Test
    fun createTodo_displaysOnList() {
        // Navigate to AddEditTodoScreen
        composeRule.onNodeWithContentDescription("Add Todo").performClick()

        // Enter todo details
        composeRule.onNodeWithText("Title").performTextInput("New Todo")
        composeRule.onNodeWithText("Description").performTextInput("New Description")

        // Save the todo
        composeRule.onNodeWithContentDescription("Save Todo").performClick()

        // Verify the new todo is displayed on the list screen
        composeRule.onNodeWithText("New Todo").assertIsDisplayed()
    }
}

package com.tecruz.todolistapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.tecruz.todolistapp.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class TodoE2ETest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

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

    @Test
    fun clickTodo_opensDetailScreen() {
        // 1. Create a new Todo to ensure the list is not empty
        composeRule.onNodeWithContentDescription("Add Todo").performClick()
        composeRule.onNodeWithText("Title").performTextInput("Click Me")
        composeRule.onNodeWithText("Description").performTextInput("Click Description")
        composeRule.onNodeWithContentDescription("Save Todo").performClick()

        // Make sure the UI has settled after navigating back from the save action
        composeRule.waitForIdle()

        // 2. Click on the newly created Todo item
        composeRule.onNodeWithText("Click Me").performClick()

        // 3. Wait for the UI to be idle before making assertions
        composeRule.waitForIdle()

        // 4. Verify that the detail screen is shown with the correct data
        composeRule.onNodeWithText("Click Me").assertIsDisplayed()
        composeRule.onNodeWithText("Click Description").assertIsDisplayed()
    }
}

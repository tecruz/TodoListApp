package com.tecruz.todolistapp.ui.add_edit_todo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
class AddEditTodoScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var repository: TodoRepository

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.onNodeWithContentDescription("Add Todo").performClick()
    }

    @Test
    fun titleTextField_updatesOnValueChange() {
        composeRule.onNodeWithText("Title").performTextInput("new title")
        composeRule.onNodeWithText("new title").assertIsDisplayed()
    }

    @Test
    fun descriptionTextField_updatesOnValueChange() {
        composeRule.onNodeWithText("Description").performTextInput("new description")
        composeRule.onNodeWithText("new description").assertIsDisplayed()
    }

    @Test
    fun saveFab_savesTodo() {
        composeRule.onNodeWithText("Title").performTextInput("new title")
        composeRule.onNodeWithContentDescription("Save Todo").performClick()
        composeRule.onNodeWithText("new title").assertIsDisplayed()
    }
}
package com.example.ui.home

import com.example.data.repository.RickMortyRepository
import com.example.testing_utils.MainCoroutineRule
import com.example.testing_utils.runBlocking
import io.mockk.MockKAnnotations
import com.example.model.Character
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RickMortyViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: RickMortyRepository

    private lateinit var viewModel: RickMortyViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = RickMortyViewModel(repository)
    }

    @Test
    fun `get character succeeded`() = coroutineRule.runBlocking {

    }

    @Test
    fun `get character failed`() = coroutineRule.runBlocking {

    }

    @Test
    fun `get characters succeeded`() = coroutineRule.runBlocking {

    }

    @Test
    fun `get characters failed`() = coroutineRule.runBlocking {

    }

    @Test
    fun `get favorite characters succeeded`() = coroutineRule.runBlocking {

    }

    @Test
    fun `get favorite characters failed`() = coroutineRule.runBlocking {

    }

    @Test
    fun `get isFavorite succeeded`() = coroutineRule.runBlocking {

    }

    @Test
    fun `get isFavorite failed`() = coroutineRule.runBlocking {

    }

    @Test
    fun `save favorite succeeded`() = coroutineRule.runBlocking {

    }

    @Test
    fun `delete favorite succeeded`() = coroutineRule.runBlocking {

    }

    private fun createCharacter() = Character(
        id = 1,
        created = "created",
        episode = emptyList(),
        gender = "Male",
        image = "image",
        name = "name",
        species = "species",
        status = "status",
        type = "type",
        url = "url",
    )
}
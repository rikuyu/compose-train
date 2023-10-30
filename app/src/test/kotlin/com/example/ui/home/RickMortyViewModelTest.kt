package com.example.ui.home

import app.cash.turbine.test
import com.example.data.repository.RickMortyRepository
import com.example.model.Character
import com.example.testing_utils.MainCoroutineRule
import com.example.testing_utils.runBlocking
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RickMortyViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: RickMortyRepository

    private lateinit var viewModel: RickMortyViewModel
    private lateinit var mockCharacter: Character

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = RickMortyViewModel(repository)
        mockCharacter = mockk()
    }

    @Test
    fun `get characters succeeded`() = coroutineRule.runBlocking {
        coEvery { repository.getCharacters() } returns listOf(mockCharacter)

        viewModel.characters.test {
            with(awaitItem()) {
                assertThat(characters).isEmpty()
                assertThat(isLoading).isFalse()
                assertThat(error).isNull()
            }

            viewModel.getCharacters()

            with(awaitItem()) {
                assertThat(characters).hasSize(1)
                assertThat(isLoading).isFalse()
                assertThat(error).isNull()
            }
        }

        coVerify(exactly = 1) {
            repository.getCharacters()
        }
    }

    @Test
    fun `get characters failed`() = coroutineRule.runBlocking {
        coEvery { repository.getCharacters() } throws Throwable()

        viewModel.characters.test {
            with(awaitItem()) {
                assertThat(characters).isEmpty()
                assertThat(isLoading).isFalse()
                assertThat(error).isNull()
            }

            viewModel.getCharacters()

            with(awaitItem()) {
                assertThat(characters).isEmpty()
                assertThat(isLoading).isFalse()
                assertThat(error).isNotNull()
            }
        }

        coVerify(exactly = 1) {
            repository.getCharacters()
        }
    }

    @Test
    fun `get favorite characters succeeded`() = coroutineRule.runBlocking {
        coEvery { repository.getFavoriteCharacters() } returns listOf(mockCharacter)

        viewModel.favoriteCharacters.test {
            with(awaitItem()) {
                assertThat(characters).isEmpty()
                assertThat(isLoading).isFalse()
                assertThat(error).isNull()
            }

            viewModel.getFavoriteCharacters()

            with(awaitItem()) {
                assertThat(characters).hasSize(1)
                assertThat(isLoading).isFalse()
                assertThat(error).isNull()
            }
        }

        coVerify(exactly = 1) {
            repository.getFavoriteCharacters()
        }
    }

    @Test
    fun `get favorite characters failed`() = coroutineRule.runBlocking {
        coEvery { repository.getFavoriteCharacters() } throws Throwable()

        viewModel.favoriteCharacters.test {
            with(awaitItem()) {
                assertThat(characters).isEmpty()
                assertThat(isLoading).isFalse()
                assertThat(error).isNull()
            }

            viewModel.getFavoriteCharacters()

            with(awaitItem()) {
                assertThat(characters).isEmpty()
                assertThat(isLoading).isFalse()
                assertThat(error).isNotNull()
            }
        }

        coVerify(exactly = 1) {
            repository.getFavoriteCharacters()
        }
    }
}

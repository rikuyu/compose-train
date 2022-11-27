package com.example.ui.home

import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.repository.RickMortyRepository
import com.example.ui.utils.LoadingState
import com.google.common.truth.Truth.assertThat
import com.example.model.Character
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RickMortyViewModelTest {

    @MockK
    private lateinit var repository: RickMortyRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // Dispatchers.setMain(StandardTestDispatcher())
    }

//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }

    @Test
    fun getCharacters() = runTest {
        val viewModel = RickMortyViewModel(repository)

        val firstValue = viewModel.characterListState.value
        val dummyData = listOf(
            mockk<Character>(relaxed = true) {
                every { name } returns "john"
            }
        )
        val response = flowOf(dummyData)
        coEvery { repository.getCharacterList() } returns response

        assertThat(firstValue.data).isNull()
        assertThat(firstValue.error).isNull()
        assertThat(firstValue.isLoading.name).isEqualTo(LoadingState.NOT_LOADING.name)

        viewModel.getCharacters()

        assertThat(firstValue.data).isEqualTo(dummyData)
        assertThat(firstValue.error).isNull()
        assertThat(firstValue.isLoading.name).isEqualTo(LoadingState.NOT_LOADING.name)

        coVerify(exactly = 1) {
            repository.getCharacterList()
            viewModel.getCharacters()
        }
    }
}
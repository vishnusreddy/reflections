package com.faanghut.reflection.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.models.PageDateWithPages
import com.faanghut.reflection.repository.PageRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PageRepository) : ViewModel() {

    val allPageWithDates: LiveData<List<PageDateWithPages>> = repository.allPagesGroupedByDate.asLiveData()

    fun insert(note: Page) = viewModelScope.launch {
        repository.insert(note)
    }
}

class HomeViewModelFactory(private val repository: PageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

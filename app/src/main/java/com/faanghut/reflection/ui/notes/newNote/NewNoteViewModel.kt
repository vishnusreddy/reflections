package com.faanghut.reflection.ui.notes.newNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.faanghut.reflection.core.SingleLiveEvent
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.repository.PageRepository
import kotlinx.coroutines.launch

class NewNoteViewModel(private val repository: PageRepository) : ViewModel() {

    val pageInsertedToDB = SingleLiveEvent<Boolean>()
    fun insert(page: Page) = viewModelScope.launch {
        repository.insert(page)
        pageInsertedToDB.postValue(true)
    }
}

class NewNoteViewModelFactory(private val repository: PageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return NewNoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
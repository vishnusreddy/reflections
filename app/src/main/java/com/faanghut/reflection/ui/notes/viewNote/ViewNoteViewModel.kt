package com.faanghut.reflection.ui.notes.viewNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.faanghut.reflection.core.SingleLiveEvent
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.repository.PageRepository
import kotlinx.coroutines.launch

class ViewNoteViewModel(private val repository: PageRepository) : ViewModel() {

    val pageDeletedFromDb = SingleLiveEvent<Boolean>()
    fun delete(page: Page) = viewModelScope.launch {
        repository.delete(page)
        pageDeletedFromDb.postValue(true)
    }
}

class ViewNoteViewModelFactory(private val repository: PageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ViewNoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
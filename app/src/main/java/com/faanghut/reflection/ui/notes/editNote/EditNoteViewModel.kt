package com.faanghut.reflection.ui.notes.editNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.faanghut.reflection.core.SingleLiveEvent
import com.faanghut.reflection.models.Page
import com.faanghut.reflection.repository.PageRepository
import kotlinx.coroutines.launch

class EditNoteViewModel(private val repository: PageRepository) : ViewModel() {

    val pageUpdatedInDb = SingleLiveEvent<Boolean>()
    val pageDeletedFromDb = SingleLiveEvent<Boolean>()

    fun delete(page: Page) = viewModelScope.launch {
        repository.delete(page)
        pageDeletedFromDb.postValue(true)
    }

    fun update(page: Page) = viewModelScope.launch {
        repository.insert(page)
        pageUpdatedInDb.postValue(true)
    }
}

class EditNoteViewModelFactory(private val repository: PageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return EditNoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
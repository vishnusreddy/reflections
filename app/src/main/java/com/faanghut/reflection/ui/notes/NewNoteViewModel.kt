package com.faanghut.reflection.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.faanghut.reflection.core.SingleLiveEvent
import com.faanghut.reflection.models.Note
import com.faanghut.reflection.repository.NoteRepository
import kotlinx.coroutines.launch

class NewNoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val noteInsertedToDb = SingleLiveEvent<Boolean>()
    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
        noteInsertedToDb.postValue(true)
    }
}

class NewNoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return NewNoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
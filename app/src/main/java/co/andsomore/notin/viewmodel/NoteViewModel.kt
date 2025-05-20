package co.andsomore.notin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.andsomore.notin.data.Note
import co.andsomore.notin.data.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.getNotes()
        .catch { e -> emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

//    suspend fun getNotes() = viewModelScope.launch {
//        repository.getNotes()
//            .catch {
//                e -> emit(emptyList())
//            }.stateIn(
//                viewModelScope,
//                SharingStarted.WhileSubscribed(5000),
//                emptyList()
//            )
//    }

    fun addNote(title: String, content: String) = viewModelScope.launch {
        repository.addNote(Note(title = title, content = content))
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(id: String) = viewModelScope.launch {
        repository.deleteNote(id)
    }

    suspend fun getNoteById(id: String): Note? {
        return repository.getNoteById(id)
    }
}
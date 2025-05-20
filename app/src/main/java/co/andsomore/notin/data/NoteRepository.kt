package co.andsomore.notin.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {
     fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: String): Note?
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: String)
}

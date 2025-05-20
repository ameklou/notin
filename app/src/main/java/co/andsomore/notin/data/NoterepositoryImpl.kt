package co.andsomore.notin.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : NoteRepository {
    private val notesCollection = firestore.collection("notes")

    override suspend fun getNotes(): Flow<List<Note>> = callbackFlow {
        val snapshotListener = notesCollection
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val notes = snapshot.toObjects(Note::class.java)
                        .map { note -> note.copy(id = snapshot.documents.find { it.toObject(Note::class.java) == note }?.id ?: "") }
                    trySend(notes)
                }
            }

        awaitClose { snapshotListener.remove() }
    }

    override suspend fun getNoteById(id: String): Note? {
        return try {
            val document = notesCollection.document(id).get().await()
            document.toObject(Note::class.java)?.copy(id = document.id)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun addNote(note: Note) {
        notesCollection.add(note).await()
    }

    override suspend fun updateNote(note: Note) {
        if (note.id.isBlank()) return
        notesCollection.document(note.id).set(note).await()
    }

    override suspend fun deleteNote(id: String) {
        notesCollection.document(id).delete().await()
    }
}
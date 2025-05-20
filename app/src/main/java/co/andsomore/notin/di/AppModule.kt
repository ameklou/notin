package co.andsomore.notin.di

import co.andsomore.notin.data.NoteRepository
import co.andsomore.notin.data.NoteRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(firestore: FirebaseFirestore): NoteRepository {
        return NoteRepositoryImpl(firestore)
    }
}
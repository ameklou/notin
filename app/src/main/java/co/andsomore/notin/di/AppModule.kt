package co.andsomore.notin.di

import co.andsomore.notin.data.NoteRepository
import co.andsomore.notin.data.NoteRepositoryImpl
import co.andsomore.notin.viewmodel.NoteViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    viewModel { NoteViewModel(get()) }
}
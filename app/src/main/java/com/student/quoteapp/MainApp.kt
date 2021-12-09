package com.student.quoteapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.student.quoteapp.data.QuoteDatabase
import com.student.quoteapp.data.QuoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.Executors

class MainApp: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { QuoteDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { QuoteRepository(database.quoteDao()) }

}



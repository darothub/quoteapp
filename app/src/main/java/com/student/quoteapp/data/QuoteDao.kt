package com.student.quoteapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.student.quoteapp.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote ORDER BY uid DESC")
    fun getAll(): Flow<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: Quote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(quotes: List<Quote>)

}



package com.student.quoteapp.data

import androidx.annotation.WorkerThread
import com.student.quoteapp.Quote

class QuoteRepository(var quoteDao: QuoteDao?) {

    fun getQuotes() = quoteDao?.getAll()
    suspend fun insertQuote(quote : Quote) = quoteDao?.insert(quote)
    suspend fun insertAll(quote : List<Quote>) = quoteDao?.insertAll(quote)
}
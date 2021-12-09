package com.student.quoteapp

import androidx.lifecycle.*
import com.student.quoteapp.data.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel(private var quoteRepository: QuoteRepository):ViewModel() {

    private var _quotes = MutableLiveData<List<Quote>>()
    val quotes get() = _quotes



    val allQuotes = quoteRepository.getQuotes()?.asLiveData()
    suspend fun insertAll(quotes: List<Quote>) = quoteRepository.insertAll(quotes.toList())
    fun insertQuote(quote: Quote) = viewModelScope.launch {
        quoteRepository.insertQuote(quote)
    }
}
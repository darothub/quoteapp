package com.student.quoteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.student.quoteapp.data.QuoteRepository


class QuoteViewModelFactory(private val dataRepository: QuoteRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuoteViewModel(dataRepository) as T
    }
}
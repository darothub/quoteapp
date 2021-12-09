package com.student.quoteapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    var text:String,
    var liked:Boolean=false
)
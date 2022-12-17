package com.wwe.correct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wwe.util.Event
import com.wwe.util.SlowRandomNumberGenerator

class CorrectViewModel : ViewModel() {
    private val newNumberEvent = MutableLiveData<Event<Any>>()
    private val numberGenerator = SlowRandomNumberGenerator()

    val correctRandomNumber: LiveData<Int> = Transformations.switchMap(newNumberEvent) {
        numberGenerator.getNumber()
    }

    /**
     * Notifies the event LiveData of a new request for a random number.
     */
    fun triggerEvent() {
        newNumberEvent.value = Event(Unit)
    }
}
package com.tasteguys.foorrng_customer.presentation.truck.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasteguys.foorrng_customer.domain.usecase.truck.RegisterReviewUseCase
import com.tasteguys.foorrng_customer.presentation.model.mapper.ReviewMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TruckReviewViewModel @Inject constructor(
    private val reviewUseCase: RegisterReviewUseCase
): ViewModel() {

    private val _selectedReview = mutableSetOf<String>()
    val selectedReview: MutableSet<String>
        get() = _selectedReview


    private val _registerReviewResult = MutableLiveData<Result<Long>>()
    val registerReviewResult: LiveData<Result<Long>>
        get() = _registerReviewResult

    fun registerReview(truckId: Long){
        viewModelScope.launch {
            reviewUseCase(
                truckId,
                rvIsDelicious = _selectedReview.contains("rvIsDelicious"),
                rvIsCheap = _selectedReview.contains("rvIsCheap"),
                rvIsClean = _selectedReview.contains("rvIsClean"),
                rvIsCool = _selectedReview.contains("rvIsCool"),
                rvIsFast = _selectedReview.contains("rvIsFast"),
                rvIsKind = _selectedReview.contains("rvIsKind"),
                rvIsSpecial = _selectedReview.contains("rvIsSpecial")
            ).let{
                _registerReviewResult.postValue(it)
            }
        }
    }



}
package org.rsschool.cats.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import org.rsschool.cats.model.Image
import org.rsschool.cats.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: Repository) : ViewModel() {

    val imagesFlow: Flow<PagingData<Image>> =
        repository.fetchImagesFlow().cachedIn(viewModelScope)
}

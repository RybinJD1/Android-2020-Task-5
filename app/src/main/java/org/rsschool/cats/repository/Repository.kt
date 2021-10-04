package org.rsschool.cats.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.rsschool.cats.model.Image
import org.rsschool.cats.paging.ImageRemotePagingSource

interface Repository {

    fun fetchImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()):
        Flow<PagingData<Image>>

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = ImageRemotePagingSource.DEFAULT_PAGE_SIZE,
            enablePlaceholders = true
        )
    }
}

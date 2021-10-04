package org.rsschool.cats.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.rsschool.cats.model.Image
import org.rsschool.cats.network.CatApi
import org.rsschool.cats.paging.ImageRemotePagingSource
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val catApi: CatApi) : Repository {

    override fun fetchImagesFlow(pagingConfig: PagingConfig): Flow<PagingData<Image>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ImageRemotePagingSource(catApi) }
        ).flow
    }
}

package org.rsschool.cats.network

import androidx.annotation.IntRange
import org.rsschool.cats.model.Image
import org.rsschool.cats.paging.ImageRemotePagingSource.Companion.DEFAULT_PAGE_SIZE
import org.rsschool.cats.paging.ImageRemotePagingSource.Companion.INITIAL_PAGE_NUMBER
import org.rsschool.cats.paging.ImageRemotePagingSource.Companion.MAX_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {

    @GET("images/search?api_key=d2b055c4-e9d9-4993-bc78-fdbb60111f1d")
    suspend fun getImages(
        @Query("limit")
        @IntRange(from = 1, to = MAX_PAGE_SIZE.toLong())
        limit: Int = DEFAULT_PAGE_SIZE,
        @Query("page")
        @IntRange(from = 1)
        page: Int = INITIAL_PAGE_NUMBER,
    ): Response<List<Image>>
}

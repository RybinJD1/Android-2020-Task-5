package org.rsschool.cats.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.rsschool.cats.model.Image
import org.rsschool.cats.network.CatApi
import retrofit2.HttpException

class ImageRemotePagingSource(private val catApi: CatApi) :
    PagingSource<Int, Image>() {

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        try {
            val page = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)
            val response = catApi.getImages(limit = pageSize, page = page)
            return if (response.isSuccessful) {
                val images = response.body().orEmpty()
                val prevPageNumber = if (page > 1) page - 1 else null
                val nextPageNumber = if (images.isEmpty()) null else page + 1
                LoadResult.Page(
                    data = images,
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val MAX_PAGE_SIZE = 20
        const val INITIAL_PAGE_NUMBER = 1
    }
}

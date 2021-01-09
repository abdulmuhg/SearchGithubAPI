package secretymus.id.cermaticodingtest.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import secretymus.id.cermaticodingtest.model.QuerySearchResult

interface ApiInterface {
    @GET("users")
    fun getSearchResult(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = PAGE_SIZE
    ): Single<QuerySearchResult>

    companion object{
        const val PAGE_SIZE = 12
    }
}
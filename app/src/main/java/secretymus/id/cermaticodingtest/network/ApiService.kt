package secretymus.id.cermaticodingtest.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import secretymus.id.cermaticodingtest.QuerySearchResult

class ApiService {
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApiInterface::class.java)

    fun getResult(query: String): Single<QuerySearchResult> {
        return api.getSearchResult(query)
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/search/"
    }
}
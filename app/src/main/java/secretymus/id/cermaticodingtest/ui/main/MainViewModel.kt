package secretymus.id.cermaticodingtest.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_fragment.*
import secretymus.id.cermaticodingtest.QuerySearchResult
import secretymus.id.cermaticodingtest.User
import secretymus.id.cermaticodingtest.network.ApiService
import secretymus.id.cermaticodingtest.ui.main.MainFragment.Companion.CODE_FIRST_LOAD
import secretymus.id.cermaticodingtest.ui.main.MainFragment.Companion.CODE_LOAD_MORE

class MainViewModel : ViewModel() {
    private val apiService = ApiService()
    private val disposable = CompositeDisposable()
    val users = MutableLiveData<List<User>>()
    val isLoadError = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun fetchFromRemote(code: Int, query: String, page: Int) {
        isLoading.value = code != CODE_LOAD_MORE
        disposable.add(
            apiService.getResult(query, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<QuerySearchResult>() {
                    override fun onSuccess(searchResult: QuerySearchResult) {
                        users.value = searchResult.items
                        isLoadError.value = false
                        isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        isLoadError.value = true
                        isLoading.value = false
                        e.printStackTrace()
                        Log.e("API", e.message.toString())
                    }
                })
        )
    }

}
package daniellopes.io.app_mobile.ui.fragment.listarticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import daniellopes.io.app_mobile.data.entities.Articles
import daniellopes.io.app_mobile.data.remote.response.Resource
import daniellopes.io.app_mobile.repository.ArticleRepositoryImpl
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListArticleViewModel @Inject constructor(
    private val repositoryImpl: ArticleRepositoryImpl
) : ViewModel() {

    private val _articles: MutableLiveData<Resource<List<Articles>>> = MutableLiveData()
    val articles: LiveData<Resource<List<Articles>>> get() = _articles

    init {
        getAllArticles()
    }

    private fun getAllArticles() = viewModelScope.launch {
        safeGetAllArticles()
    }

    private suspend fun safeGetAllArticles() {
        _articles.postValue(Resource.Loading())
        try {
            val response = repositoryImpl.getAllArticles()
            _articles.postValue(handleGetAllArticles(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _articles.postValue(
                    Resource.Error(
                        "Erro de conexão com a Internet",
                        null
                    )
                )
                else -> _articles.postValue(Resource.Error("Falha na conversão dos dados", null))
            }
        }
    }

    private fun handleGetAllArticles(response: Response<List<Articles>>): Resource<List<Articles>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message(), null)
    }
}
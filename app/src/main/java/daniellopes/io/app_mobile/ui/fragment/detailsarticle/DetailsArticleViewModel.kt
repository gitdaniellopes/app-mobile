package daniellopes.io.app_mobile.ui.fragment.detailsarticle

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
class DetailsArticleViewModel @Inject constructor(
    private val repositoryImpl: ArticleRepositoryImpl
) : ViewModel() {

    private val _articleId: MutableLiveData<Resource<Articles>> = MutableLiveData()
    val articleId: LiveData<Resource<Articles>> get() = _articleId

    fun findById(id: String) = viewModelScope.launch {
        safeGetArticleById(id)
    }

    private suspend fun safeGetArticleById(id: String) {
        _articleId.postValue(Resource.Loading())
        try {
            val response = repositoryImpl.getArticleById(id)
            _articleId.postValue(handleGetArticleById(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _articleId.postValue(
                    Resource.Error(
                        "Falha na conexão com a internet",
                        null
                    )
                )
                else -> _articleId.postValue(Resource.Error("Erro na conversão de dados", null))
            }
        }
    }

    private fun handleGetArticleById(response: Response<Articles>): Resource<Articles>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}
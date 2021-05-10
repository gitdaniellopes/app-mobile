package daniellopes.io.app_mobile.repository

import daniellopes.io.app_mobile.data.entities.Articles
import daniellopes.io.app_mobile.data.remote.ArticleApi
import retrofit2.Response
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: ArticleApi
) : ArticleRepository {
    override suspend fun getAllArticles(): Response<List<Articles>> = api.getAllArticles()

    override suspend fun getArticleById(id: String): Response<Articles> = api.getArticleById(id)
}
package daniellopes.io.app_mobile.repository

import daniellopes.io.app_mobile.data.entities.Articles
import retrofit2.Response

interface ArticleRepository {

    suspend fun getAllArticles(): Response<List<Articles>>
    suspend fun getArticleById(id: String): Response<Articles>
}
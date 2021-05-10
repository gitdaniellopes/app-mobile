package daniellopes.io.app_mobile.data.remote

import daniellopes.io.app_mobile.data.entities.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApi {

    @GET("articles")
    suspend fun getAllArticles(): Response<List<Articles>>

    @GET("articles/{id}")
    suspend fun getArticleById(
        @Path("id")
        id: String
    ): Response<Articles>
}
package daniellopes.io.app_mobile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daniellopes.io.app_mobile.data.remote.ArticleApi
import daniellopes.io.app_mobile.other.Constants.BASE_URL
import daniellopes.io.app_mobile.repository.ArticleRepository
import daniellopes.io.app_mobile.repository.ArticleRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideArticleRepositoryImpl(
        api: ArticleApi
    ) = ArticleRepositoryImpl(api) as ArticleRepository

    @Singleton
    @Provides
    fun provideArticleApi(): ArticleApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ArticleApi::class.java)
    }
}
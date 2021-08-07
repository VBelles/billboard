package io.github.vbelles.billboard.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.vbelles.billboard.BuildConfig
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import io.github.vbelles.billboard.data.repository.section.SectionRepository
import io.github.vbelles.billboard.ui.screens.main.MainViewModel
import io.github.vbelles.billboard.ui.screens.page.PageViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create


val appModule = module {
    single<OkHttpClient> { buildHttpClient() }
    single<SectionRepository> {
        SectionRepository(
            buildApiClient(BuildConfig.SECTIONS_API_URL, get()),
            BuildConfig.SECTIONS_API_ENDPOINT
        )
    }
    single<ContentRepository> {
        ContentRepository(
            buildApiClient(BuildConfig.TMDB_API_URL, get()),
            BuildConfig.TMDB_API_KEY,
            BuildConfig.TMDB_IMAGE_URL
        )
    }
    viewModel { MainViewModel(get()) }
    viewModel { PageViewModel(get()) }
}

private fun buildHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()
}

private val json = Json {
    encodeDefaults = false
    ignoreUnknownKeys = true
}

private inline fun <reified T> buildApiClient(baseUrl: String, httpClient: OkHttpClient): T {
    return Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create()
}
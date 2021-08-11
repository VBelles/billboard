package io.github.vbelles.billboard.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.github.vbelles.billboard.R
import io.github.vbelles.billboard.data.repository.content.ContentRepository
import io.github.vbelles.billboard.data.repository.section.SectionRepository
import io.github.vbelles.billboard.ui.screens.details.DetailsViewModel
import io.github.vbelles.billboard.ui.screens.grid.GridViewModel
import io.github.vbelles.billboard.ui.screens.main.MainViewModel
import io.github.vbelles.billboard.ui.screens.page.PageViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create


val appModule = module {
    single<OkHttpClient> { buildHttpClient() }
    single<SectionRepository> {
        SectionRepository(buildApiClient(androidContext().getString(R.string.sections_api_url), get()))
    }
    single<ContentRepository> {
        ContentRepository(
            buildApiClient(androidContext().getString(R.string.tmdb_api_url), get()),
            androidContext().getString(R.string.tmdb_api_key),
            androidContext().getString(R.string.tmdb_image_url)
        )
    }
    viewModel { MainViewModel(get()) }
    viewModel { parameters -> PageViewModel(parameters[0], parameters[1], get(), get()) }
    viewModel { parameters -> GridViewModel(get(), parameters[0], parameters[1]) }
    viewModel { parameters -> DetailsViewModel(get(), parameters[0], parameters[1]) }
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

@OptIn(ExperimentalSerializationApi::class)
private inline fun <reified T> buildApiClient(baseUrl: String, httpClient: OkHttpClient): T {
    return Retrofit.Builder()
        .client(httpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create()
}
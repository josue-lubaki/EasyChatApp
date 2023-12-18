package com.alithya.common.di

import com.alithya.common.BuildConfig
import com.alithya.common.data.remote.api.AuthenticationService
import com.alithya.common.domain.repository.DataStoreOperations
import com.alithya.common.network.AuthInterceptor
import com.alithya.common.network.HttpClientFactory
import com.alithya.common.utils.Constants.NETWORK_TIME_OUT
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
        single<HttpClientFactory> { HttpClientFactory() }
        single<OkHttpClient.Builder> { provideOkHttpClientBuilder(httpClientFactory = get()) }
        single<Interceptor> { provideInterceptor(dataStoreOperations = get()) }
        single<OkHttpClient> {
            provideOkHttpClient(
                clientBuilder = get(),
                authInterceptor = get()
            )
        }
        single<Retrofit> { provideRetrofit(okHttpClient = get()) }
        single<AuthenticationService> { provideAuthenticationService(retrofit = get()) }
        factory<CoroutineDispatcher> { provideDispatchers() }
}

internal fun provideDispatchers() : CoroutineDispatcher = Dispatchers.IO

internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    val gson = GsonBuilder().setLenient().create()

    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

internal fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService {
    return retrofit.create(AuthenticationService::class.java)
}

internal fun provideOkHttpClientBuilder(
    httpClientFactory: HttpClientFactory
): OkHttpClient.Builder {
    return httpClientFactory.create()
}

internal fun provideInterceptor(dataStoreOperations: DataStoreOperations): Interceptor {
    return AuthInterceptor(dataStoreOperations)
}

internal fun provideOkHttpClient(
    clientBuilder : OkHttpClient.Builder,
    authInterceptor: Interceptor
): OkHttpClient {

    clientBuilder
        .addInterceptor(authInterceptor)
        .connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
    }

    return clientBuilder.build()
}
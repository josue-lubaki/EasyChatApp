package com.alithya.common.di

import com.alithya.common.data.repository.repositoryimpl.UserRepositoryImpl
import com.alithya.common.domain.usecases.get_me_information.GetMeUseCase
import com.alithya.common.data.datasource.UserRemoteDataSource
import com.alithya.common.data.datasource.datasourceimpl.UserRemoteDataSourceImpl
import com.alithya.common.data.remote.api.AuthenticationService
import com.alithya.common.data.remote.api.UserService
import com.alithya.common.data.repository.DataStoreRepository
import com.alithya.common.domain.repository.DataStoreOperations
import com.alithya.common.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.dsl.module
import retrofit2.Retrofit

val userModule = module {

        factory {
            provideUserService(retrofit = get())
        }

        factory<UserRemoteDataSource> {
            provideUserRemoteDataSource(
                userService = get(),
                authenticationService = get(),
                dispatcher = get()
            )
        }

        factory<UserRepository> {
            provideUserRepository(remoteDataSource = get())
        }

        factory {
            provideGetMeUseCase(repository = get())
        }
}

internal fun provideUserService(retrofit : Retrofit): UserService = retrofit.create(UserService::class.java)
internal fun provideUserRemoteDataSource(
    userService: UserService,
    authenticationService: AuthenticationService,
    dispatcher: CoroutineDispatcher
): UserRemoteDataSource {
    return UserRemoteDataSourceImpl(
        service = userService,
        authenticationService = authenticationService,
        dispatchers = dispatcher
    )
}

internal fun provideUserRepository(remoteDataSource: UserRemoteDataSource): UserRepository {
    return UserRepositoryImpl(remoteDataSource = remoteDataSource)
}

internal fun provideRepository(dataStore: DataStoreOperations): DataStoreRepository {
    return DataStoreRepository(dataStore)
}

internal fun provideGetMeUseCase(repository: UserRepository): GetMeUseCase {
    return GetMeUseCase(repository)
}
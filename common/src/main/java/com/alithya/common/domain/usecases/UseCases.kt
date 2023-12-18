package com.alithya.common.domain.usecases

import com.alithya.common.domain.usecases.read_current_user_id.ReadCurrentUserIdUseCase
import com.alithya.common.domain.usecases.read_preferred_language.ReadPreferredLanguageUseCase
import com.alithya.common.domain.usecases.read_token.ReadAccessTokenUseCase
import com.alithya.common.domain.usecases.save_current_user_id.SaveCurrentUserIdUseCase
import com.alithya.common.domain.usecases.save_preferred_language.SavePreferredLanguageUseCase
import com.alithya.common.domain.usecases.save_token.SaveAccessTokenUseCase

data class UseCasesCommon (
    val savePreferredLanguageUseCase : SavePreferredLanguageUseCase,
    val readPreferredLanguageUseCase : ReadPreferredLanguageUseCase,
    val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    val readAccessTokenUseCase: ReadAccessTokenUseCase,
    val readCurrentUserIdUseCase: ReadCurrentUserIdUseCase,
    val saveCurrentUserIdUseCase: SaveCurrentUserIdUseCase
)
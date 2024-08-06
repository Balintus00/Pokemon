package io.github.balintus00.pokemon.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
class ProvisionModule {

    @Provides
    @BaseUrl
    @Suppress("FunctionOnlyReturningConstant")
    internal fun provideBaseUrl(): String = "https://the-one-api.dev/v2"

    @Provides
    internal fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> = OkHttp

    @Provides
    internal fun provideHttpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            logger = object : Logger {

                val KTOR_LOG_TAG = "HTTP Client"

                override fun log(message: String) {
                    Log.i(KTOR_LOG_TAG, message)
                }
            }

            level = LogLevel.ALL
        }
    }

// TODO
/*    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME,
    ).build()

    @Provides
    fun provideCharacterDao(database: AppDatabase): CharacterDao = database.characterDao()*/
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl
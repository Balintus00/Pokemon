package io.github.balintus00.pokemon.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.balintus00.pokemon.data.disk.AppDatabase
import io.github.balintus00.pokemon.data.disk.CaughtPokemonDao
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
class PokemonProvisionModule {

    @Provides
    @BaseUrl
    @Suppress("FunctionOnlyReturningConstant")
    fun provideBaseUrl(): String = "https://pokeapi.co/api/v2"

    @Provides
    fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*> = OkHttp

    @Provides
    fun provideHttpClient(): HttpClient = HttpClient {
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

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME,
    ).build()

    @Provides
    fun provideCaughtPokemonDao(database: AppDatabase): CaughtPokemonDao =
        database.caughtPokemonDao()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl
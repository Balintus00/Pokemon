package io.github.balintus00.pokemon.data.disk

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CaughtPokemon::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun caughtPokemonDao(): CaughtPokemonDao

    companion object {
        const val DATABASE_NAME = "pokemon-app-db"
    }
}
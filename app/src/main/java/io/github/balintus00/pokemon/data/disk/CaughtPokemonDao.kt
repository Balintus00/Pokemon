package io.github.balintus00.pokemon.data.disk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CaughtPokemonDao {

    @Query("SELECT * FROM caughtpokemon")
    fun getCaughtPokemons(): Flow<List<CaughtPokemon>>

    @Query("SELECT * FROM caughtpokemon WHERE name = :caughtPokemonName")
    fun getCaughtPokemonByName(caughtPokemonName: String): Flow<CaughtPokemon?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCaughtPokemon(caughtPokemon: CaughtPokemon)

    @Query("DELETE FROM caughtpokemon WHERE name = :pokemonName")
    suspend fun deleteCaughtPokemon(pokemonName: String)
}
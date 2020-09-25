package com.lucasrodrigues.pokemonshowcase.data_access.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucasrodrigues.pokemonshowcase.data_access.local.dao.*
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.*
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonAbilityCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonMoveCrossRef
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.relation.PokemonTypeCrossRef
import com.lucasrodrigues.pokemonshowcase.utils.DatabaseConverters

@Database(
    entities = [
        Pokemon::class,
        Type::class,
        Ability::class,
        Move::class,
        PokemonAbilityCrossRef::class,
        PokemonMoveCrossRef::class,
        PokemonTypeCrossRef::class,
        RemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java, "database.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeyDao(): RemoteKeysDao
    abstract fun abilityDao(): AbilityDao
    abstract fun moveDao(): MoveDao
    abstract fun typeDao(): TypeDao
}
package com.lucasrodrigues.pokemonshowcase.extensions

import com.lucasrodrigues.pokemonshowcase.constants.Generation
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Ability
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Move
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Pokemon
import com.lucasrodrigues.pokemonshowcase.data_access.local.entity.Type
import com.lucasrodrigues.pokemonshowcase.data_access.network.data.*
import com.lucasrodrigues.pokemonshowcase.model.*
import kotlin.math.max
import kotlin.math.min

fun DisplayPokemon.toPokemon(): Pokemon {
    return Pokemon(
        pokemonName = pokemonName,
        number = number
    )
}

fun AbilityInsidePokemonData.toAbility(): Ability {
    return Ability(
        abilityId = this.ability.url.toId(),
        name = this.ability.name
    )
}

fun MoveInsidePokemonData.toMove(): Move {
    return Move(
        moveId = this.move.url.toId(),
        name = this.move.name
    )
}

fun StatInsidePokemonData.toPokemonStat(): PokemonStat {
    return PokemonStat(
        name = this.stat.name,
        value = this.base_stat
    )
}

fun TypeInsidePokemonData.toType(): Type {
    return Type(
        typeId = this.type.url.toId(),
        name = this.type.name
    )
}

fun DisplayItemData.toDisplayPokemon(): DisplayPokemon {
    return DisplayPokemon(
        pokemonName = this.name,
        number = this.url.toId()
    )
}

fun Map<String, Any?>.toPokemonSpriteList(): List<PokemonSprite> {
    return this.filter {
        it.value != null && it.value is String
    }.map {
        PokemonSprite(
            name = it.key,
            url = it.value as String
        )
    }
}

fun PagedListData<DisplayItemData>.toPagedPokemonList(
    generation: Generation
): PagedPokemonList {
    val absolutePreviousOffset = this.previous?.toOffset()
    val absoluteNextOffset = this.next?.toOffset()

    val lowerBound = generation.lowerBound() - 1
    val upperBound = generation.upperBound() - 1

    val nextOffset = if (absoluteNextOffset == null || absoluteNextOffset >= upperBound)
        null
    else
        min(upperBound, absoluteNextOffset - lowerBound)

    val previousOffset = if (absolutePreviousOffset == null || absolutePreviousOffset < lowerBound)
        null
    else
        max(0, absolutePreviousOffset - lowerBound)

    return PagedPokemonList(
        pokemon = this.results.map {
            it.toDisplayPokemon()
        },
        previousOffset = previousOffset,
        nextOffset = nextOffset,
    )
}

fun PokemonData.toPokemonDetailed(): PokemonDetailed {
    return PokemonDetailed(
        pokemon = this.toPokemon(),
        abilities = this.abilities.map {
            it.toAbility()
        },
        types = this.types.map {
            it.toType()
        },
        moves = this.moves.map {
            it.toMove()
        }
    )
}

fun PokemonData.toPokemon(): Pokemon {
    return Pokemon(
        pokemonName = this.name,
        number = this.id,
        sprites = this.sprites.toPokemonSpriteList(),
        baseStats = this.stats.map {
            it.toPokemonStat()
        },
        height = this.height.toDouble() / 10,
        weight = this.weight.toDouble() / 10,
    )
}
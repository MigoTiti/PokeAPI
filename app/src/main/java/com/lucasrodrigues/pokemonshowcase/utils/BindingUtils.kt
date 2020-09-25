package com.lucasrodrigues.pokemonshowcase.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lucasrodrigues.pokemonshowcase.BuildConfig
import com.lucasrodrigues.pokemonshowcase.R
import com.lucasrodrigues.pokemonshowcase.model.DisplayPokemon
import com.lucasrodrigues.pokemonshowcase.model.LoadingState
import com.lucasrodrigues.pokemonshowcase.model.PokemonDetailed

object BindingUtils {

    @JvmStatic
    @BindingAdapter("icon")
    fun setIcon(view: ImageButton, drawable: Drawable?) {
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("visibleIfLoading")
    fun visibleIfLoading(view: View, loadingState: LoadingState?) {
        setVisibility(view, loadingState is LoadingState.Loading)
    }

    @JvmStatic
    @BindingAdapter("visibleIfError")
    fun visibleIfError(view: View, loadingState: LoadingState?) {
        setVisibility(view, loadingState is LoadingState.Error)
    }

    @JvmStatic
    @BindingAdapter("pokemonIcon")
    fun setPokemonIcon(view: ImageView, displayPokemon: DisplayPokemon?) {
        Glide.with(view)
            .load(
                if (displayPokemon != null)
                    "${BuildConfig.POKEMON_IMAGE_BASE_URL}${displayPokemon.number}.png"
                else
                    R.drawable.placeholder
            )
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("pokemonIcon")
    fun setPokemonIcon(view: ImageView, pokemon: PokemonDetailed?) {
        Glide.with(view)
            .load(
                if (pokemon != null)
                    "${BuildConfig.POKEMON_IMAGE_BASE_URL}${pokemon.pokemon.number}.png"
                else
                    R.drawable.placeholder
            )
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("image")
    fun setImage(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url ?: R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, value: Boolean?) {
        view.isVisible = value == true
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibilityNew(view: View, value: Boolean?) {
        if (value == true)
            view.isVisible = true
        else
            view.isInvisible = true
    }
}
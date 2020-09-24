package com.lucasrodrigues.pokemonshowcase.framework

interface AlertService {
    fun sendErrorAlert(message: String)
    fun sendSuccessAlert(message: String)
    fun sendInfoAlert(message: String)
    fun sendErrorAlert(error: Throwable)
}
package com.pokemon.project.home.data.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonResponse (
    var count: Int,
    var next  : String,
    var previous: String?,
    var results: List<Results>
    ): Parcelable
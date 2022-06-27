package com.pokemon.project.home.data.model.response

data class DetailPokemonResponse (
    var base_happiness: Int,
    var capture_rate: Int,
    var color: Color,
    var egg_groups: List<EggGroups>
        )
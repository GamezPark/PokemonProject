package com.pokemon.project.home.data.model.response

data class Abilities(
    var ability: Ability,
    var is_hidden: Boolean,
    var slot: Int
){
    data class Ability(var name: String, var url: String)
}

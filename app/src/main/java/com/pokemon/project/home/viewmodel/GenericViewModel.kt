package com.pokemon.project.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pokemon.project.home.data.model.response.AbilitiesResponse
import com.pokemon.project.home.data.model.response.DetailPokemonResponse
import com.pokemon.project.home.data.model.response.PokemonResponse
import com.pokemon.project.home.data.service.network.ServiceCallback
import com.pokemon.project.home.viewmodel.baseviewmodel.BaseViewModel

class GenericViewModel(private val mContext: Context) : BaseViewModel(mContext),
    ServiceCallback.ListPokemons, ServiceCallback.DetailsPokemon, ServiceCallback.AbilitiesPokemon{

    private val _listPokemon = MutableLiveData<PokemonResponse>()
    val listPokemon: LiveData<PokemonResponse>
        get() = _listPokemon

    private val _detailPokemon = MutableLiveData<DetailPokemonResponse>()
    val detailPokemon: LiveData<DetailPokemonResponse>
        get() = _detailPokemon

    private val _abilitiesPokemon = MutableLiveData<AbilitiesResponse>()
    val abilities: LiveData<AbilitiesResponse>
        get() = _abilitiesPokemon

    init {
        onGetListPokemonService("20")
    }

   fun onGetListPokemonService(limit: String){
        progress.set(true)
        repository.listServicePokemon(limit, this)
    }

    fun onGetDetailPokemon(name: String?){
        progress.set(true)
        name?.let {
            repository.detailServicePokemon(it, this)
        }

    }

    fun onGetAbilitiePokemon(name: String?){
        progress.set(true)
        name?.let {
            repository.abilitiesServicePokemon(it, this)
        }

    }

   override fun onSuccessPokemonList(
        serviceId: Int,
        pokemonListResponse: PokemonResponse) {

        progress.set(false)
      //  repositoryLocal.deleteAll()
        _listPokemon.value = pokemonListResponse
        pokemonListResponse.results.let {
           // repositoryLocal.insertServices(it)
        }
    }

    override fun onSuccessPokemonDetail(
        serviceId: Int,
        detailPokemonResponse: DetailPokemonResponse
    ) {
        progress.set(false)
        //  repositoryLocal.deleteAll()
        _detailPokemon.value = detailPokemonResponse
        detailPokemonResponse.let {
            // repositoryLocal.insertServices(it)
        }
    }

    override fun onSuccessPokemonabilities(
        serviceId: Int,
        abilitiesPokemonResponse: AbilitiesResponse
    ) {
        progress.set(false)
        //  repositoryLocal.deleteAll()
        _abilitiesPokemon.value = abilitiesPokemonResponse
        abilitiesPokemonResponse.let {
            // repositoryLocal.insertServices(it)
        }
    }


}
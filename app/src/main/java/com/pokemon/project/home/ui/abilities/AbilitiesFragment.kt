package com.pokemon.project.home.ui.abilities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pokemon.project.databinding.AbilitiesFragmentBinding
import com.pokemon.project.home.data.model.response.AbilitiesResponse
import com.pokemon.project.home.ui.detail.DetailFragment
import com.pokemon.project.home.viewmodel.GenericViewModel

class AbilitiesFragment : Fragment() {

    private var namePokemon: String? = null
    private lateinit var homeViewModel : GenericViewModel
    private lateinit var binding: AbilitiesFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            namePokemon = it.getString(DetailFragment.ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AbilitiesFragmentBinding.inflate(inflater)
        homeViewModel = GenericViewModel(requireContext())
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel
        homeViewModel.onGetAbilitiePokemon(namePokemon)
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        homeViewModel.abilities.observe(viewLifecycleOwner, Observer {
            applyBinding(it)
        })
    }

    private fun applyBinding(it: AbilitiesResponse) {
        with(binding) {
            if(it.abilities[0].ability.name.isNotEmpty()) abiliteOne.text = it.abilities[0].ability.name
            if(it.abilities[1].ability.name.isNotEmpty()) abilitieTwo.text = it.abilities[1].ability.name
            if(it.abilities[2].ability.name.isNotEmpty()) abilitieThree.text = it.abilities[2].ability.name
        }
    }

}
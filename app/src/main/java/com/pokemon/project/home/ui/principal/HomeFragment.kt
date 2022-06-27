package com.pokemon.project.home.ui.principal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.pokemon.project.R
import com.pokemon.project.databinding.FragmentHomeBinding
import com.pokemon.project.home.adapter.PokemenItemAdapter
import com.pokemon.project.home.data.model.response.PokemonResponse
import com.pokemon.project.home.data.model.response.Results
import com.pokemon.project.home.ui.detail.DetailFragment
import com.pokemon.project.home.viewmodel.GenericViewModel

class HomeFragment : Fragment(){

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel : GenericViewModel
    private lateinit var adapter: PokemenItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = PokemenItemAdapter(
            actionClick = { it: Results ->
                val bundle = Bundle()
                bundle.putString(DetailFragment.ARG_PARAM1, it.name)
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailFragment, bundle)

            }, requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        homeViewModel = GenericViewModel(requireContext())
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel
        applyBinding()
        setupObservers()

        return binding.root
    }

    private fun applyBinding() {
        with(binding) {
            recyclerviewHome.adapter = adapter
        }
    }


    private fun setupObservers() {
        homeViewModel.listPokemon.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.results.toMutableList())

        })
    }


}
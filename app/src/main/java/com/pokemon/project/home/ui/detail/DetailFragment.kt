package com.pokemon.project.home.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.pokemon.project.R
import com.pokemon.project.databinding.FragmentDetailBinding
import com.pokemon.project.home.data.model.response.DetailPokemonResponse
import com.pokemon.project.home.data.model.response.EggGroups
import com.pokemon.project.home.viewmodel.GenericViewModel

class DetailFragment : Fragment() {

    private var namePokemon: String? = null
    private lateinit var homeViewModel : GenericViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            namePokemon = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        homeViewModel = GenericViewModel(requireContext())


        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel
        homeViewModel.onGetDetailPokemon(namePokemon)
        setupObservers()


        binding.btnHabilidades.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(ARG_PARAM1, namePokemon)
            view?.findNavController()?.navigate(R.id.action_detailFragment_to_abilitiesFragment, bundle)
        }
        return binding.root
    }

    private fun applyBinding(view: DetailPokemonResponse) {
        with(binding) {
            tvNamePokemon.text = namePokemon
            tvFelicidadBase.text= requireActivity().getString(R.string.felicidad_base, view.base_happiness.toString())
            tvTasaCaptura.text = requireActivity().getString(R.string.captura,  view.capture_rate.toString())
            tvColor.text = requireActivity().getString(R.string.color, view.color.name)

            var grupos: String = ""
            for(item: EggGroups in view.egg_groups){
                grupos += item.name+", "
            }
            tvGrupos.text = "Grupos de huevos: "+ grupos
        }
    }

    private fun setupObservers() {
        homeViewModel.detailPokemon.observe(viewLifecycleOwner, Observer {
            applyBinding(it)
        })
    }
    companion object {
        const val ARG_PARAM1 = "NAME_POKEMON"
    }
}
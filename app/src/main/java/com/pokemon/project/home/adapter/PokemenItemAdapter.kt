package com.pokemon.project.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pokemon.project.databinding.PokemonItemAdapterBinding
import com.pokemon.project.home.data.model.response.Results

class PokemenItemAdapter(
    private val actionClick: (Results) -> Unit,
    private val mActivity: FragmentActivity
): ListAdapter<Results, RecyclerView.ViewHolder>(CardDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokemonItemAdapterBinding.inflate(inflater, parent, false)
        return PrincipalCardViewHolder(binding, parent.context, mActivity)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cardModel = getItem(position)
        if (holder is PrincipalCardViewHolder) {
            holder.bind(cardModel)
        }
    }

    inner class PrincipalCardViewHolder(val binding: ViewBinding, val context: Context, val mActivity: FragmentActivity) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Results) {
                with(binding as PokemonItemAdapterBinding) {
                        namePokemon.text = item.name
                        containerNamePokemon.setOnClickListener {
                            actionClick(item)
                        }
                }
            }
        }


    object CardDiff : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }


}
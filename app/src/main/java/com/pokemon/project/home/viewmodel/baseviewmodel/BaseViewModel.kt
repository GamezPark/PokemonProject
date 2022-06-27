package com.pokemon.project.home.viewmodel.baseviewmodel

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.pokemon.project.home.data.service.network.ServiceCallback
import com.pokemon.project.home.data.service.repository.ServiceRepositoryImpl
import com.pokemon.project.home.data.service.repository.ServicesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel (private val context: Context)
    : ViewModel(), ServiceCallback.GenericCallback {


    var repository: ServicesRepository
    var progress = ObservableBoolean(false)

    //var repositoryLocal: DataServiceDao

    init {
        repository = ServiceRepositoryImpl(
            context,
            CoroutineScope(Job() + Dispatchers.Main)
        )

       // repositoryLocal = AppDataBaseLocal.getInstance(context)!!.ServicesCommerce()
    }

    override fun onRetry(serviceId: Int, httpCode: Int, message: String) {

    }

    override fun onFormatError(serviceId: Int, message: String) {

    }

    override fun onServiceError(serviceId: Int, code: Int, message: String?) {

    }

}
package com.pokemon.project.home.data.service.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pokemon.project.home.data.service.Constants
import com.pokemon.project.home.data.service.network.api.PokemonApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class NetworkDefinition (val context: Context) {

    private val TIMEOUT = 100L
    val service: PokemonApi
    var requestInterceptor: RequestInterceptor? = null

    init {
        /**
         * Moshi para ver la conexion por terminal
         */
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, MoshiUTCDateUtil())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(NullMoshiConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(generateHttpClient())
            .build()

        service = retrofit.create(PokemonApi::class.java)
    }

    private fun generateHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val interceptorHeaders = Interceptor {
            val request = it.request().newBuilder()
                .build()
            requestInterceptor?.onRequest(it.request())
            val response = it.proceed(request)
            val copyResponse = response.newBuilder().build()
            requestInterceptor?.onResponse(copyResponse)
            response
        }
        return OkHttpClient.Builder()
            .addInterceptor(interceptorHeaders)
            .addInterceptor(interceptor)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Clase generica para la respuesta de los endpoint
     */
    class Connect<T>(val context: Context, val serviceId: Int) {
        suspend fun startService(
            caller: CallService<T>
        ){
            try {
                val response = caller.call().await()
                if (response != null) {
                    caller.onSuccessResponse(serviceId, response)
                } else
                    caller.onServiceError(
                        serviceId,
                        Constants.INVALID_RESPONSE_SERVICE_ERROR_CODE,
                        "Error en la respuesta del servicio"
                    )
            } catch (e: Exception) {
                if (e is HttpException) {
                    caller.onRetry(
                        serviceId,
                        e.code(),
                        e.message?.let { it } ?: "Error en el servicio")
                }else
                    caller.onServiceError(serviceId,
                        Constants.UNKNOWN_SERVICE_ERROR_CODE,
                        e.message?.let { it } ?: "Error en el servicio")
            }
        }
        interface CallService<T> {
            suspend fun call(): Deferred<T>
            fun onSuccessResponse (serviceId: Int, response: T)
            /**
             * Called when receive http exception
             */
            fun onRetry(serviceId: Int, httpCode: Int, message: String)
            /**
             * Called when service response is not valid or unexpected error occurs
             */
            fun onServiceError (serviceId: Int, code: Int, message : String?)
        }
    }
    interface RequestInterceptor {
        fun onRequest (request: Request?)
        fun onResponse (response: Response?)
    }
}
package com.example.perfectweatherallyear.api

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class SynchronousCallAdapterFactory : CallAdapter.Factory() {
    override operator fun get(
        returnType: Type,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): CallAdapter<Any?, Any?> {
        return object : CallAdapter<Any?, Any?> {
            override fun responseType(): Type {
                return returnType
            }

            override fun adapt(call: Call<Any?>): Any? {
                return try {
                    call.execute().body()
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
        }
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return SynchronousCallAdapterFactory()
        }
    }
}
package com.example.flowershopapp.API

import com.example.flowershopapp.API.Model.BouquetRemote
import com.example.flowershopapp.API.Model.OrderBouquetCrossRefRemote
import com.example.flowershopapp.API.Model.OrderBouquetRemote
import com.example.flowershopapp.API.Model.OrderRemote
import com.example.flowershopapp.API.Model.UserOrderCrossRefRemote
import com.example.flowershopapp.API.Model.UserOrderRemote
import com.example.flowershopapp.API.Model.UserRemote
import com.example.flowershopapp.Entities.Model.UsersWithOrders
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MyServerService {
    @GET("Users")
    suspend fun getUsers(): List<UserRemote>

    @GET("Users/{name}")
    suspend fun getUser(
        @Path("name") name: String,
    ): UserRemote

    @GET("Users/{name}")
    suspend fun getUserWithOrders(
        @Path("name") name: String,
    ): UserOrderRemote

    @GET("Users/{id}")
    suspend fun getUsersWithOrders(): List<UserOrderRemote>

    @POST("Users")
    suspend fun createUser(
        @Body user: UserRemote,
    ): UserRemote

    @PUT("Users/{id}")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body user: UserRemote,
    ): UserRemote

    @DELETE("Users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int,
    ): UserRemote

    @GET("Orders")
    suspend fun getOrders(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int,
    ): List<OrderRemote>

    @GET("Orders/{id}")
    suspend fun getOrder(
        @Path("id") id: Int,
    ): OrderRemote

    @GET("Orders/{id}")
    suspend fun getOrderWithBouquets(
        @Path("id") id: Int,
    ): OrderBouquetRemote

    @GET("Orders/{id}")
    suspend fun getOrdersWithBouquets(): List<OrderBouquetRemote>

    @POST("Orders")
    suspend fun createOrder(
        @Body order: OrderRemote,
    ): OrderRemote

    @DELETE("Orders/{id}")
    suspend fun deleteOrder(
        @Path("id") id: Int,
    ): OrderRemote

    @GET("Bouquets")
    suspend fun getBouquets(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int,
    ): List<BouquetRemote>

    @GET("Bouquets/{id}")
    suspend fun getBouquet(
        @Path("id") id: Int,
    ): BouquetRemote

    @POST("Bouquets")
    suspend fun createBouquet(
        @Body bouquet: BouquetRemote,
    ): BouquetRemote

    @PUT("Bouquets/{id}")
    suspend fun updateBouquet(
        @Path("id") id: Int,
        @Body bouquet: BouquetRemote,
    ): BouquetRemote

    @DELETE("Bouquets/{id}")
    suspend fun deleteBouquet(
        @Path("id") id: Int,
    ): BouquetRemote

    @POST("OrderBouquets")
    suspend fun createOrderBouquet(
        @Body orderBouquet: OrderBouquetCrossRefRemote,
    )

    @DELETE("OrderBouquets/{id}")
    suspend fun deleteOrderBouquet(
        @Path("id") id: Int,
    )

    @POST("UserOrders")
    suspend fun createUserOrder(
        @Body userOrder: UserOrderCrossRefRemote,
    )

    @DELETE("UserOrders/{id}")
    suspend fun deleteUserOrder(
        @Path("id") id: Int,
    )

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8079/"

        @Volatile
        private var INSTANCE: MyServerService? = null

        fun getInstance(): MyServerService {
            return INSTANCE ?: synchronized(this) {
                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BASIC
                val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                    .build()
                    .create(MyServerService::class.java)
                    .also { INSTANCE = it }
            }
        }
    }
}
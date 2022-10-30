package `in`.lj.lifecare.api

import `in`.lj.lifecare.data.Detected
import `in`.lj.lifecare.data.Features
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DiagnosisApi {

    @GET("/")
    suspend fun getFeatureList(): Response<Features>

    @POST("/detect")
    suspend fun getDisease( @Body body: Features): Response<Detected>

}
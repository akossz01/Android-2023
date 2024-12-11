package com.tasty.recipesapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApiClient {
    companion object {
        private const val BASE_URL =
            "https://recipe-appservice-cthjbdfafnhfdtes.germanywestcentral-01.azurewebsites.net/"

        private const val AUTH_TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjJjOGEyMGFmN2ZjOThmOTdmNDRiMTQyYjRkNWQwODg0ZWIwOTM3YzQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxNjI1ODkxMzM3NDgtcWpndWZzNnJ2NDRmY3J0NHE4ZHN0cmU2djFlbG80Y3MuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxNjI1ODkxMzM3NDgtcWpndWZzNnJ2NDRmY3J0NHE4ZHN0cmU2djFlbG80Y3MuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQ0OTA4MTM1OTc1MjA3NTYwMzAiLCJlbWFpbCI6ImFrb3NzejEyQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiajlVT29YTHphQ2NNeF9rX2NFelp4USIsIm5hbWUiOiJBa29zIFN6YWJvIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0pQeXhsZTR3YVFQLURSRWNmdFdTWVl6eDNoRC1PVDdZNHVreFZkVHJRTS05TUVLUT1zOTYtYyIsImdpdmVuX25hbWUiOiJBa29zIiwiZmFtaWx5X25hbWUiOiJTemFibyIsImlhdCI6MTczMzkyNzE3MywiZXhwIjoxNzMzOTMwNzczfQ.PIq5iG_jcstA6MEcBx6nthEBe4IHeKHcf1xgqbjNjtuDEE1y2hF_SW2NwpD7aVfs4C9142KTIuulJGKCDB4BgiQKQi-ylQaWZhAZVwP_AB_Z_F3juUQCLh51kyeg_eA2sa_mHcPVW15GE0wJaQIvNFqN3rfmAP6PIB69GDSymSM8in34yT3L1h_-4ocq3nzc8On-1HEkq8a8FF-5N6NE3UsAffCoF-rjnydEmr8wUOSB5XQed2tiyYOm_vnsxhQRVPk50iAYTcB1xww86m0kbfhkGJbizdzKhsp7_MFhf7sjIQYAZaSDckvdFD6UZKtHn9clAHdE1-71_YF_jkQtZQ"
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(AUTH_TOKEN))
        .build()

    val recipeService: RecipeService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        recipeService = retrofit.create(RecipeService::class.java)
    }
}

package dev.atharvakulkarni.moviefinder.data.repositories

import dev.atharvakulkarni.moviefinder.data.model.MovieDetail
import dev.atharvakulkarni.moviefinder.data.network.ApiInterface
import dev.atharvakulkarni.moviefinder.data.network.SafeApiRequest

class MovieDetailRepository(private val api: ApiInterface) : SafeApiRequest()
{
    suspend fun getMovieDetail(title: String, apiKey: String): MovieDetail
    {
        return apiRequest { api.getMovieDetailData(title, apiKey) }
    }
}
package dev.atharvakulkarni.moviefinder.data.repositories

import dev.atharvakulkarni.moviefinder.data.model.SearchResults
import dev.atharvakulkarni.moviefinder.data.network.ApiInterface
import dev.atharvakulkarni.moviefinder.data.network.SafeApiRequest

class HomeRepository(private val api: ApiInterface) : SafeApiRequest()
{
    suspend fun getMovies(searchTitle: String, apiKey: String, pageIndex: Int): SearchResults
    {
        return apiRequest{ api.getSearchResultData(searchTitle, apiKey, pageIndex) }
    }
}
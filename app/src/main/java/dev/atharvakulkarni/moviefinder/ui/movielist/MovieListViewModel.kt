package dev.atharvakulkarni.moviefinder.ui.movielist

import androidx.lifecycle.ViewModel
import dev.atharvakulkarni.moviefinder.data.repositories.MovieListRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.atharvakulkarni.moviefinder.data.model.SearchResults
import dev.atharvakulkarni.moviefinder.util.ApiException
import dev.atharvakulkarni.moviefinder.util.AppConstant
import dev.atharvakulkarni.moviefinder.util.NoInternetException
import dev.atharvakulkarni.moviefinder.util.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieListViewModel(private val repository: MovieListRepository) : ViewModel()
{
    private var pageIndex = 0
    private var totalMovies = 0
    private var movieList = ArrayList<SearchResults.SearchItem?>()

    private val _moviesLiveData = MutableLiveData<State<ArrayList<SearchResults.SearchItem?>>>()
    val moviesLiveData: LiveData<State<ArrayList<SearchResults.SearchItem?>>>
        get() = _moviesLiveData

    private val _movieNameLiveData = MutableLiveData<String>()
    val movieNameLiveData: LiveData<String>
        get() = _movieNameLiveData

    private val _loadMoreListLiveData = MutableLiveData<Boolean>()
    val loadMoreListLiveData: LiveData<Boolean>
        get() = _loadMoreListLiveData

    private lateinit var movieResponse: SearchResults

    init
    {
        _loadMoreListLiveData.value = false
        _movieNameLiveData.value = ""
    }

    fun getMovies()
    {
        if (pageIndex == 1)
        {
            movieList.clear()
            _moviesLiveData.postValue(State.loading())
        }
        else
        {
            if (movieList.isNotEmpty() && movieList.last() == null)
                movieList.removeAt(movieList.size - 1)
        }
        viewModelScope.launch(Dispatchers.IO)
        {
            if (_movieNameLiveData.value != null && _movieNameLiveData.value!!.isNotEmpty())
            {
                try
                {
                    movieResponse = repository.getMovies(_movieNameLiveData.value!!, AppConstant.API_KEY, pageIndex)
                    withContext(Dispatchers.Main)
                    {
                        if (movieResponse.response == AppConstant.SUCCESS)
                        {
                            movieList.addAll(movieResponse.search)
                            totalMovies = movieResponse.totalResults.toInt()
                            _moviesLiveData.postValue(State.success(movieList))
                            _loadMoreListLiveData.value = false
                        }
                        else
                            _moviesLiveData.postValue(State.error(movieResponse.error))
                    }
                }
                catch (e: ApiException)
                {
                    withContext(Dispatchers.Main)
                    {
                        _moviesLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                }
                catch (e: NoInternetException)
                {
                    withContext(Dispatchers.Main)
                    {
                        _moviesLiveData.postValue(State.error(e.message!!))
                        _loadMoreListLiveData.value = false
                    }
                }
            }
        }
    }

    fun searchMovie(query: String)
    {
        _movieNameLiveData.value = query
        pageIndex = 1
        totalMovies = 0
        getMovies()
    }

    fun loadMore()
    {
        pageIndex++
        getMovies()
    }

    fun checkForLoadMoreItems(visibleItemCount: Int, totalItemCount: Int, firstVisibleItemPosition: Int)
    {
        if (!_loadMoreListLiveData.value!! && (totalItemCount < totalMovies))
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0)
                _loadMoreListLiveData.value = true
    }
}
package dev.atharvakulkarni.moviefinder

import android.app.Application
import dev.atharvakulkarni.moviefinder.data.network.ApiInterface
import dev.atharvakulkarni.moviefinder.data.network.NetworkConnectionInterceptor
import dev.atharvakulkarni.moviefinder.data.repositories.HomeRepository
import dev.atharvakulkarni.moviefinder.data.repositories.MovieDetailRepository
import dev.atharvakulkarni.moviefinder.data.repositories.MovieListRepository
import dev.atharvakulkarni.moviefinder.ui.home.HomeViewModelFactory
import dev.atharvakulkarni.moviefinder.ui.moviedetail.MovieDetailViewModelFactory
import dev.atharvakulkarni.moviefinder.ui.movielist.MovieListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MFApplication : Application(), KodeinAware
{
    override val kodein = Kodein.lazy{
        import(androidXModule(this@MFApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { HomeRepository(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from singleton { MovieDetailRepository(instance()) }
        bind() from provider { MovieDetailViewModelFactory(instance()) }
        bind() from singleton { MovieListRepository(instance()) }
        bind() from provider { MovieListViewModelFactory(instance()) }
    }
}
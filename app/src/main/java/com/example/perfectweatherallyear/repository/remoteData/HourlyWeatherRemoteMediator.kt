package com.example.perfectweatherallyear.repository.remoteData

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.perfectweatherallyear.model.HourWeather
import com.example.perfectweatherallyear.repository.remoteData.weatherData.RemoteWeatherDataSource
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
    class HourlyWeatherRemoteMediator(
    private val city: String,
    private val amountOfDays: Int,
    private val networkService: RemoteWeatherDataSource
    ) : RemoteMediator<Int, HourWeather>() {

        override suspend fun load(loadType: LoadType, state: PagingState<Int, HourWeather>): MediatorResult {
//            val page = when (loadType) {
//                LoadType.REFRESH -> {
//                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                    remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
//                }
//                LoadType.PREPEND -> {
//                    val remoteKeys = getRemoteKeyForFirstItem(state)
//                    val prevKey = remoteKeys?.prevKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    prevKey
//                }
//                LoadType.APPEND -> {
//                    val remoteKeys = getRemoteKeyForLastItem(state)
//                    val nextKey = remoteKeys?.nextKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    nextKey
//                }
//            }

            return try {

                val weatherForecast = networkService.getWeatherForecast(city, amountOfDays)
                val endOfPaginationReached = weatherForecast.isEmpty()

//                    database.withTransaction {
//                        if (loadType == LoadType.REFRESH) {
//                            database.remoteKeysDao().clearRemoteKeys()
//                            database.weatherDao().clearHourlyWeather()
//                        }
//                        val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
//                        val nextKey = if (endOfPaginationReached) null else page + 1
//                        val keys = weatherForecast.map {
//                            RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
//                        }
//                        database.remoteKeysDao().insertAll(keys)
//                        database.weatherDao().upsertHourlyWeather(dataHourlyWeatherData)
//                    }
                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (e: IOException) {
                MediatorResult.Error(e)
            } catch (e: HttpException) {
                MediatorResult.Error(e)
            }
        }

//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, HourWeather>): RemoteKeys? {
//        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { repo -> database.remoteKeysDao().remoteKeysRepoId(repo.id) }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, HourWeather>): RemoteKeys? {
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { repo -> database.remoteKeysDao().remoteKeysRepoId(repo.id) }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, HourWeather>
//    ): RemoteKeys? {
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { repoId -> database.remoteKeysDao().remoteKeysRepoId(repoId) }
//        }
//    }
}

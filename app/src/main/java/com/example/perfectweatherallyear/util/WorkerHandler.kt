package com.example.perfectweatherallyear.util

//class WorkerHandler(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
//    @Inject
//    lateinit var repository: WeatherRepository
//    lateinit var dayWeatherForNotification: DayWeather
//    lateinit var hourWeatherForNotification:  HourWeather
//
//    init {
//        appContext.appComponent.inject(this)
//    }

//    override fun doWork(): Result {
//        GlobalScope.launch {
//            NotificationHandler.createNotification(applicationContext, loadHourWeatherTempForNotification())
//        }
//        return success()
//    }

//    companion object {
//        private val WEATHER_TAG = "PeriodicWorkTag"
//
//        fun createWorker(context: Context) {
//            val constraints = Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresBatteryNotLow(false)
//                .build()
//
//            val work = PeriodicWorkRequestBuilder<WorkerHandler>(4, TimeUnit.HOURS)
//                .setConstraints(constraints)
//                .build()
//            val workManager = WorkManager.getInstance(context)
//                workManager.enqueueUniquePeriodicWork(WEATHER_TAG, ExistingPeriodicWorkPolicy.REPLACE, work)
//        }
//    }
//
//    private fun loadDayWeatherForNotification(): DayWeather {
//        val location = Location(1, "Moscow")
//        val weatherForecast = repository.getWeatherForecast(location, DAYS_NUMBER)
//        weatherForecast.subscribeWith(object:
//            DisposableObserver<List<DayWeather>>() {
//            override fun onNext(t: List<DayWeather>) {
//                val weatherForecastLiveDataConverted = LiveDataReactiveStreams.fromPublisher(
//                    weatherForecast.toFlowable(
//                        BackpressureStrategy.DROP
//                    )
//                )
//                dayWeatherForNotification = weatherForecastLiveDataConverted.value?.get(
//                    weatherForecastLiveDataConverted.value!!.size - 1) ?: throw Exception()
//            }
//
//            override fun onError(e: Throwable) {
//                Log.i("Error", e.toString())
//            }
//
//            override fun onComplete() {
//                TODO("Not yet implemented")
//            }
//        })
//        return dayWeatherForNotification
//    }
//
//    private suspend fun loadHourWeatherTempForNotification(): String {
//        var hourWeatherTempForNotification = ""
//            when (val hourlyWeatherForecast = repository.getHourlyWeather(DAYS_NUMBER, loadDayWeatherForNotification())) {
//                is DataResult.Ok -> {
//                    val sdf = SimpleDateFormat("HH:mm:ss")
//                    val currentHour: String = sdf.format(Date()).take(2)
//                    for (hourWeather in hourlyWeatherForecast.response) {
//                        val hour = hourWeather.time.substringAfterLast(" ").take(2)
//                        if(hour == currentHour) {
//                            hourWeatherForNotification = hourWeather
//                        }
//                    }
//                    hourWeatherTempForNotification = "temperature ${hourWeatherForNotification.temperature}"
//                }
//                is DataResult.Error -> hourlyWeatherForecast.error
//            }
//        return hourWeatherTempForNotification
//    }
//}
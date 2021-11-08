package com.example.perfectweatherallyear.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.perfectweatherallyear.repository.WeatherRepositoryImp;
import com.example.perfectweatherallyear.ui.weekWeather.WeekWeatherViewModel;

public class WeekWeatherViewModelFactory implements ViewModelProvider.Factory {
    private final WeatherRepositoryImp repository;

    public WeekWeatherViewModelFactory(WeatherRepositoryImp repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new WeekWeatherViewModel(repository);
    }
}
package com.example.perfectweatherallyear.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.perfectweatherallyear.repository.Repository;
import com.example.perfectweatherallyear.ui.weekWeather.WeekWeatherViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Repository mParam;

    public ViewModelFactory(Repository param) {
        mParam = param;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new WeekWeatherViewModel(mParam);
    }
}
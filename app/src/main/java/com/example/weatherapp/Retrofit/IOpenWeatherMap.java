package com.example.weatherapp.Retrofit;

import com.example.weatherapp.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatheByLatLn(@Query("lat") String lat,
                                               @Query("lon") String lng,
                                               @Query("appid") String appid,
                                               @Query("units") String units);
}

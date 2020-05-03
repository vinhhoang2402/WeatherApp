package com.example.weatherapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.weatherapp.Common.Common;
import com.example.weatherapp.Model.WeatherResult;
import com.example.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.weatherapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWeatherFragment extends Fragment {
    ImageView image_weather;
    TextView txt_name_city,txt_wind,txt_pressure,txt_humidity,txt_sunrise,txt_sunset,txt_geocoords,txt_desciption,txt_datetime,txt_nhietdo;
    LinearLayout weather_pannel;
    ProgressBar loading;
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mServicel;
    static TodayWeatherFragment instance;

    public static TodayWeatherFragment getInstance()
    {
        if (instance==null)
            instance=new TodayWeatherFragment();
        return instance;
    }


    public TodayWeatherFragment() {
        compositeDisposable=new CompositeDisposable();
        Retrofit retrofit= RetrofitClient.getClient();
        mServicel=retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View itemView= inflater.inflate(R.layout.fragment_today_weather, container, false);
         image_weather=itemView.findViewById(R.id.img_weather);
        txt_datetime=itemView.findViewById(R.id.txt_date_time);
        txt_name_city=itemView.findViewById(R.id.txt_city_name);
        txt_desciption=itemView.findViewById(R.id.txt_mota);
        txt_geocoords=itemView.findViewById(R.id.txt_geocoords);
        txt_humidity=itemView.findViewById(R.id.txt_humidyti);
        txt_nhietdo=itemView.findViewById(R.id.txt_nhietdo);
        txt_pressure=itemView.findViewById(R.id.txt_pressure);
        txt_sunrise=itemView.findViewById(R.id.txt_sunrise);
        txt_sunset=itemView.findViewById(R.id.txt_sunset);
        txt_wind=itemView.findViewById(R.id.txt_wind);

        weather_pannel=itemView.findViewById(R.id.weather_pannel);
        loading=itemView.findViewById(R.id.loading);

        getWeather();

         return itemView;
    }

    private void getWeather() {
    compositeDisposable.add(mServicel.getWeatheByLatLn(String.valueOf(Common.current_location.getLatitude()),
            String.valueOf(Common.current_location.getLongitude()),
            Common.APP_ID,
            "metric")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<WeatherResult>() {
        @Override
        public void accept(WeatherResult weatherResult) throws Exception {
            Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/")
                    .append(weatherResult.getWeather().get(0).getIcon())
            .append(".png").toString()).into(image_weather);
            txt_nhietdo.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("*C").toString());
            txt_datetime.setText(Common.converDate(weatherResult.getDt()));
            txt_pressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append("hpa").toString());
            txt_humidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append("%").toString());
            txt_sunrise.setText(Common.convertHour(weatherResult.getSys().getSunrise()));
            txt_sunset.setText(Common.convertHour(weatherResult.getSys().getSunset()));
            txt_geocoords.setText(new StringBuilder("").append(weatherResult.getCoord().toString()).append("").toString());
            txt_wind.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())).append("Speed,")+
                    new StringBuilder(String.valueOf(weatherResult.getWind().getGust())).append("/ms").toString());

            weather_pannel.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
    }));
    }

}

package com.example.cozma.weatherapp.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cozma.weatherapp.R;
import com.example.cozma.weatherapp.customView.TextViewOpenSans;
import com.example.cozma.weatherapp.customView.TextViewOpenSansBold;
import com.example.cozma.weatherapp.model.WeatherDataModel;
import com.example.cozma.weatherapp.util.Utils;

import java.util.List;

/**
 * Created by cozma on 03.11.2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherDataModel> weathersList;
    private Context context;
    private Integer viewId;
    MyAdapterListener onClickListener;


    public WeatherAdapter(Context context, Integer viewId, List<WeatherDataModel> weathersList, MyAdapterListener listener) {
        this.context = context;
        this.viewId = viewId;
        this.weathersList = weathersList;
        this.weathersList = weathersList;
        onClickListener = listener;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return weathersList.size();
    }


    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);
        Utils.showObjectLog("Adapter", viewId);
        WeatherViewHolder pvh = new WeatherViewHolder(v);
        return pvh;
    }


    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        private CardView weatherCardCiew;
        private TextViewOpenSans myLocationTextView;
        private TextViewOpenSansBold speedWindTextView;
        private TextViewOpenSansBold humidityTextView;
        private TextView imageWeatherTextView,deletCity;
        private TextViewOpenSansBold tempTextView;


        WeatherViewHolder(View itemView) {
            super(itemView);
            weatherCardCiew = itemView.findViewById(R.id.recycler_id);
            myLocationTextView = itemView.findViewById(R.id.layoutWeather_myLocation);
            speedWindTextView = itemView.findViewById(R.id.layoutWeather_speedWind);
            humidityTextView = itemView.findViewById(R.id.layoutWeather_description);
            imageWeatherTextView = itemView.findViewById(R.id.layoutWeather_imageWeather);
            tempTextView = itemView.findViewById(R.id.layoutWeather_temp);
            deletCity = itemView.findViewById(R.id.layoutWeather_deletCity);

            deletCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.iconImageViewOnClick(view, getAdapterPosition());
                }
            });
        }
    }


    @Override
    public void onBindViewHolder(WeatherViewHolder weatherViewHolder, int position) {
        weatherViewHolder.speedWindTextView.setText(String.valueOf(weathersList.get(position).getList().get(position).getWind().getSpeed().intValue()) + " " + "km/h");
        Double kelvin = weathersList.get(position).getList().get(position).getMain().getTemp();
        Double celsius =  kelvin - 273.15F;
        weatherViewHolder.tempTextView.setText(String.valueOf(celsius.intValue()));
        weatherViewHolder.myLocationTextView.setText(weathersList.get(position).getCity().getName());
        weatherViewHolder.imageWeatherTextView.setText(weathersList.get(position).getCity().getCountry());
        weatherViewHolder.humidityTextView.setText(String.valueOf(weathersList.get(position).getList().get(position).getMain().getHumidity().intValue()) + " " + "%");
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface MyAdapterListener {
        void iconImageViewOnClick(View v, int position);
    }

}

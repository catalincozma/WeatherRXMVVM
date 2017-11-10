package com.example.cozma.weatherapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.cozma.weatherapp.adapter.WeatherAdapter;
import com.example.cozma.weatherapp.customView.TextViewOpenSans;
import com.example.cozma.weatherapp.customView.TextViewOpenSansBold;
import com.example.cozma.weatherapp.handler.PermissionHandler;
import com.example.cozma.weatherapp.model.CityDataModel;
import com.example.cozma.weatherapp.model.MyWeatherVM;
import com.example.cozma.weatherapp.model.WeatherDataModel;
import com.example.cozma.weatherapp.util.Utils;

import java.util.ArrayList;

import rx.Observer;

public class MainActivity extends AppCompatActivity implements WeatherService.WeatherListener, View.OnClickListener {
    private MyWeatherVM viewModel;
    private CardView weatherCardView;
    private TextViewOpenSans myLocationTextView;
    private TextViewOpenSansBold speedWindTextView;
    private TextViewOpenSansBold humidityTextView;
    private TextView countryTextView, addCitytextView;
    private TextViewOpenSansBold tempTextView;
    private View layoutMyLocation;
    private AutoCompleteTextView searchCityTextView;
    private Context context = this;
    private WeatherAdapter weatherCityAdapter;
    private RecyclerView cityWeatherRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MyWeatherVM(MainApp.getInstance());
        viewModel.weatherListener = this;
        viewModel.cityWeatherListener = this;
        initComponent();
        checkPermissionLocation();
        this.checkLocationEnable(this);


        /***
         *Observer for my location result
         */
        viewModel.searchObservable.subscribe(new Observer<ArrayList<CityDataModel>>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ArrayList<CityDataModel> cityDataModels) {
                populateSuggestions(cityDataModels);
            }

        });

        /**Observer for my city result */
        myLocationObservable();
    }

    private void myLocationObservable() {
        viewModel.myLocationCityObservable.subscribe(new Observer<WeatherDataModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherDataModel cityDataModel) {
                onWeatherForMyLocation(cityDataModel);
            }
        });
    }

    private void checkPermissionLocation() {
        if (!PermissionHandler.getInstance().checkPermissions(this)) {
          finish();
        }
    }

    public void checkLocationEnable(Context context) {
        if (!Utils.isLocationEnabled(context).booleanValue()) {
            layoutMyLocation.setVisibility(View.GONE);
            Utils.askGpsPermission(context);
            return;
        }
    }

    protected void onResume() {
        super.onResume();
        myLocationObservable();
    }


    private void initComponent() {
        weatherCardView = (CardView) findViewById(R.id.recycler_id);
        myLocationTextView = (TextViewOpenSans) findViewById(R.id.layoutWeather_myLocation);
        speedWindTextView = (TextViewOpenSansBold) findViewById(R.id.layoutWeather_speedWind);
        humidityTextView = (TextViewOpenSansBold) findViewById(R.id.layoutWeather_description);
        countryTextView = (TextView) findViewById(R.id.layoutWeather_imageWeather);
        tempTextView = (TextViewOpenSansBold) findViewById(R.id.layoutWeather_temp);
        searchCityTextView = (AutoCompleteTextView) findViewById(R.id.mainActivity_autoCompleteView_city);
        cityWeatherRecyclerView = (RecyclerView) findViewById(R.id.mainActivity_recycler);
        addCitytextView = (TextView) findViewById(R.id.mainActivity_addCity);
        layoutMyLocation = findViewById(R.id.weather_myLocation);


        searchCityTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.searchCity(String.valueOf(charSequence));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    //Populate my location card view with data
    public void onWeatherForMyLocation(WeatherDataModel weatherDataModel) {
        if (weatherDataModel.getCity().getName().equals("")) {
            layoutMyLocation.setVisibility(View.GONE);
            return;
        }
        layoutMyLocation.setVisibility(View.VISIBLE);
        myLocationTextView.setText(weatherDataModel.getCity().getName());
        speedWindTextView.setText(String.valueOf(weatherDataModel.getList().get(0).getWind().getSpeed().intValue()) + " " + "km/h");
        Double kelvin = weatherDataModel.getList().get(0).getMain().getTemp();
        Double celsius = kelvin - 273.15F;
        tempTextView.setText(String.valueOf(celsius.intValue()));
        countryTextView.setText(weatherDataModel.getCity().getCountry());
        humidityTextView.setText(String.valueOf(weatherDataModel.getList().get(0).getMain().getHumidity().intValue()) + " " + "%");

    }

    @Override
    //Populate recycle view with my cities
    public void onWeatherForCity(WeatherDataModel modelCity) {
        viewModel.citiesWeatherList.add(modelCity);
        weatherCityAdapter = new WeatherAdapter(this, R.layout.layout_my_location, viewModel.citiesWeatherList, new WeatherAdapter.MyAdapterListener() {
            @Override
            public void iconImageViewOnClick(View v, int position) {
                viewModel.deleteCityFromFireBase(position);
                (cityWeatherRecyclerView.getAdapter()).notifyItemRemoved(position);
            }
        });
        cityWeatherRecyclerView.setAdapter(weatherCityAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cityWeatherRecyclerView.setLayoutManager(llm);
        weatherCityAdapter.notifyDataSetChanged();
    }


    public void populateSuggestions(final ArrayList<CityDataModel> citySuggestions) {
        ArrayList<String> cities = new ArrayList<>();
        for (CityDataModel city : citySuggestions) {
            cities.add(city.getName() + "," + city.getCountry());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item,
                cities);

        adapter.notifyDataSetChanged();
        searchCityTextView.setAdapter(adapter);
        searchCityTextView.setThreshold(1);
        searchCityTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchCityTextView.setError(null);
                searchCityTextView.showDropDown();
                InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });
        searchCityTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (citySuggestions.size() != 0)
                    viewModel.getWeatherForCity(citySuggestions.get(i));
                searchCityTextView.setText("");

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mainActivity_addCity:
                searchCityTextView.setVisibility(View.VISIBLE);
                break;
        }
    }
}

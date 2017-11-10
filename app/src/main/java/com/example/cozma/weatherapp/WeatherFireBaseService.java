package com.example.cozma.weatherapp;

import com.example.cozma.weatherapp.model.CityDataModel;
import com.example.cozma.weatherapp.util.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by cozma on 06.11.2017.
 */

public class WeatherFireBaseService {
    DatabaseReference databaseReference;
    GetCitiesFromFireBase citiesFromFireBaseListener;



    public void getWeatherCitiesFireBase() {
        final ArrayList<CityDataModel> citiesFromFirebase = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("city").child("weather").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotCity : dataSnapshot.getChildren()) {
                    DataSnapshot cityName = snapshotCity.child("name");
                    DataSnapshot country = snapshotCity.child("country");
                    DataSnapshot id = snapshotCity.child("id");
                    DataSnapshot latitude = snapshotCity.child("latitude");
                    DataSnapshot longitude = snapshotCity.child("longitude");
                    CityDataModel city = new CityDataModel();

                    if (cityName.exists()) {
                        city.setName(cityName.getValue().toString());

                    }
                    if (country.exists()) {
                        city.setCountry(country.getValue().toString());
                    }
                    if (id.exists()){
                        city.setId(Integer.parseInt( id.getValue().toString()));
                    }
                    if (latitude.exists()){
                        city.setLatitude(Double.parseDouble( latitude.getValue().toString()));
                    }
                    if (longitude.exists()){
                        city.setLongitude(Double.parseDouble( longitude.getValue().toString()));;
                    }
                    citiesFromFirebase.add(city);


                }
                Utils.showObjectLog("Firebase",citiesFromFirebase);
                citiesFromFireBaseListener.onCitiesWeatherFromFirebase(citiesFromFirebase);
                //
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void deleteCityFromFireBase(String nameCity){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final Query applesQuery = databaseReference.child("city").child("weather")
                .orderByChild("name")
                .equalTo(nameCity);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sendCityToFireBase(CityDataModel weatherDataModelMyLocation){
        long timeStamp =  System.currentTimeMillis();
        String ts = String.valueOf(timeStamp);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("city").child("weather").child(ts).setValue(weatherDataModelMyLocation);
        Utils.showObjectLog("Firebase",weatherDataModelMyLocation);
    }


    public interface GetCitiesFromFireBase {
        void onCitiesWeatherFromFirebase(ArrayList< CityDataModel> cityDataModel);
    }


}

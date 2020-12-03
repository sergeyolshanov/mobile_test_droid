package com.demo.mobile_test_droid.api;

import com.demo.mobile_test_droid.pojo.Contacts;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface ApiService {
    @GET("generated-01.json")
    Observable<List<Contacts>> getContacts1();

    @GET("generated-02.json")
    Observable<List<Contacts>> getContacts2();

    @GET("generated-03.json")
    Observable<List<Contacts>> getContacts3();

    /*@GET("generated-01.json")
    Single<List<Contacts>> getContacts(@Query("name") String name, @Query("search") String query);*/

}

package com.nuv.retrofitapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DefaultColorFragment extends Fragment {
    Call<ArrayList<ColorApiResponse>> call;
    private ArrayList<ColorApiResponse> colorApiResponses;
    TextView test;
    ArrayList<String> colors= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_default_color, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.COLOR_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
         test=view.findViewById(R.id.tv_test);

        call=apiService.getColorResponse();
        call.enqueue(new Callback<ArrayList<ColorApiResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<ColorApiResponse>> call, @NonNull Response<ArrayList<ColorApiResponse>> response) {
                assert response.body() != null;
                for(int i =0;i<=5;i++)
                {
                    for(int j=0;j<=4;j++)
                    {

                        colors.add(response.body().get(i).getValues().get(j).getColor());
                    }
                }
                Toast.makeText(view.getContext(),response.body().get(0).getValues().get(0).getColor(),Toast.LENGTH_SHORT).show();
              test.setText(colors.toString());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<ColorApiResponse>> call, @NonNull Throwable t) {
                Toast.makeText(view.getContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
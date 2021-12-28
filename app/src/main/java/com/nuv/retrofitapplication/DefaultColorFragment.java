package com.nuv.retrofitapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DefaultColorFragment extends Fragment implements ColorRecyclerViewAdapter.OnColorSelect {
    Call<ArrayList<ColorApiResponse>> call;

    ArrayList<String> colors= new ArrayList<>();
    RecyclerView rvColors;
    Button next;
    ColorRecyclerViewAdapter colorRecyclerViewAdapter;

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

         rvColors=view.findViewById(R.id.rv_colors);
         next=view.findViewById(R.id.btn_next);

       //checkButton();

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
                colorRecyclerViewAdapter = new ColorRecyclerViewAdapter(colors,DefaultColorFragment.this::colorSelected);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(),3);
                rvColors.setLayoutManager(mLayoutManager);
                rvColors.setAdapter(colorRecyclerViewAdapter);
                colorRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<ColorApiResponse>> call, @NonNull Throwable t) {
                Toast.makeText(view.getContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void checkButton() {
        next.setEnabled(true);
    }

    @Override
    public void colorSelected(ArrayList<String> selectedIndex) {
        if(selectedIndex.size()==3){
         checkButton();
         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Bundle bundle = new Bundle();
                  bundle.putStringArrayList(Constants.COLOR_SELECTED_INDEX,selectedIndex);
                  bundle.putStringArrayList(Constants.COLOR_ARRAYLIST,colors);
                 ColorChangeFragment homepage = new ColorChangeFragment();
                 homepage.setArguments(bundle);
                 FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction transaction =fragmentManager.beginTransaction();
                 transaction.replace(R.id.frame, homepage);
                 transaction.commit();
             }
         });
    }
    else {
        next.setEnabled(false);
        }
    }


}
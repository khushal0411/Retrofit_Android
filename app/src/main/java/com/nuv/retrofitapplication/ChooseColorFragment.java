package com.nuv.retrofitapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nuv.retrofitapplication.adapters.ColorRecyclerViewAdapter;
import com.nuv.retrofitapplication.constant.Constants;
import com.nuv.retrofitapplication.databinding.FragmentChooseColorBinding;
import com.nuv.retrofitapplication.model.ColorApiResponse;
import com.nuv.retrofitapplication.network.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ChooseColorFragment extends Fragment implements ColorRecyclerViewAdapter.OnColorSelect {
    Call<ArrayList<ColorApiResponse>> colorResponseCall;

    ArrayList<String> colors= new ArrayList<>();
    ColorRecyclerViewAdapter colorRecyclerViewAdapter;
    FragmentChooseColorBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_choose_color,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.COLOR_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        colorResponseCall =apiService.getColorResponse();
        colorResponseCall.enqueue(new Callback<ArrayList<ColorApiResponse>>() {
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
                colorRecyclerViewAdapter = new ColorRecyclerViewAdapter(colors, ChooseColorFragment.this::colorSelected);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(view.getContext(),3);
                binding.rvColors.setLayoutManager(mLayoutManager);
                binding.rvColors.setAdapter(colorRecyclerViewAdapter);
                colorRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<ColorApiResponse>> call, @NonNull Throwable t) {
                Toast.makeText(view.getContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkButton() {
        binding.btnNext.setEnabled(true);
    }

    @Override
    public void colorSelected(ArrayList<String> selectedIndex) {
        if(selectedIndex.size()==3){
         checkButton();
         binding.btnNext.setOnClickListener(v -> {
             Bundle bundle = new Bundle();
             bundle.putStringArrayList(Constants.COLOR_SELECTED_INDEX,selectedIndex);
             bundle.putStringArrayList(Constants.COLOR_ARRAYLIST,colors);
             ColorChangeFragment homepage = new ColorChangeFragment();
             homepage.setArguments(bundle);
             FragmentManager fragmentManager = getFragmentManager();
             assert fragmentManager != null;
             FragmentTransaction transaction =fragmentManager.beginTransaction();
             transaction.replace(R.id.fl_home_screen, homepage).addToBackStack(null);
             transaction.commit();
         });
    }
    else {
        binding.btnNext.setEnabled(false);
        }
    }


}
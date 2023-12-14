package com.example.lab13;

import static com.example.lab13.MainActivity.RON_SWANSON_QUOTES_API_URL;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lab13.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new MainActivity.instance.FetchQuoteTask()
                //MainActivity.FetchQuoteTask().execute(RON_SWANSON_QUOTES_API_URL);
                new FetchQuoteTask(getView()).execute(RON_SWANSON_QUOTES_API_URL);
            }
        });
        new FetchQuoteTask(getView()).execute(RON_SWANSON_QUOTES_API_URL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
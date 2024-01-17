package com.example.cs_2340_student_scheduler_app.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cs_2340_student_scheduler_app.databinding.EditFragmentBinding;

public class EditFragment extends Fragment {

    private EditFragmentBinding binding;

    private EditText courseName;
    private EditText instructName;
    private EditText timeText;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = EditFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseName = binding.editTextClassName;
        instructName = binding.editTextInstructorName;
        timeText = binding.editTextTime;


        binding.submitButtEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String courseNameStr = courseName.getText().toString();
                String instructNameStr = instructName.getText().toString();
                String timeTextStr = timeText.getText().toString();
                courseName.setText(courseNameStr);
                instructName.setText(instructNameStr);
                timeText.setText(timeTextStr);

                NavController navController = NavHostFragment.findNavController(EditFragment.this);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("courseEdit", courseNameStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("timeEdit", timeTextStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("instructorEdit", instructNameStr);
                navController.popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.example.cs_2340_student_scheduler_app.ui.classes;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cs_2340_student_scheduler_app.databinding.FragmentClassesMenuBinding;
import com.example.cs_2340_student_scheduler_app.ui.assignments.Assignment;
import com.example.cs_2340_student_scheduler_app.ui.assignments.AssignmentMenuFragmentArgs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ClassesMenuFragment extends Fragment {

    private FragmentClassesMenuBinding binding;
    private EditText courseName;
    private EditText instructName;
    private EditText timeText;
    private EditText daysText;
    private EditText sectionText;
    private EditText locationText;
    private EditText roomText;

    private ArrayList<Classes> classList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassesMenuBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int index = ClassesMenuFragmentArgs.fromBundle(getArguments()).getIndex();
        loadData();
        courseName = binding.editTextClassName;
        instructName = binding.editTextInstructorName;
        timeText = binding.editTextTime;
        daysText = binding.editTextDays;
        sectionText = binding.editTextSection;
        locationText = binding.editTextLocation;
        roomText = binding.editTextRoom;

        timeText.setText(classList.get(index).getTime());
        instructName.setText(classList.get(index).getInstructor());
        courseName.setText(classList.get(index).getCourseName());
        daysText.setText(classList.get(index).getDays());
        sectionText.setText(classList.get(index).getSection());
        locationText.setText(classList.get(index).getLocation());
        roomText.setText(classList.get(index).getRoomNumber());


        binding.submitButt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String courseNameStr = courseName.getText().toString();
                String instructNameStr = instructName.getText().toString();
                String timeTextStr = timeText.getText().toString();
                String daysTextStr = daysText.getText().toString();
                String sectionTextStr = sectionText.getText().toString();
                String locationTextStr = locationText.getText().toString();
                String roomTextStr = roomText.getText().toString();

                NavController navController = NavHostFragment.findNavController(ClassesMenuFragment.this);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("courseEdit", courseNameStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("timeEdit", timeTextStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("instructorEdit", instructNameStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("daysEdit", daysTextStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("sectionEdit", sectionTextStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("locationEdit", locationTextStr);
                navController.getPreviousBackStackEntry().getSavedStateHandle().set("roomEdit", roomTextStr);
                navController.popBackStack();
            }
        });
    }

    private void loadData() {
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("courses", null);
        Type type = new TypeToken<ArrayList<Classes>>() {}.getType();
        classList = gson.fromJson(json, type);
        if (classList == null) {
            classList = new ArrayList<>();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
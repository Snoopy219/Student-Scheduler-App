package com.example.cs_2340_student_scheduler_app.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cs_2340_student_scheduler_app.MainActivity;
import com.example.cs_2340_student_scheduler_app.User;
import com.example.cs_2340_student_scheduler_app.UserDao;
import com.example.cs_2340_student_scheduler_app.databinding.FragmentClassesBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs_2340_student_scheduler_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ClassesFragment extends Fragment {
    private FloatingActionButton buttonAdd;
    private ArrayList<Classes> classList = new ArrayList<>();

    private ArrayList<Integer> index = new ArrayList<>();


    private FragmentClassesBinding binding;

    private ClassAdapter classAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClassesViewModel classesViewModel =
                new ViewModelProvider(this).get(ClassesViewModel.class);

        index.add(0);
        loadDB();
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView courseCards =root.findViewById(R.id.idClass);

        classAdapter = new ClassAdapter(getContext(), classList, this, index);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        courseCards.setLayoutManager(linearLayoutManager);
        courseCards.setAdapter(classAdapter);


        handleChanges();
        handleAddButton(root);
        return root;

    }

    private void handleAddButton(View root) {
        buttonAdd = root.findViewById(R.id.buttAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                classList.add(new Classes("default", "default", "default", "monday", "default", "default", "default"));
                loadDB();
                index.set(0, classList.size());
                ClassesFragmentDirections.ActionNavigationDashboardToNavigationAddClass action = ClassesFragmentDirections.actionNavigationDashboardToNavigationAddClass(index.get(0));
                NavHostFragment.findNavController(ClassesFragment.this).navigate(action);
                updateDB();
            }
        });
    }

    private void handleChanges() {
        NavController navController = NavHostFragment.findNavController(this);

        navController.getCurrentBackStackEntry().getSavedStateHandle().getLiveData("locationEdit").observe(getViewLifecycleOwner(), new Observer() {

            @Override
            public void onChanged(Object o) {
                updateDB();
                classAdapter.notifyDataSetChanged();
            }
        });
    }

    public void updateDB() {
        UserDao userDao = MainActivity.db.userDao();
        User user = userDao.getUser(MainActivity.currUser);
        Gson gson = new Gson();
        user.classes = gson.toJson(classList);
        userDao.updateUsers(user);
    }

    public void loadDB() {
        UserDao userDao = MainActivity.db.userDao();
        User user = userDao.getUser(MainActivity.currUser);
        Gson gson = new Gson();
        String json = user.classes;
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
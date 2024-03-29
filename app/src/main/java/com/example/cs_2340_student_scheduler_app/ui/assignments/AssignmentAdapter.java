package com.example.cs_2340_student_scheduler_app.ui.assignments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs_2340_student_scheduler_app.MainActivity;
import com.example.cs_2340_student_scheduler_app.ManipulateData;
import com.example.cs_2340_student_scheduler_app.R;
import com.example.cs_2340_student_scheduler_app.User;
import com.example.cs_2340_student_scheduler_app.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder>{
    private final Context context;
    private ArrayList<Assignment> assignmentList;
    private Fragment from;
    private boolean deletingComplete;

    private ArrayList<Integer> index;

    public AssignmentAdapter(Context context, ArrayList<Assignment> assignmentList, Fragment from, ArrayList<Integer> index, boolean deletingComplete) {
        this.index = index;
        this.from = from;
        this.context = context;
        this.assignmentList = assignmentList;
        this.deletingComplete = deletingComplete;
    }

    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_card, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.ViewHolder holder, int position) {
        Assignment model = assignmentList.get(position);
        holder.title.setText(model.getTitle());
        holder.dueDate.setText(model.getDueDate());
        holder.associatedClass.setText(model.getClassName());
        holder.completedSwitch.setChecked(model.isCompleted());

    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView dueDate;
        private final TextView associatedClass;
        private final SwitchMaterial completedSwitch;

        private FloatingActionButton deleteButt;
        private Button editButt;
        private AssignmentAdapter adapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.assignTitle);
            dueDate = itemView.findViewById(R.id.dueDate);
            associatedClass = itemView.findViewById(R.id.associatedClass);
            deleteButt = itemView.findViewById(R.id.deleteBut);
            editButt = itemView.findViewById(R.id.editButt);
            completedSwitch = itemView.findViewById(R.id.completedSwitch);
            final boolean[] confirmed = {false};

            deleteButt.setOnClickListener(view -> {
                if (!confirmed[0]) {
                    confirmed[0] = true;
                    deleteButt.setImageResource(R.drawable.ic_stat_name);
                } else {
                    adapter.assignmentList.remove(getAdapterPosition());
                    adapter.updateDB();
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });

            editButt.setOnClickListener(view -> {
                adapter.index.set(0, getAdapterPosition());
                int indexPar = adapter.index.get(0);
                AssignmentsFragmentDirections.ActionNavigationNotificationsToNavigationAssignmentMenuFragment action = AssignmentsFragmentDirections.actionNavigationNotificationsToNavigationAssignmentMenuFragment(indexPar);
                NavHostFragment.findNavController(adapter.from).navigate((NavDirections) action);
            });

            completedSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                adapter.assignmentList.get(getAdapterPosition()).setCompleted(isChecked);
                if (adapter.deletingComplete) {
                    adapter.assignmentList.remove(getAdapterPosition());
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
                adapter.updateDB();
            });
        }

        public ViewHolder linkAdapter(AssignmentAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void updateDB() {
        UserDao userDao = MainActivity.db.userDao();
        User user = userDao.getUser(MainActivity.currUser);
        Gson gson = new Gson();
        user.assignments = gson.toJson(assignmentList);
        userDao.updateUsers(user);
    }
}

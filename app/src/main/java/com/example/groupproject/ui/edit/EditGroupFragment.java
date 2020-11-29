package com.example.groupproject.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.groupproject.R;
import com.example.groupproject.Task;
import com.example.groupproject.ui.create.AddTaskFragment;

import java.util.HashMap;
import java.util.Map;

public class EditGroupFragment extends Fragment {
    String group_id, group_name, group_description;
    EditText name, description;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_group, container, false);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle data=getArguments();
        group_id=data.getString("group_id");
        group_description=data.getString("group_description");
        group_name=data.getString("group_name");
        name=(EditText)view.findViewById(R.id.editGroupName);
        description=(EditText)view.findViewById(R.id.editDescription);
        name.setText(group_name);
        description.setText(group_description);
        view.findViewById(R.id.saveGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue= Volley.newRequestQueue(view.getContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/group.php",
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                getActivity().onBackPressed();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", group_id);
                        params.put("name", name.getText().toString());
                        params.put("description", description.getText().toString());
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        view.findViewById(R.id.deleteGroup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue= Volley.newRequestQueue(view.getContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/group.php",
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                getActivity().getSupportFragmentManager().popBackStack();
                                getActivity().onBackPressed();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id", group_id);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        view.findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment f= new AddTaskFragment();
                Bundle data = new Bundle();
                data.putString("group_id", group_id);
                f.setArguments(data);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, f).addToBackStack(null).commit();
            }
        });

    }
}
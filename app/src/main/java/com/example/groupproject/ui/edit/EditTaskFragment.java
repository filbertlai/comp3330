package com.example.groupproject.ui.edit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class EditTaskFragment extends Fragment {
    String task_id, task_name, task_no;
    EditText name, order;
    Button save, delete;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_task, container, false);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle data=getArguments();
        task_id=data.getString("task_id");
        task_name=data.getString("task_name");
        task_no=data.getString("task_no");
        name=(EditText)view.findViewById(R.id.editTaskName);
        order=(EditText)view.findViewById(R.id.editOrder);
        name.setText(task_name);
        order.setText(task_no);
        save=(Button)view.findViewById(R.id.saveTask);
        delete=(Button)view.findViewById(R.id.deleteTask);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue= Volley.newRequestQueue(view.getContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/task.php",
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
                        params.put("id", task_id);
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue= Volley.newRequestQueue(view.getContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, "https://i.cs.hku.hk/~khchan4/task.php",
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
                        params.put("id", task_id);
                        params.put("name", name.getText().toString());
                        params.put("task_no", order.getText().toString());
                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
}

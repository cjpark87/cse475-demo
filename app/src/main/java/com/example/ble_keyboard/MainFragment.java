package com.example.ble_keyboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView) view.findViewById(R.id.list_inputs);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                //Post post = dataSnapshot.getValue(Post.class);
                // ..
                adapter.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Log.d("New Data", ds.getKey() + "," + ds.getChildrenCount() + "," + ds.child("time").getValue() + ", " +  ds.child("value").getValue());
                    for (DataSnapshot ds1: ds.getChildren()) {
                        Log.d("New Data", ds1.getKey() + "," + ds1.getChildrenCount() + "," + ds1.child("time").getValue() + ", " +  ds1.child("value").getValue());
                        adapter.add(ds1.child("value").getValue() + ", " + ds1.child("time").getValue());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Error", "loadPost:onCancelled", databaseError.toException());
            }
        };
        database.addValueEventListener(postListener);

        view.findViewById(R.id.button_fetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("inputs").removeValue();

            }
        });
    }

    public void updateDeviceTextView (String device) {
        TextView textview = getView().findViewById(R.id.textView2);
        textview.setText(device);
    }
}
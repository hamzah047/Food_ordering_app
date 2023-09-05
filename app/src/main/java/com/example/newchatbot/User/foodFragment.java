package com.example.newchatbot.User;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.newchatbot.CustomerDashboardActivity;
import com.example.newchatbot.R;
import com.example.newchatbot.UserFoodAdapter;
import com.example.newchatbot.model.food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class foodFragment extends Fragment {

    Button button;
    SearchView search;
    GridView coursesGV;
    ArrayList<food> dataModalArrayList=new ArrayList<>();
    FirebaseFirestore db;
    String searchRes="";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        search=getView().findViewById(R.id.searchView);
        Log.d("datahre1","here");
        coursesGV = getView().findViewById(R.id.idGVCourses);
        loaddata();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                searchRes=newText;
                loaddata();
                //Toast.makeText(getContext(), newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });



    }
private void loaddata(){
    dataModalArrayList.clear();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dishes");
    Log.d("datahre2","here");
    reference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot datas: dataSnapshot.getChildren()){
                String name=datas.child("Name").getValue().toString();
                Boolean res=name.contains(searchRes);
                String name2=datas.child("Price").getValue().toString();
                Boolean res2=name2.contains(searchRes);
                Log.d("datafoudn","result 1:"+res.toString()+" result 2:"+res2.toString());
                Log.d("containdata",res.toString()+" name");
                if(res){
                    food d=new food();

                    d.setDesc(datas.child("Desc").getValue().toString());
                    d.setName(datas.child("Name").getValue().toString());
                    d.setPrice(datas.child("Price").getValue().toString());
                    d.setEmail(datas.child("email").getValue().toString());
                    d.setImage(datas.child("image").getValue(String.class));
                    Log.d("datahere",datas.getKey());
                    d.setFoodID(datas.getKey());
                    dataModalArrayList.add(d);
                }
                else if(res2){
                    food d=new food();

                    d.setDesc(datas.child("Desc").getValue().toString());
                    d.setName(datas.child("Name").getValue().toString());
                    d.setPrice(datas.child("Price").getValue().toString());
                    d.setEmail(datas.child("email").getValue().toString());
                    d.setImage(datas.child("image").getValue(String.class));
                    Log.d("datahere",datas.getKey());
                    d.setFoodID(datas.getKey());
                    dataModalArrayList.add(d);
                }

                //Log.d("datahere","here");
                //Toast.makeText(checkDrivers.this, familyname, Toast.LENGTH_SHORT).show();
            }
            Log.d("datachecking",String.valueOf(dataModalArrayList.size()));
            Log.d("datafound here",String.valueOf(dataModalArrayList.size()));
            UserFoodAdapter adapter = new UserFoodAdapter(getContext(), dataModalArrayList);

            // after passing this array list
            // to our adapter class we are setting
            // our adapter to our list view.
            coursesGV.setAdapter(adapter);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false);
    }
}
package com.example.appplaces.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.appplaces.entity.Place;
import com.example.appplaces.entity.User;
import com.example.appplaces.view.home.MainInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainModel implements MainInterface.Model {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ArrayList<Place> lisPlaces;
    private Place place;
    private User user;
    int count = 0, countPlace;


    public MainModel() {
    }


    public void getPlaces(MainInterface.TaskListener listener) {

        db.collection("places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    countPlace = task.getResult().size();
                    lisPlaces = new ArrayList<>();
                    for (int i = 0; i < task.getResult().size(); i++) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(i);
                        place = new Place();
                        place.setId(document.getId());
                        place.setDescription(document.getString("description"));
                        place.setCreatedAt(document.getTimestamp("createdAt").toDate());
                        place.setArrayFotos((ArrayList<String>) document.get("fotos"));
                        user = new User();
                        user.setId(document.getString("user_id"));
                        place.setUser(user);
                        lisPlaces.add(place);
                        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> taskUser) {
                                count++;
                                for (int i = 0; i < taskUser.getResult().size(); i++) {
                                    DocumentSnapshot document = taskUser.getResult().getDocuments().get(i);

                                    for (int o = 0; o < lisPlaces.size(); o++) {
                                        String user1 = lisPlaces.get(o).getUser().getId();
                                        String user2 =document.getId();
                                        if ( user1.equals(user2)){
                                            user = new User();
                                            user.setId(document.getId());
                                            user.setEmail(document.getString("email"));
                                            user.setName(document.getString("name"));
                                            lisPlaces.get(o).setUser(user);
                                        }
                                    }
                                }
                                if (count == countPlace)
                                    listener.loadPlaces(lisPlaces);
                            }
                        });
                    }
                    listener.loadPlaces(lisPlaces);
                }
            }
        });

    }

    @Override
    public void saveLocation(String latitud, String longitud) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("latitud", latitud);
        objectMap.put("longitud", longitud);
        db.collection("users").document(firebaseAuth.getCurrentUser().getUid())
                .update(objectMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("MENSAJE","Se actualizo coordenadas "+latitud+" "+longitud);
                    }
                });
    }
}
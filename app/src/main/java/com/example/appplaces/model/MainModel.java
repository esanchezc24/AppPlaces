package com.example.appplaces.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.appplaces.entity.Place;
import com.example.appplaces.entity.User;
import com.example.appplaces.view.home.MainInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import org.w3c.dom.Document;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MainModel implements MainInterface.Model {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Place> lisPlaces;
    private Place place;
    private User user;
    int count=0, countPlace;


    public MainModel() {
    }


    public void getPlaces(MainInterface.TaskListener listener) {

        db.collection("places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    countPlace=task.getResult().size();
                    lisPlaces = new ArrayList<>();
                    for (int i = 0; i < task.getResult().size(); i++) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(i);
                        place = new Place();
                        place.setId(document.getId());
                        place.setDescription(document.getString("description"));
                        place.setCreatedAt(document.getTimestamp("createdAt").toDate());
                        place.setArrayFotos((ArrayList<String>) document.get("fotos"));
                        lisPlaces.add(place); //se agrega el place al array
                        db.collection("users").document(document.getString("user_id")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                count++;
                                DocumentSnapshot documentUser = task.getResult();
                                user = new User();
                                user.setId(documentUser.getId());
                                user.setEmail(documentUser.getString("email"));
                                user.setName(documentUser.getString("name"));
                                lisPlaces.get(count-1).setUser(user);
//                                place.setUser(user); //se agrega el user a place
//                                lisPlaces.add(place); //se agrega el place al array
                                if (count == countPlace)
                                    listener.loadPlaces(lisPlaces); //devuelve la lista.
                            }
                        });
                    }
                    listener.loadPlaces(lisPlaces);
                }
            }
        });

    }
}

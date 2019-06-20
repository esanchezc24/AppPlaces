package com.example.appplaces.model;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.appplaces.entity.Place;
import com.example.appplaces.entity.User;
import com.example.appplaces.view.home.MainInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class MainModel implements MainInterface.Model {

    private MainInterface.TaskListener taskListener;
    private MainInterface.View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private ArrayList<Place> lisPlaces = new ArrayList<>();
    private Place place;


    public MainModel(MainInterface.TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    public void getPlaces() {
        db.collection("places").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().size(); i++) {
                        place = new Place();
                        place.setId(task.getResult().getDocuments().get(1).getId());
                        place.setDescription(task.getResult().getDocuments().get(i).getString("description"));
                        place.setCreatedAt(task.getResult().getDocuments().get(i).getTimestamp("createdAt").toDate());
                        db.collection("users").document(task.getResult().getDocuments().get(i).getString("user_id")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        User user = new User();
                                        user.setEmail(document.getString("email"));
                                        user.setName(document.getString("name"));
                                        place.setUser(user);
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                        lisPlaces.add(place);
                    }
                    taskListener.loadPlaces(lisPlaces);

                }
            }
        });

    }


}

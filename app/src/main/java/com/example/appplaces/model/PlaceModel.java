package com.example.appplaces.model;

import android.support.annotation.NonNull;

import com.example.appplaces.entity.Place;
import com.example.appplaces.view.login.LoginInterface;
import com.example.appplaces.view.place.PlaceInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlaceModel implements PlaceInterface.Model {
    private PlaceInterface.TaskListener taskListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public PlaceModel(PlaceInterface.TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    public void doSave(Place place) {
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("description", place.getDescription());
        objectMap.put("user_id",firebaseAuth.getCurrentUser().getUid());
        objectMap.put("createdAt", new Date());
        db.collection("places")
                .add(objectMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        taskListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        taskListener.onError(e.getMessage());
                    }
                });
    }

}

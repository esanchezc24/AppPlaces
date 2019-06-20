package com.example.appplaces.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.appplaces.entity.Place;
import com.example.appplaces.view.place.PlaceInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PlaceModel implements PlaceInterface.Model {
    private PlaceInterface.TaskListener taskListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private ArrayList<String> listUrl = new ArrayList<>();

    public PlaceModel(PlaceInterface.TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    public void doSave(Place place, ArrayList<Uri> fotos) {

        for (int i = 0; i < fotos.size(); i++) {
            String name = fotos.get(i).getLastPathSegment();
            StorageReference filePath = storageRef.child("places_img").child(name);
            filePath.putFile(fotos.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {
                            String downloadUri = uri.toString();
                            listUrl.add(downloadUri);
                            if (fotos.size() == listUrl.size()){
                                savePlace(place);
                            }
                        }
                    });
                }
            });

        }

    }

    public void savePlace(Place place){
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("description", place.getDescription());
        objectMap.put("user_id",firebaseAuth.getCurrentUser().getUid());
        objectMap.put("createdAt", new Date());
        db.collection("places")
                .add(objectMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        for (int i = 0; i < listUrl.size(); i++) {
                            Map<String, Object> image = new HashMap<>();
                            image.put("url", listUrl.get(i));
                            documentReference.collection("fotos")
                                    .add(image).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.i("MENSAJE","ID "+documentReference.getId());
                                }
                            });
                        }
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

package com.example.appplaces.view.place;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.appplaces.entity.Place;
import com.example.appplaces.R;
import com.example.appplaces.presenter.PlacePresenter;
import com.example.appplaces.view.home.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends Activity implements PlaceInterface.View {

    private EditText edtDecription;
    private Button btnFotos, btnSave;
    private MaterialDialog dialog;
    private PlaceInterface.Presenter presenter;
    private ImageView imgFotos1;
    private ImageView imgFotos2;
    private Place place;

    private ArrayList<Uri> imagePathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        edtDecription = findViewById(R.id.edtDescription);
        imgFotos1 = findViewById(R.id.imgFotos1);
        imgFotos2 = findViewById(R.id.imgFotos2);
        btnFotos = findViewById(R.id.btnFotos);
        btnSave = findViewById(R.id.btnSave);
        presenter = new PlacePresenter(this);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere, por favor...")
                .progress(true, 0)
                .cancelable(false);
        dialog = builder.build();

        btnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSave();
            }
        });
    }


    private void setInputs(boolean enable) {
        edtDecription.setEnabled(enable);
        btnFotos.setEnabled(enable);
        btnSave.setEnabled(enable);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                imagePathList = new ArrayList<Uri>();
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    int count = mClipData.getItemCount();
                    if (count > 2) {
                        Toast.makeText(this, "Solo se puede subir dos imagenes", Toast.LENGTH_SHORT).show();
                        count = 2;
                    }
                    for (int i = 0; i < count; i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        imagePathList.add(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            if (i == 1)
                                imgFotos1.setImageBitmap(bitmap);
                            else
                                imgFotos2.setImageBitmap(bitmap);

                            imgFotos1.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                } else if (data.getData() != null) {
                    Uri contentURI = data.getData();
                    imagePathList.add(contentURI);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                        imgFotos1.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void handleSave() {
        if (!isValidDescription()) {
            Toast.makeText(this, "Descripción Incorrecta", Toast.LENGTH_SHORT).show();
        } else if (!isValidFotos()) {
            Toast.makeText(this, "Suba alguna foto", Toast.LENGTH_SHORT).show();
        } else {
            place = new Place();
            place.setDescription(edtDecription.getText().toString().trim());

            presenter.toSave(place,imagePathList);
        }

    }

    @Override
    public boolean isValidDescription() {
        if (TextUtils.isEmpty(edtDecription.getText().toString())) {
            edtDecription.setError("Nombre Incorrecto");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isValidFotos() {

        if (imagePathList == null)
            return false;
        else
            return true;
    }

    @Override
    public void onSave() {
        Toast.makeText(this, "SE REGISTRO CON EXITO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}

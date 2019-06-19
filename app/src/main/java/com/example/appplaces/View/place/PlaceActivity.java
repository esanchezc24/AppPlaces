package com.example.appplaces.View.place;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.appplaces.Data.Entity.Place;
import com.example.appplaces.R;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity implements PlaceInterface.View {

    private EditText edtDecription;
    private Button btnFotos, btnSave;
    private MaterialDialog dialog;
    private PlaceInterface.Presenter presenter;
    private ImageView imgFotos;
    private Place place;
    int PICK_IMAGE_REQUEST = 111;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        edtDecription = findViewById(R.id.edtDescription);
        imgFotos = findViewById(R.id.imgFotos);
        btnFotos = findViewById(R.id.btnFotos);
        btnSave = findViewById(R.id.btnSave);
//        presenter = new PlaceInterface(this);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere, por favor...")
                .progress(true, 0)
                .cancelable(false);
        dialog = builder.build();

        btnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Seleccione Imagen"), PICK_IMAGE_REQUEST);
            }
        });
    }

    private void setInputs(boolean enable) {
        edtDecription.setEnabled(enable);
        btnFotos.setEnabled(enable);
        btnSave.setEnabled(enable);
    }

    protected void onActivityResult(int requestCode, int resulCode, Intent data) {
        super.onActivityResult(requestCode, resulCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resulCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgFotos.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
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
        if (isValidDescription() && isValidFotos()) {
            place = new Place();
            place.setDescription(edtDecription.getText().toString().trim());
//            place.setUser();
        } else {
            Toast.makeText(this, "No se puede guardar", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean isValidDescription() {
        if (TextUtils.isEmpty(edtDecription.getText().toString())) {
            Toast.makeText(this, "Descripción Incorrecto", Toast.LENGTH_SHORT).show();
            edtDecription.setError("Nombre Incorrecto");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isValidFotos() {
        if (filePath != null)
            return true;
        else
            return false;
    }

    @Override
    public void onSave() {
        Toast.makeText(this, "SE REGISTRO CON EXITO", Toast.LENGTH_SHORT).show();
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

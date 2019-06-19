package com.example.appplaces.view.place;

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
import com.example.appplaces.entity.Place;
import com.example.appplaces.R;
import com.example.appplaces.presenter.PlacePresenter;

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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Seleccione Imagen"), PICK_IMAGE_REQUEST);
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
        if (!isValidDescription()) {
            Toast.makeText(this, "Descripción Incorrecta", Toast.LENGTH_SHORT).show();
        } else if (!isValidFotos()) {
            Toast.makeText(this, "Suba alguna foto", Toast.LENGTH_SHORT).show();
        } else {
            place = new Place();
            place.setDescription(edtDecription.getText().toString().trim());
            presenter.toSave(place);
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
        if (filePath == null) {
            return false;
        } else
            return true;
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

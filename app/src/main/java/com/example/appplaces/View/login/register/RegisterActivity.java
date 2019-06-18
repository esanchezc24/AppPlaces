package com.example.appplaces.View.login.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.appplaces.Presenter.LoginPresenter;
import com.example.appplaces.Presenter.RegisterPresenter;
import com.example.appplaces.R;
import com.example.appplaces.View.login.LoginActivity;
import com.example.appplaces.View.login.LoginInterface;

public class RegisterActivity extends AppCompatActivity implements RegisterInterface.View{
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnRegister;
    private MaterialDialog dialog;
    private RegisterInterface.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        presenter = new RegisterPresenter(this);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere, por favor...")
                .progress(true, 0)
                .cancelable(false);
        dialog = builder.build();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });
    }

    private void setInputs(boolean enable) {
        edtName.setEnabled(enable);
        edtEmail.setEnabled(enable);
        edtPassword.setEnabled(enable);
        btnRegister.setEnabled(enable);
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
    public void handleRegister() {

        if (!isValidName())
            Toast.makeText(this, "Nombre Incorrecto", Toast.LENGTH_SHORT).show();
        else if (!isValidEmail())
            Toast.makeText(this, "Email Incorrecto", Toast.LENGTH_SHORT).show();
        else if (!isValidPassword())
            Toast.makeText(this, "Password Incorrecto", Toast.LENGTH_SHORT).show();
        else
            presenter.toRegister(edtName.getText().toString().trim(), edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
    }

    @Override
    public boolean isValidName() {
        if (TextUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(this, "Nombre Incorrecto", Toast.LENGTH_SHORT).show();
            edtName.setError("Nombre Incorrecto");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches();
    }

    @Override
    public boolean isValidPassword() {
        if (TextUtils.isEmpty(edtPassword.getText().toString()) || edtPassword.getText().toString().length() < 6) {
            Toast.makeText(this, "Password Incorrecto, Debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            edtPassword.setError("Password Incorrecto");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRegister() {
        Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}

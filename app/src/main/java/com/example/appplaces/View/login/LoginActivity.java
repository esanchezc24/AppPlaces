package com.example.appplaces.View.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.appplaces.View.home.MainActivity;
import com.example.appplaces.Presenter.LoginPresenter;
import com.example.appplaces.R;
import com.example.appplaces.View.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginInterface.View {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnRegister;
    private MaterialDialog dialog;
    private LoginInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        presenter = new LoginPresenter(this);
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Cargando")
                .content("Espere, por favor...")
                .progress(true, 0)
                .cancelable(false);
        dialog = builder.build();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), RegisterActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void setInputs(boolean enable) {
        edtEmail.setEnabled(enable);
        edtPassword.setEnabled(enable);
        btnLogin.setEnabled(enable);

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
    public void handleLogin() {
        if (!isValidEmail())
            Toast.makeText(this, "Email Incorrecto", Toast.LENGTH_SHORT).show();
        else if (!isValidPassword())
            Toast.makeText(this, "Password Incorrecto", Toast.LENGTH_SHORT).show();
        else
            presenter.toLogin(edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());

    }

    @Override
    public boolean isValidEmail() {
        return Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches();
    }

    @Override
    public boolean isValidPassword() {
        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            Toast.makeText(this, "Password Incorrecto", Toast.LENGTH_SHORT).show();
            edtPassword.setError("Password Incorrecto");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onLogin() {
        Toast.makeText(this, "Login EXITOSO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (this, MainActivity.class);
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

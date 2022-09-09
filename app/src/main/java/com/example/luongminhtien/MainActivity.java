package com.example.luongminhtien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.luongminhtien.database.AppDatabase;
import com.example.luongminhtien.database.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edUsername, edEmail, edDes;
    CheckBox cb;
    Button btnSeen;
    Spinner spinner;
    String gender = "Male";
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
        edDes = findViewById(R.id.edDes);
        cb = findViewById(R.id.cb);
        btnSeen = findViewById(R.id.btnSeen);
        spinner = findViewById(R.id.spGender);
        db = AppDatabase.getAppDatabase(this);
        btnSeen.setOnClickListener(this);
        initGender();
    }

    private void initGender() {
        String[] listGender = {"Male", "Female", "Unknown"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                listGender);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onItemClick: " + listGender[position]);
                gender = listGender[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSeen:
                onSeen();
                break;
            default:
                break;
        }
    }
    private void onSeen() {
        if (!validate()) {
            return;
        }
        User user = new User();
        user.username = edUsername.getText().toString();
        user.email = edEmail.getText().toString();
        user.des = edDes.getText().toString();
        user.gender = gender;
        long id = db.userDao().insertUser(user);
        if (id > 0) {
            Toast.makeText(this, "Success !!!", Toast.LENGTH_SHORT).show();
        }
        makeTotal();
    }

    private void makeTotal() {
        Toast.makeText(this, "Total : " + db.userDao().getALl().size(), Toast.LENGTH_LONG).show();
    }

    private boolean validate() {
        String mess = null;
        if (edUsername.getText().toString().trim().isEmpty()) {
            mess = "Username is empty !!!";
        } else if (edEmail.getText().toString().trim().isEmpty()) {
            mess = "Email is empty !!!";
        } else if (edDes.getText().toString().trim().isEmpty()) {
            mess = "Description is empty !!!";
        }
        if (mess != null) {
            Toast.makeText(this, mess, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
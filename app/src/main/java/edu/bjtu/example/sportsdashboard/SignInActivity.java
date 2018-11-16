package edu.bjtu.example.sportsdashboard;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class SignInActivity extends AppCompatActivity {
    private String baseURL = "http://localhost:8080/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        Button signInBt = findViewById(R.id.sign_in_btn);
        signInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifySignIn();
            }
        });

        Button signUpBt = findViewById(R.id.need_sign_up_btn);
        signUpBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void verifySignIn () {
        new Thread(new Runnable() {
            @Override
            public void run(){
                EditText username = findViewById (R.id.sign_in_userId);
                EditText password = findViewById (R.id.sign_in_pass);
                String url = baseURL + "?name=" + username.getText().toString().trim() + "&pass=" + password.getText().toString().trim();
                HttpClient httpCient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                try {
                    HttpResponse httpResponse = httpCient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity,"utf-8");
                        if (response.equals("1")) {
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
//                            username.getText().clear();
//                            password.getText().clear();
                            Looper.prepare();
                            Toast toast = Toast.makeText(SignInActivity.this,"wrong username or password",Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

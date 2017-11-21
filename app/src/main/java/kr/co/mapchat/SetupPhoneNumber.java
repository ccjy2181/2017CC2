package kr.co.mapchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetupPhoneNumber extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_phone_number);

        setupToolbar(R.id.toolbar, "반갑습니다!");
        Button button = (Button) findViewById(R.id.bt_auth);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetupPhoneNumber.this, MainActivity.class));
            }
        });
    }
}

package kr.co.mapchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SetupPhoneNumber extends BaseActivity {
    int iCurrentSelection;
    Spinner month;
    Spinner day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_phone_number);

        setupToolbar(R.id.toolbar, "반갑습니다!");
        Button button = (Button) findViewById(R.id.bt_auth);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoginData(); // 로그인 데이터를 String 4개짜리 배열에 담아서 return.
                startActivity(new Intent(SetupPhoneNumber.this, MainActivity.class));
                finish();
            }
        });
        month = (Spinner) findViewById(R.id.month);

        day = (Spinner) findViewById(R.id.day1);

        iCurrentSelection = month.getSelectedItemPosition();
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (iCurrentSelection != i) {
                    if(i == 2){
                        day = (Spinner) findViewById(R.id.day_feb);
                        day.setVisibility(View.VISIBLE);
                    }else if(i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12){
                        day = (Spinner) findViewById(R.id.day2);
                        day.setVisibility(View.VISIBLE);
                    }else{
                        day = (Spinner) findViewById(R.id.day1);
                        day.setVisibility(View.VISIBLE);
                    }
                }
                iCurrentSelection = i;
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    public String[] getLoginData(){
        String[] data = new String[4];
        EditText nickname = (EditText)findViewById(R.id.nickname);
        EditText year = (EditText)findViewById(R.id.et_year);
        month = (Spinner) findViewById(R.id.month);
        int index = month.getSelectedItemPosition();
        if(index == 2){
            day = (Spinner) findViewById(R.id.day_feb);
        }else if(index==1 || index==3 || index==5 || index==7 || index==8 || index==10 || index==12){
            day = (Spinner) findViewById(R.id.day2);
        }else{
            day = (Spinner) findViewById(R.id.day1);
        }

        data[0] = nickname.getText().toString();
        data[1] = year.getText().toString();
        data[2] = month.getSelectedItem().toString();
        data[3] = day.getSelectedItem().toString();

        return data;
    }
}

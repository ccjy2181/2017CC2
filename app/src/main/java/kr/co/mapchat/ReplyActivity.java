package kr.co.mapchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.Date;

import kr.co.mapchat.dto.AnswerDTO;
import kr.co.mapchat.dto.MessageDTO;
import kr.co.mapchat.util.fireBase.MyFirebaseConnector;
import kr.co.mapchat.util.system.ImageManager;
import kr.co.mapchat.util.system.Property;

import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;

public class ReplyActivity  extends Activity{
    String title;

    MyFirebaseConnector myFirebaseConnector;

    MessageDTO messageDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reply);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        Bundle bundle = intent.getExtras();
        messageDTO = (MessageDTO) bundle.getSerializable("messageDTO");

        title = messageDTO.getTitle();

        super.onCreate(savedInstanceState);

        ImageView img = (ImageView)findViewById(R.id.reply_map);
        TextView tv_title = (TextView) findViewById(R.id.reply_title);

        ImageManager imageManager = new ImageManager();
        img.setImageBitmap(imageManager.decodingImageData(messageDTO.getImage_string()));
        tv_title.setText(title);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


    public String getContentInput(){
        EditText content = (EditText)findViewById(R.id.reply_content);
        String content_str = content.getText().toString();
        return content_str;
    }

    public void ButtonWriteClicked(View view) {

        //DTO 새로 해서 넣어야합니당

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setUser(prefs.getString("token", ""));
        answerDTO.setComment(getContentInput());
        answerDTO.setRegdate(new Date());

        myFirebaseConnector = new MyFirebaseConnector("message");
        myFirebaseConnector.insertData(answerDTO, messageDTO.getKey()+"/answer");

        finish();
    }

    public void ButtonCancelClicked(View view) {
        finish();
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}
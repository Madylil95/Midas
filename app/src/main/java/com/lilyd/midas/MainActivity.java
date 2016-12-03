package com.lilyd.midas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    private ImageButton touchArea;
    private TextView bitText,asciiText,timeUpText,timeDownText;
    private ImageView midasPic,unknownPic,lilyPic,capnPic;

    //pressTime is current time when pressed, releaseTime is current time when released
    private long pressTime = -1l;
    private long releaseTime = 1l;

    //duration is time elapsed pressed, duration2 is time elapsed lifted
    private long duration1,duration0= -1l;

    private String asciString = "";
    private String idString = "";

    private final static String person1 = "MIDA";
    private final static String person2 = "LILY";
    private final static String person3 = "CAPN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        touchArea = (ImageButton)findViewById(R.id.touch_area_id);
        bitText = (TextView)findViewById(R.id.bstream_id);
        asciiText = (TextView)findViewById(R.id.ascii_id);
        midasPic = (ImageView)findViewById(R.id.midas_id);
        lilyPic = (ImageView)findViewById(R.id.lily_id);
        capnPic = (ImageView)findViewById(R.id.capn_id);
        unknownPic = (ImageView)findViewById(R.id.unknown_id);
        timeUpText = (TextView)findViewById(R.id.timeup_id);
        timeDownText = (TextView)findViewById(R.id.timedown_id);

        touchArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                String str = "";
                if((idString.length() == 9) && (idString.charAt(0) == '|')){
                    int charCode = Integer.parseInt(idString.substring(1),2);
                    str =  new Character((char)charCode).toString();
                    asciString += str;
                    asciiText.setText(asciString);
                    idString = "";
                }
                if(idString.length() >= 9){
                    idString = "";
                }

                if(asciString.length()>=4){
                    String lastFour = asciString.substring(asciString.length()-4);
                    switch(lastFour){
                        case person1:
                            capnPic.setVisibility(View.INVISIBLE);
                            unknownPic.setVisibility(View.INVISIBLE);
                            lilyPic.setVisibility(View.INVISIBLE);
                            midasPic.setVisibility(View.VISIBLE);
                            asciString = "";
                            break;
                        case person2:
                            capnPic.setVisibility(View.INVISIBLE);
                            unknownPic.setVisibility(View.INVISIBLE);
                            midasPic.setVisibility(View.INVISIBLE);
                            lilyPic.setVisibility(View.VISIBLE);
                            asciString = "";
                            break;
                        case person3:
                            unknownPic.setVisibility(View.INVISIBLE);
                            midasPic.setVisibility(View.INVISIBLE);
                            lilyPic.setVisibility(View.INVISIBLE);
                            capnPic.setVisibility(View.VISIBLE);
                            asciString = "";

                        default:
                            unknownPic.setVisibility(View.INVISIBLE);
                            midasPic.setVisibility(View.INVISIBLE);
                            lilyPic.setVisibility(View.INVISIBLE);
                            capnPic.setVisibility(View.INVISIBLE);
                            unknownPic.setVisibility(View.VISIBLE);
                    }
                }

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    pressTime = System.currentTimeMillis();
                    if(releaseTime != 1l){
                        duration0 = System.currentTimeMillis() - releaseTime;
                        timeUpText.setText(String.valueOf(duration0));
                        if(duration0 > 188 && duration0 < 1175)
                        {
                            String s = String.valueOf(duration0/188);
                            int z = Integer.valueOf(s);
                                for (int i = 0; i < z; i++) {
                                    idString += "0";
                                }
                        }
                    }
                    bitText.setText(idString);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    releaseTime = System.currentTimeMillis();
                    duration1 = System.currentTimeMillis() - pressTime;
                    timeDownText.setText(String.valueOf(duration1));
                    if(duration1 > 180 && duration1 < 223) {
                        idString = "|";
                    }
                    if(duration1 > 84 && duration1 < 135) {
                            idString += "1";
                    }
                    bitText.setText(idString);

                }
                return false;
            }
        });
    }




}

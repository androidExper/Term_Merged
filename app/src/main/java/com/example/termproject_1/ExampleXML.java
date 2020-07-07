package com.example.termproject_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class ExampleXML extends AppCompatActivity {
    EditText edit;
    TextView text;
    XmlPullParser xpp;

    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_x_m_l);

        edit= (EditText)findViewById(R.id.edit);
        text= (TextView)findViewById(R.id.result);

    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data=getXmlData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    String getXmlData(){
        String str= edit.getText().toString();      // EditText 에서  작성한 식품명 text
        String DESC_KOR = URLEncoder.encode(str);   // 식품명 str 을 encoding
        String serviceKey="d36886a7c1bd4011bcd6";   // serviceKey

        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/" + serviceKey + "/I2790/xml/1/999/DESC_KOR=" + DESC_KOR;
        StringBuffer buffer=new StringBuffer();

        try{
            URL url= new URL(queryUrl);                                         // String Type 의 queryUrl 을 URL 객체로 생성함
            InputStream is= url.openStream();                                   // url 위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );     // InputStream 으로부터 xml 입력 받음

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            buffer.append("파싱을 시작합니다!\n\n");
            while( eventType != XmlPullParser.END_DOCUMENT ) {
                switch( eventType ) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();                                 //태그 이름 얻어오기

                        if(tag.contains("row")) ;                           // 첫번째 검색결과
                        else if (tag.equals("NUTR_CONT3")) {
                            buffer.append("단백질 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("NUTR_CONT2")) {
                            buffer.append("탄수화물 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("NUTR_CONT1")) {
                            buffer.append("열량 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("SERVING_SIZE")) {
                            buffer.append("총내용량 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("FOOD_GROUP")) {
                            break;
                        }
                        else if (tag.equals("MAKER_NAME")) {
                            buffer.append("제조사명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("BGN_YEAR")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT9")) {
                            buffer.append("트랜스지방 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("NUTR_CONT8")) {
                            buffer.append("포화지방산 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("FOOD_CD")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT7")) {
                            buffer.append("콜레스테롤 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("NUTR_CONT6")) {
                            buffer.append("나트륨 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("NUTR_CONT5")) {
                            buffer.append("당류 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("NUTR_CONT4")) {
                            buffer.append("지방 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("DESC_KOR")) {
                            buffer.append("식품이름 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("SAMPLING_MONTH_NAME")) {
                            break;
                        }
                        else if (tag.equals("SAMPLING_REGION_NAME")) {
                            buffer.append("지역명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("SUB_REF_NAME")) {
                            break;
                        }
                        else if (tag.equals("GROUP_NAME")) {
                            buffer.append("식품군 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        else if (tag.equals("SAMPLING_REGION_CD")) {
                            break;
                        }
                        else if (tag.equals("RESEARCH_YEAR")) {
                            break;
                        }
                        else if (tag.equals("SAMPLING_MONTH_CD")) {
                            break;
                        }
                        else if (tag.equals("NUM")) {
                            break;
                        }
                        else if (tag.equals("ANIMAL_PLANT")) {
                            break;
                        }
                        break;


                    case XmlPullParser.TEXT:
                        break;


                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.contains("row")) buffer.append("\n");        // 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }
        }
        catch (Exception e){
            Log.d("error!",e.getMessage());
            e.printStackTrace();
        }

        buffer.append("파싱을 종료합니다!\n");

        return buffer.toString();                                           // StringBuffer 문자열 객체 반환
    }
}
package com.example.termproject_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<String,Void,String>{
    RecyclerView recyclerView;
    FoodListAdapter foodListAdapter;
    ArrayList<Food> foodList ;
    Activity currActivity;

    private ProgressDialog progressDialog;

    public MyAsyncTask(Activity currActivity,RecyclerView recyclerView,FoodListAdapter foodListAdapter,ArrayList<Food> foodList) {
        this.currActivity = currActivity;
        this.recyclerView = recyclerView;
        this.foodListAdapter = foodListAdapter;
        this.foodList = foodList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(currActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String ... strings) {
        String[] arranged = strings[0].split("/");
        // [0] : foodname
        // [1] : 부족한 영양소 index
        // [2] : 부족량

        if(arranged[0].equals(" "))                     // 추천식단 요청
            return this.XML_Recommendation(arranged);
        else                                            // 음식 이름 검색
            return this.XML_SearchTab(arranged);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        foodListAdapter.setItems(foodList);
        try {
            recyclerView.setAdapter(foodListAdapter);
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("test", "onPostExecute: error : " + e);
        }
        progressDialog.dismiss();
    }

    public FoodListAdapter getFoodListAdapter(){
        Log.d("test", "getFoodListAdapter: " + foodListAdapter.getItemCount());
        return this.foodListAdapter;
    }

    private String XML_SearchTab(String[] strings){
        String str= strings[0];                     // EditText 에서  작성한 식품명 text
        String DESC_KOR = URLEncoder.encode(str);   // 식품명 str 을 encoding
        String serviceKey="d36886a7c1bd4011bcd6";   // serviceKey
        String[] info = new String[15];
        info[0] = "1";
        ArrayList<Food> food_info = new ArrayList<Food>();
        // info[0]      NUM
        // info[1]      NUTR_CONT1
        // info[2]      NUTR_CONT2
        // info[3]      NUTR_CONT3
        // info[4]      NUTR_CONT4
        // info[5]      NUTR_CONT5
        // info[6]      NUTR_CONT6
        // info[7]      NUTR_CONT7
        // info[8]      NUTR_CONT8
        // info[9]      NUTR_CONT9
        // info[10]     DESC_KOR
        // info[11]     GROUP_NAME
        // info[12]     MAKER_NAME
        // info[13]     SERVING_SIZE
        // info[14]     SAMPLING_REGION_NAME

        String st = "1";
        String ed = "999";

        Log.d("test", st);
        Log.d("test", ed);

        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/" + serviceKey + "/I2790/xml/" + st + "/" + ed + "/DESC_KOR=" + DESC_KOR;
        StringBuffer buffer=new StringBuffer();
        Log.d("test", "========== XML parsing start ==========");
        int count = 0;
        try{
            URL url= new URL(queryUrl);                                         // String Type 의 queryUrl 을 URL 객체로 생성함
            InputStream is= url.openStream();                                   // url 위치로 입력스트림 연결
            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );     // InputStream 으로부터 xml 입력 받음
            String tag;
            xpp.next();
            int eventType= xpp.getEventType();
            buffer.append("파싱 시작\n\n");
            while( eventType != XmlPullParser.END_DOCUMENT) {
                switch( eventType ) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("parsing\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();                                 //태그 이름 얻어오기
                        if(tag.contains("row")) {
                            ++count;
                        }                           // 첫번째 검색결과
                        else if (tag.equals("NUTR_CONT3")) {
                            buffer.append("단백질 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            info[3] = xpp.getText();
                        }
                        else if (tag.equals("NUTR_CONT2")) {
                            buffer.append("탄수화물 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            info[2] = xpp.getText();
                        }
                        else if (tag.equals("NUTR_CONT1")) {
                            buffer.append("열량 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            info[1] = xpp.getText();
                        }
                        else if (tag.equals("SERVING_SIZE")) {
                            break;
                        }
                        else if (tag.equals("FOOD_GROUP")) {
                            break;
                        }
                        else if (tag.equals("MAKER_NAME")) {
                            break;
                        }
                        else if (tag.equals("BGN_YEAR")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT9")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT8")) {
                            break;
                        }
                        else if (tag.equals("FOOD_CD")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT7")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT6")) {
                            buffer.append("나트륨 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            info[6] = xpp.getText();
                        }
                        else if (tag.equals("NUTR_CONT5")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT4")) {
                            buffer.append("지방 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            info[4] = xpp.getText();
                        }
                        else if (tag.equals("DESC_KOR")) {
                            buffer.append("식품이름 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            info[10] = xpp.getText();
                        }
                        else if (tag.equals("SAMPLING_MONTH_NAME")) {
                            break;
                        }
                        else if (tag.equals("SAMPLING_REGION_NAME")) {
                            break;
                        }
                        else if (tag.equals("SUB_REF_NAME")) {
                            break;
                        }

                        else if (tag.equals("GROUP_NAME")) {
                            break;
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
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        if(tag.contains("row")) {
                            if(count >=1){
                                foodList.add(new Food(info[10],new String[]{info[2],info[6],info[3],info[4]},info[1]));
                                Log.d("test", "getXmlData: "+info[10]+" "+info[2]+" "+info[3]+" "+info[4]+" "+info[1]);
                            }
                            buffer.append("\n");
                        };        // 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "XML error : " + e);
        }
        Log.d("test", "getXmlData: count  : " + count);
        buffer.append("파싱 종료\n\n");

        Log.d("test","========== XML parsing stop ==========");
        return buffer.toString();
    }

    private String XML_Recommendation(String[] strings){

        String index = strings[1];          // 부족한 영양소 종류
        String deficiency = strings[2];     // 부족량

        ArrayList<Food> food_info = new ArrayList<Food>();
        String[] info = new String[15];
        info[0] = "1";

        String st = "1";
        String ed = "30";
        String serviceKey="d36886a7c1bd4011bcd6";   // serviceKey
        String queryUrl = "http://openapi.foodsafetykorea.go.kr/api/" + serviceKey + "/I2790/xml/" + st + "/" + ed;

        StringBuffer buffer=new StringBuffer();
        Log.d("test", "========== XML parsing start ==========");
        int count = 0;
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
            while( eventType != XmlPullParser.END_DOCUMENT) {
                switch( eventType ) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();                                 //태그 이름 얻어오기
                        if(tag.contains("row")) {
                            ++count;
                        }                                                   // 첫번째 검색결과
                        else if (tag.equals("NUTR_CONT3")) {
                            buffer.append("단백질 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");

                            float temp;
                            if(xpp.getText() == null || xpp.getText().isEmpty())
                                temp = 0;
                            else
                                temp = Float.parseFloat(xpp.getText());

                            if(strings[1].equals("3")) {
                                if(temp < Float.parseFloat(deficiency))
                                    break;
                            }

                            info[3] = xpp.getText();
                        }
                        else if (tag.equals("NUTR_CONT2")) {
                            buffer.append("탄수화물 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");

                            float temp;
                            if(xpp.getText() == null || xpp.getText().isEmpty())
                                temp = 0;
                            else
                                temp = Float.parseFloat(xpp.getText());

                            if(strings[1].equals("2")) {
                                if(temp < Float.parseFloat(deficiency))
                                    break;
                            }

                            info[2] = xpp.getText();
                        }
                        else if (tag.equals("NUTR_CONT1")) {
                            buffer.append("열량 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");

                            float temp;
                            if(xpp.getText() == null || xpp.getText().isEmpty())
                                temp = 0;
                            else
                                temp = Float.parseFloat(xpp.getText());

                            if(strings[1].equals("1")) {
                                if(temp < Float.parseFloat(deficiency))
                                    break;
                            }

                            info[1] = xpp.getText();
                        }
                        else if (tag.equals("SERVING_SIZE")) {
                            break;
                        }
                        else if (tag.equals("FOOD_GROUP")) {
                            break;
                        }
                        else if (tag.equals("MAKER_NAME")) {
                            break;
                        }
                        else if (tag.equals("BGN_YEAR")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT9")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT8")) {
                            break;
                        }
                        else if (tag.equals("FOOD_CD")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT7")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT6")) {
                            buffer.append("나트륨 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");

                            float temp;
                            if(xpp.getText() == null || xpp.getText().isEmpty())
                                temp = 0;
                            else
                                temp = Float.parseFloat(xpp.getText());

                            if(strings[1].equals("6")) {
                                if(temp < Float.parseFloat(deficiency))
                                    break;
                            }

                            info[6] = xpp.getText();
                        }
                        else if (tag.equals("NUTR_CONT5")) {
                            break;
                        }
                        else if (tag.equals("NUTR_CONT4")) {
                            buffer.append("지방 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");

                            float temp;
                            if(xpp.getText() == null || xpp.getText().isEmpty())
                                temp = 0;
                            else
                                temp = Float.parseFloat(xpp.getText());

                            if(strings[1].equals("4")) {
                                if(temp < Float.parseFloat(deficiency))
                                    break;
                            }

                            info[4] = xpp.getText();
                        }
                        else if (tag.equals("DESC_KOR")) {
                            buffer.append("식품이름 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                            info[10] = xpp.getText();
                        }
                        else if (tag.equals("SAMPLING_MONTH_NAME")) {
                            break;
                        }
                        else if (tag.equals("SAMPLING_REGION_NAME")) {
                            break;
                        }
                        else if (tag.equals("SUB_REF_NAME")) {
                            break;
                        }

                        else if (tag.equals("GROUP_NAME")) {
                            break;
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
                        tag= xpp.getName(); // 태그 이름 얻어오기
                        if(tag.contains("row")) {
                            if(count >=1){
                                foodList.add(new Food(info[10],new String[]{info[2],info[6],info[3],info[4]},info[1]));
                                Log.d("test", "getXmlData: "+info[10]+" "+info[2]+" "+info[3]+" "+info[4]+" "+info[1]);
                            }

                            buffer.append("\n");
                        };        // 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType= xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "XML : " + e);
        }
        Log.d("test", "getXmlData: count  : "+count);
        buffer.append("파싱을 종료합니다!\n");

        Log.d("test", "========== XML parsing stop ==========");
        return buffer.toString();
    }
}
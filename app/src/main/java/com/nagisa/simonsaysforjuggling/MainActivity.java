package com.nagisa.simonsaysforjuggling;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.net.sip.SipAudioCall;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.widget.TextView.BufferType.NORMAL;
import static android.widget.Toast.makeText;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;



public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    int i,count;
    View contentview;
    private int NumOfTimes=3;

    private ArrayList<Boolean> wazaBoolean=new ArrayList<>();
    List<Map<String,Object>> wazaList= new ArrayList<>();

    public ArrayList<String> wazaString= new ArrayList<String>();
 //   public ArrayList<>
    public Map<String,Object>map=new HashMap<>();
    private MediaPlayer mediaPlayer;
    private Handler handler;

        // Mapのキー
    private final String[] FROM = {"wazaname","check"};
        // リソースのコントロールID
    private final int[] TO = {R.id.textView,R.id.checkBox};

        // カスタムアダプター
     private class MyAdapter extends  SimpleAdapter{

            // 外部から呼び出し可能なマップ
            public Map<Integer,Boolean> checkList = new HashMap<>();

            public MyAdapter(Context context, List<? extends Map<String, ?>> data,
                             int resource, String[] from, int[] to) {
                super(context, data, resource, from, to);

                // 初期値を設定する
                for(int i=0; i<data.size();i++){
                    Map map = (Map)data.get(i);
                    checkList.put(i,(Boolean)map.get("check"));
                }
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox ch = view.findViewById(R.id.checkBox);

                // チェックの状態が変化した場合はマップに記憶する
                ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checkList.put(position,isChecked);
                    }
                });
                return view;
            }
            public Boolean getCheck(final int position){

                return checkList.get(position);
            }
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Where", "OnCreate");
        // 音量調整を端末のボタンに任せる
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ListIn("カスケード");
        ListIn("ハーフシャワー");
        ListIn("シャワー");
        ListIn("ピルエット");

        setContentView(R.layout.activity_home);
        contentview=findViewById(R.id.activity_home);
        MobileAds.initialize(this, "ca-app-pub-2023826967245344~5179377209");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void ListIn(String newwazaa){
         wazaString.add(newwazaa);
         map=new HashMap<>();
         map.put("wazaname",newwazaa);
         map.put("check",true);
         wazaList.add(map);
    }

    public void GoSetting(View view){
        Log.i("Where","GoSetting");
        setContentView(R.layout.setting);
        contentview=findViewById(R.id.setting);
        ListView listView = (ListView) findViewById(R.id.wazalistview);
        EditText numedit = findViewById(R.id.numeditor);
        numedit.setText(Integer.toString(NumOfTimes),NORMAL);
        Resources resources=getResources();
        Log.i("Where","Clear list");
        final MyAdapter adapter = new MyAdapter(MainActivity.this,
                this.wazaList,R.layout.wazalist,FROM,TO);
        listView.setAdapter(adapter);
        view.findViewById(R.id.checkBox);
            //wazaBoolean.size()<=wazaList.size();
        findViewById(R.id.GoHome_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(i=0;i<wazaBoolean.size();i++){
                    wazaBoolean.set(i, adapter.getCheck(i));
                    // (wazaList.get(i)).s=adapter.getCheck(i);
                }
                for(i=wazaBoolean.size();i<wazaList.size();i++){
                    wazaBoolean.add( adapter.getCheck(i));
                    //wazaList.put(i,true);
                }
                GoHome(view);
            }
        });
    }




    public void GoHome(View view){
        Log.i("Where","GoHome");
        if(mediaPlayer!=null) {
            audioStop();
        }
        //設定からホームに戻るとき
        if(contentview.getId()==R.id.setting) {
            //回数のデータ記憶
            EditText numedit = findViewById(R.id.numeditor);
            String num = numedit.getText().toString();
            if (num.length() != 0) {
                NumOfTimes = Integer.parseInt(num);
                Toast toast=makeText(this,"回数を"+NumOfTimes+"回に変更しました",Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast=makeText(this,"回数が入力されていません",Toast.LENGTH_SHORT);
                toast.show();
                GoSetting(view);
            }
            Log.i("info","NumOfTimes:"+NumOfTimes);
        }
        setContentView(R.layout.activity_home);
        contentview=findViewById(R.id.activity_home);
    }

    public void SkillAddition(View view){
        EditText skillname=findViewById(R.id.newskill);
        String newskill=skillname.getText().toString();
        if(newskill.length()==0){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("技名をいれてください");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        else {
            ListIn(newskill);
            Toast toast=makeText(this,"技を登録しました",Toast.LENGTH_SHORT);
            toast.show();
            skillname.setText("");
            GoSetting(view);
        }
    }

    public void GoGame(View view){
        Log.i("Where","GoGame");
        setContentView(R.layout.activity_game);
        contentview=findViewById(R.id.activity_game);
        count=0;
        audioInstruct();
    }

    private void audioInstruct(){
        final String filename="Simonsays.mp3";
        Log.i("Where", "audioInstruct");
        if(count<NumOfTimes) {
            audioPlay(filename);
            count++;
        }
    }

    private boolean audioSetup(String filename){
        Log.i("Where","audioSetup");
        boolean fileCheck = false;
        // インタンスを生成
        mediaPlayer = new MediaPlayer();
        // assetsから mp3 ファイルを読み込み
        try(AssetFileDescriptor afdescripter = getAssets().openFd(filename)){
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),afdescripter.getStartOffset(),afdescripter.getLength());
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return fileCheck;
    }

    private void audioPlay(final String filename) {
        Log.i("Where","audioPlay:"+filename);
        if (mediaPlayer == null) {
            Log.i("Where","medianull");
            // audio ファイルを読出し
            if (audioSetup(filename)){
            }
            else{
                return;
            }
        }
        // 再生する
        mediaPlayer.start();
        Log.i("Where","start");
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                audioStop();
                switch (filename) {
                    case "Simonsays.mp3":
                        audioPlay(ChooseSkill() + ".mp3");
                        break;
                    case "カスケード.mp3":
                    case "ハーフシャワー.mp3":
                    case "ピルエット.mp3":
                    case "シャワー.mp3":
                        audioPlay("do.mp3");
                        break;
                    case "do.mp3":
                        audioInstruct();
                        break;
                }
            }
        });
    }

    private String ChooseSkill(){

        TextView TVinstruct;
        int r;
        TVinstruct =findViewById(R.id.instruct);
        Random rnd=new Random();
        do {
            r = rnd.nextInt(wazaList.size());
        }while(!wazaBoolean.get(r));
        TVinstruct.setText(wazaString.get(r) + "をしてください");
        return wazaString.get(r);
    }

    private void audioStop() {
        // 再生終了
        mediaPlayer.stop();
        // リセット
        mediaPlayer.reset();
        // リソースの解放
        mediaPlayer.release();
        mediaPlayer = null;
    }

}

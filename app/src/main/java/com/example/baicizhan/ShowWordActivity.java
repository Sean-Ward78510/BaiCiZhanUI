package com.example.baicizhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import Data.QueryWord;
import Tool.OKHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowWordActivity extends AppCompatActivity {

    String address = "https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/words/";
    EditText editText;
    ImageView imageView;
    TextView title;
    TextView accent;
    TextView mean_cn;
    TextView mean_en;
    TextView sentence;
    TextView sentence_meaning;
    QueryWord queryWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word);

        editText = (EditText) findViewById(R.id.show_edit);
        imageView = (ImageView) findViewById(R.id.show_icon);
        title = (TextView) findViewById(R.id.title_word);
        accent = (TextView) findViewById(R.id.accent_word);
        mean_cn = (TextView) findViewById(R.id.mean_cn_word);
        mean_en = (TextView) findViewById(R.id.mean_en_word);
        sentence = (TextView) findViewById(R.id.sentence_word);
        sentence_meaning = (TextView) findViewById(R.id.sentence_meaning_word);


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Intent intent = new Intent(ShowWordActivity.this,SearchWordActivity.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowWordActivity.this,SearchWordActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        Log.d("showWord", "onCreate: ExtraString" +" " + word);
        queryWord = new QueryWord();
        queryWord(word);
    }
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            showQueryWord();
        }
    };
    public void queryWord(String word) {
        OKHttpUtil.sandOkhttp(address + word + ".json", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String rp = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(rp);
                    queryWord.word = jsonObject.getString("word");
                    queryWord.accent = jsonObject.getString("accent");
                    queryWord.mean_cn = jsonObject.getString("mean_cn");
                    queryWord.mean_en = jsonObject.getString("mean_en");
                    queryWord.sentence = jsonObject.getString("sentence");
                    queryWord.sentence_meaning = jsonObject.getString("sentence_trans");
                    Log.d("showWord1", "showQueryWord: " + queryWord.word + "  " + queryWord.sentence_meaning);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        handler.sendEmptyMessage(1);
    }
    public void showQueryWord(){
        title.setText(queryWord.word);
        accent.setText(queryWord.accent);
        mean_cn.setText(queryWord.mean_cn);
        mean_en.setText(queryWord.mean_en);
        sentence.setText(queryWord.sentence);
        sentence_meaning.setText(queryWord.sentence_meaning);
        Log.d("showWord2", "showQueryWord: " + queryWord.word + "  " + queryWord.sentence_meaning);
    }
}
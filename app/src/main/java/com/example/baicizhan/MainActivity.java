package com.example.baicizhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Data.Vocabulary;
import DataBase.WordDataBase;
import Tool.OKHttpUtil;
import Tool.ViewPageAdapter;
import Tool.VocabularyDao;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String query_word = "https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/words/";
    //https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/words/word.json
    private String loadWords = "https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/list.json";
    private SharedPreferences sp;
    private WordDataBase wordDataBase;
    private VocabularyDao dao;

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPageAdapter adapter;
    private List<Fragment> fragments;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        initUI();
        initWords();
    }

    private void initWords(){
        boolean isInit = false;
        sp = getPreferences(Context.MODE_PRIVATE);
        isInit = sp.getBoolean("word_init",false);
        Log.d("Word0", "initWords: ");
        if (!isInit){
            wordDataBase = Room.databaseBuilder(this,WordDataBase.class,"my_wordDataBase")
                    .build();
            dao = wordDataBase.vocabularyDao();
            Log.d("Word1", "initWords: ");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OKHttpUtil.sandOkhttp(loadWords,new Callback(){
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                String respondDate = response.body().string();
                                Log.d("Word2", "initWords: ");
                                try {
                                    JSONObject jsonObject = new JSONObject(respondDate);
                                    JSONArray array = jsonObject.getJSONArray("list");

                                    for (int i = 0; i < array.length(); i++) {
                                        Vocabulary vocabulary = new Vocabulary();
                                        vocabulary.word = (String) array.get(i);
                                        dao.insertWord(vocabulary);
                                    }
                                    Log.d("Word", "initWords: list number is" + " " + dao.getAll().size());
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.d("Word", "onFailure: " + e);
                            }
                        });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
            isInit = true;
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("word_init",true);
            editor.apply();
        }
    }
    private void initUI(){
        fragments = new ArrayList<>();
        fragments.add(new Fragment_Word());
        fragments.add(new Fragment_Study());
        fragments.add(new Fragment_Store());
        fragments.add(new Fragment_Mine());
        adapter = new ViewPageAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.word);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.study);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.store);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.mine);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.word)
                    viewPager.setCurrentItem(0);
                if (item.getItemId() == R.id.study)
                    viewPager.setCurrentItem(1);
                if (item.getItemId() == R.id.store)
                    viewPager.setCurrentItem(2);
                if (item.getItemId() == R.id.mine)
                    viewPager.setCurrentItem(3);
                return true;
            }
        });
    }
}
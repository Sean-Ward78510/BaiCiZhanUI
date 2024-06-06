package com.example.baicizhan;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.room.Insert;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Data.Vocabulary;
import DataBase.WordDataBase;
import Tool.SearchAdapter;
import Tool.VocabularyDao;
import Tool.WordAdapter;

public class SearchWordActivity extends AppCompatActivity {
    private WordDataBase wordDataBase;
    private VocabularyDao dao;

    private SearchView searchView;
    private ListView listView;
    private List<Vocabulary> vocabularies;
    private SearchAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        wordDataBase = Room.databaseBuilder(this, WordDataBase.class,"my_wordDataBase").build();
        dao = wordDataBase.vocabularyDao();

        searchView = (SearchView) findViewById(R.id.query_search);
        listView = (ListView) findViewById(R.id.query_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vocabulary vocabulary = (Vocabulary) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent(SearchWordActivity.this,ShowWordActivity.class);
                intent.putExtra("word",vocabulary.word);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() != 0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            vocabularies = dao.searchWords(newText);
                            Log.d("SearchWord", "run: " + newText);
                        }
                    }).start();
                    try {
                        Thread.sleep(300);
                        if (adapter == null){
                            adapter = new SearchAdapter(SearchWordActivity.this,R.layout.word_item,vocabularies);
                            listView.setAdapter(adapter);
                        }else {
                            adapter.setVocabularies(vocabularies);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    adapter.vocabularies.clear();
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
    }
}
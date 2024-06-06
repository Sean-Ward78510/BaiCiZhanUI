package com.example.baicizhan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.baicizhan.R;

import java.util.ArrayList;
import java.util.List;

import Data.Vocabulary;
import DataBase.WordDataBase;
import Tool.VocabularyDao;
import Tool.WordAdapter;

public class Fragment_Word extends Fragment {
    NestedScrollView nestedScrollView;
    AutoCompleteTextView autoText;
    private WordDataBase wordDataBase;
    private VocabularyDao dao;
    WordAdapter adapter;
    ImageView imageView;
    List<Vocabulary> wordList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word,container,false);
        nestedScrollView = (NestedScrollView) view.findViewById(R.id.NestScrollView);

        autoText = (AutoCompleteTextView) view.findViewById(R.id.autoComplete_word);
        imageView = (ImageView) view.findViewById(R.id.search_icon_word);

        wordDataBase = Room.databaseBuilder(getContext(),WordDataBase.class,"my_wordDataBase").build();
        dao = wordDataBase.vocabularyDao();

        getWordList();
        adapter = new WordAdapter(getContext(),R.layout.word_item,wordList);
        autoText.setAdapter(adapter);
        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<Vocabulary> arrayAdapter = (ArrayAdapter<Vocabulary>) adapterView.getAdapter();
                Vocabulary vocabulary = arrayAdapter.getItem(i);
                Intent intent = new Intent(getContext(),ShowWordActivity.class);
                intent.putExtra("word",vocabulary.word);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchWordActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getWordList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                wordList = dao.getAll();
            }
        }).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

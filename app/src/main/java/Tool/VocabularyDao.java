package Tool;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Data.Vocabulary;

@Dao
public interface VocabularyDao{

    @Query("SELECT * FROM vocabulary")
    List<Vocabulary> getAll();

    @Query("SELECT * FROM vocabulary WHERE WORD LIKE '%' || :s || '%' ")
    List<Vocabulary> searchWords(String s);

    @Update
    void upDataWord(Vocabulary vocabulary);

    @Delete
    void deleteWord(Vocabulary vocabulary);

    @Insert
    long insertWord(Vocabulary vocabulary);
}

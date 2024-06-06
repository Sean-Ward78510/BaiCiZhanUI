package DataBase;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import Data.Vocabulary;
import Tool.VocabularyDao;

@Database(entities = {Vocabulary.class},version = 1,exportSchema = false)
public abstract class WordDataBase extends RoomDatabase {
    public abstract VocabularyDao vocabularyDao();
}

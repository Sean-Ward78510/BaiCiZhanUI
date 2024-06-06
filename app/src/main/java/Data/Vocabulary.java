package Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vocabulary {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String word;
    public Vocabulary() {
    }

    @Override
    public String toString() {
        return word;
    }
}

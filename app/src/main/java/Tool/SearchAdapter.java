package Tool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baicizhan.R;

import java.util.ArrayList;
import java.util.List;

import Data.Vocabulary;

public class SearchAdapter extends ArrayAdapter<Vocabulary> {

    private int res;
    public List<Vocabulary> vocabularies;

    public SearchAdapter(@NonNull Context context, int resource, @NonNull List<Vocabulary> objects) {
        super(context, resource, objects);
        res = resource;
        vocabularies = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Vocabulary vocabulary = vocabularies.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(res,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.word);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(vocabulary.word);
        return view;
    }

    class ViewHolder{
        TextView textView;
    }

    @Override
    public int getCount() {
        return vocabularies.size();
    }

    public void setVocabularies(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }
}

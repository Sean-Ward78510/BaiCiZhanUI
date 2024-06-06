package Tool;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.baicizhan.R;

import java.util.ArrayList;
import java.util.List;

import Data.Vocabulary;
import DataBase.WordDataBase;

public class WordAdapter extends ArrayAdapter<Vocabulary> {
    private int layoutRes;
    private List<Vocabulary> originalList = null;
    private List<Vocabulary> filteredList;

    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Vocabulary> objects) {
        super(context, resource, objects);
        layoutRes = resource;
        originalList = new ArrayList<>(objects);
        filteredList = new ArrayList<>();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Vocabulary vocabulary = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(layoutRes,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.word);
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
        return filteredList.size();
    }

    @Nullable
    @Override
    public Vocabulary getItem(int position) {
        return filteredList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                filteredList.clear();
                if (charSequence == null || charSequence.length() == 0){
                }else {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    for (Vocabulary item : originalList){
                        if (item.word.contains(filterPattern))
                            filteredList.add(item);
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    clear();
                    addAll((List<Vocabulary>) filterResults.values);
                    notifyDataSetChanged();
                }
//                if (filterResults != null && filterResults.count > 0) {
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
            }
        };
    }

}

package Tool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baicizhan.R;

import java.util.List;

import Data.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Card> goodList;
    public CardAdapter(List<Card> goodList) {
        this.goodList = goodList;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView title;
        TextView introduce;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imageView = (ImageView) itemView.findViewById(R.id.card_image);
            title = (TextView) itemView.findViewById(R.id.card_title);
            introduce = (TextView) itemView.findViewById(R.id.card_introduce);
            price = (TextView) itemView.findViewById(R.id.card_price);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_store_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = goodList.get(position);
        holder.imageView.setImageResource(card.getImage());
        holder.title.setText(card.getTitle());
        holder.introduce.setText(card.getIntroduce());
        holder.price.setText(card.getPrice());
    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

}

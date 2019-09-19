package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Responsible for displaying data from the model into a row in the recycler view
public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.ViewHolder> {

    //Interface Definition
    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    //Class Member Variables
    List<String> items;
    String cur_item;
    OnLongClickListener longClickListener;



    //Constructor
    public itemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    //Responsible for creating each view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        //Wrap it inside of a viewholder and return
        return new ViewHolder(todoView);
    }
    //Responsible for taking data in a particular position and putting it into a particular ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab the item at the position
        cur_item = items.get(position);
        //Bind the item into the specified view holder
        holder.bind(cur_item);

    }
    //Returns the number of Items available in the data (Tells the RV how many items are in the list)
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }
        //UPdate the view inside the the view holder with this data
        public void bind(String item){
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Notify the listener which position was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true; //returning true consumes the long click
                }
            });
        }
    }
}

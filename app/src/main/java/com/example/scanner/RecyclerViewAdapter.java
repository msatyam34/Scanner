package com.example.scanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scanner.db.ScannedItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<ScannedItem> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setItemList(List<ScannedItem> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();


    }



    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_design,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.serialNoTextView.setText(Integer.toString(itemList.get(position).sNo));
        holder.codeTextView.setText(itemList.get(position).barcode);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView serialNoTextView;
        TextView codeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            serialNoTextView = itemView.findViewById(R.id.serialNoTextView);
            codeTextView = itemView.findViewById(R.id.codeTextView);
        }
    }
}

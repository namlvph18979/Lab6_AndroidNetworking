package com.example.lab6_androidnetworking.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lab6_androidnetworking.DAO.Books_DAO;
import com.example.lab6_androidnetworking.DAO.File_DAO;
import com.example.lab6_androidnetworking.Model.Books;
import com.example.lab6_androidnetworking.R;
import com.example.lab6_androidnetworking.UI.Acti_BooksEdit;
import com.example.lab6_androidnetworking.UI.Acti_viewBooks;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Books_Adapter extends RecyclerView.Adapter<Books_Adapter.ViewHoder> implements Filterable {
    Context context;
    ArrayList<Books> _list;
    ArrayList<Books> _listOld;
    Books books;
    Books_DAO dao;
    File_DAO file_dao;

    public Books_Adapter(Context context, ArrayList<Books> _list) {
        this.context = context;
        this._list = _list;
        this._listOld = _list;
    }


    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, @SuppressLint("RecyclerView") int position) {
        books = _list.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.price.setText(formatter.format(_list.get(position).getPrice())+" vnd");
        holder.title.setText(_list.get(position).getName());
        holder.author.setText(_list.get(position).getAuthor());
        //holder.price.setText(String.valueOf(_list.get(position).getPrice()));
        Picasso.get().load(_list.get(position).getLink()).centerCrop().resize(350,350).into(holder.img);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Books_Adapter.this.onClick(_list.get(position));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                books = _list.get(position);
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_delete);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button yes,no;
                TextView title;
                title = dialog.findViewById(R.id.tv_dialog_title);
                title.setText("Delete "+books.getName()+" ?");
                yes = dialog.findViewById(R.id.btn_dialog_yes);
                no = dialog.findViewById(R.id.btn_dialog_no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dao = new Books_DAO();
                        file_dao = new File_DAO();
                        file_dao.Delete_image(_list.get(position).getLink(),context);
                        dao.delete_Books(books.getID(),context);
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Acti_BooksEdit.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",books = _list.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }
    private void onClick(Books books){
        Intent intent = new Intent(context, Acti_viewBooks.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",books);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (_list!=null){
            return _list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence.toString().isEmpty()){
                    _list = _listOld;
                }else{
                    ArrayList<Books> _arr = new ArrayList<>();
                    for (Books books: _listOld) {
                        if (books.getName().toString().toUpperCase().contains(charSequence.toString().toUpperCase())){
                            _arr.add(books);
                        }
                    }
                    _list = _arr;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =_list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                _list = (ArrayList<Books>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        ImageView img;
        LinearLayout linearLayout;
        TextView title,author,price;
        TextView edit,delete;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_home_img);
            title = itemView.findViewById(R.id.item_home_title);
            author = itemView.findViewById(R.id.item_home_author);
            price = itemView.findViewById(R.id.item_home_price);
            edit = itemView.findViewById(R.id.item_home_edit);
            delete = itemView.findViewById(R.id.item_home_delete);
            linearLayout = itemView.findViewById(R.id.item_home_LinearLayout);
        }
    }
}

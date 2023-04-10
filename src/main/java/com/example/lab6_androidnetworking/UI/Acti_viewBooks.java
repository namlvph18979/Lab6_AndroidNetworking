package com.example.lab6_androidnetworking.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.lab6_androidnetworking.Model.Books;
import com.example.lab6_androidnetworking.R;
import com.squareup.picasso.Picasso;

public class Acti_viewBooks extends AppCompatActivity {

    ImageView img;
    TextView name,author,message;
    Books books;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_view_books);
        img = findViewById(R.id.Acti_viewBooks_img);
        name = findViewById(R.id.Acti_viewBooks_name);
        author = findViewById(R.id.Acti_viewBooks_author);
        message = findViewById(R.id.TextView_message);
        Bundle bundle = getIntent().getExtras();
        if (bundle==null){
            return;
        }
        books = new Books();
        books = (Books) bundle.get("data");
        Picasso.get().load(books.getLink()).centerCrop().resize(350,350).into(img);
        name.setText(books.getName());
        author.setText(books.getAuthor());
        message.setText(books.getMessage());
        message.setText(books.getMessage());
    }
}
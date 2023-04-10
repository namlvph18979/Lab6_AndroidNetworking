package com.example.lab6_androidnetworking.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6_androidnetworking.DAO.Books_DAO;
import com.example.lab6_androidnetworking.Model.Books;
import com.example.lab6_androidnetworking.R;

import com.google.android.material.textfield.TextInputLayout;

public class Acti_BooksEdit extends AppCompatActivity {

    TextInputLayout name,message,author,price;
    String _name,_message,_author,_price;
    //Date dNow;
    int check = 0;
    TextView create;
    Books_DAO dao;
    Books books;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_books_edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle==null){
            return;
        }
        books = new Books();
        books = (Books) bundle.get("data");

        dao = new Books_DAO();

        name = findViewById(R.id.Acti_BooksEdit_name);
        message = findViewById(R.id.Acti_BooksEdit_messege);
        author = findViewById(R.id.Acti_BooksEdit_author);
        price = findViewById(R.id.Acti_BooksEdit_price);
        create = findViewById(R.id.Acti_BooksEdit_tv);

        name.getEditText().setText(books.getName());
        message.getEditText().setText(books.getMessage());
        author.getEditText().setText(books.getAuthor());
        price.getEditText().setText(String.valueOf(books.getPrice()));

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name = name.getEditText().getText().toString().trim();
                _message = message.getEditText().getText().toString().trim();
                _author = author.getEditText().getText().toString().trim();
                _price = price.getEditText().getText().toString().trim();

                if (_name.isEmpty()){
                    name.setError("Tên Sách");
                    name.setErrorEnabled(true);
                }else{
                    name.setErrorEnabled(false);
                    check++;
                }

                if (_message.isEmpty()){
                    message.setError("Nội dung");
                    message.setErrorEnabled(true);
                }else{
                    message.setErrorEnabled(false);
                    check++;
                }

                if (_author.isEmpty()){
                    author.setError("Tác giả");
                    author.setErrorEnabled(true);
                }else{
                    author.setErrorEnabled(false);
                    check++;
                }

                if (_price.isEmpty()){
                    price.setError("Giá tiền");
                    price.setErrorEnabled(true);
                }else{
                    price.setErrorEnabled(false);
                    check++;
                }
                if (check==4){
                    Books _books = new Books(books.getID(),_name,_message,_author,books.getLink(),Integer.parseInt(_price));
                    dao.Edit_Books(_books,books.getID(),Acti_BooksEdit.this);
                    check=0;
                    finish();
                }else{
                    check=0;
                }
            }
        });
    }
}
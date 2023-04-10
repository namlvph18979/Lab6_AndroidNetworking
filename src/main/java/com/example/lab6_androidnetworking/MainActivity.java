
package com.example.lab6_androidnetworking;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6_androidnetworking.Adapter.Books_Adapter;
import com.example.lab6_androidnetworking.DAO.Books_DAO;
import com.example.lab6_androidnetworking.DAO.User_DAO;
import com.example.lab6_androidnetworking.Model.Books;
import com.example.lab6_androidnetworking.UI.Acti_createBooks;
import com.example.lab6_androidnetworking.UI.Acti_hello;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView username,email,logout;
    SearchView searchView;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    FirebaseFirestore firebaseFirestore;
    Books_Adapter adapter;
    Books_DAO dao;
    User_DAO user_dao;
    ArrayList<Books> _list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_dao = new User_DAO();
        username = findViewById(R.id.MainActivity_fullname);
        email = findViewById(R.id.MainActivity_email);
        logout = findViewById(R.id.MainActivity_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn muốn đăng xuất không ???")
                        .setTitle("Đăng Xuất Tài Khoản");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Toast.makeText(MainActivity.this, "Đăng Xuất", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, Acti_hello.class));
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Toast.makeText(view.getContext(), "Thao tác bị hủy", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        user_dao.checkUser(username,email);

        searchView = findViewById(R.id.MainActivity_SearchView);

        recyclerView = findViewById(R.id.MainActivity_RecyclerView);
        floatingActionButton = findViewById(R.id.MainActivity_FloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Acti_createBooks.class));
            }
        });
        firebaseFirestore = FirebaseFirestore.getInstance();
        _list = new ArrayList<>();
        adapter = new Books_Adapter(MainActivity.this,_list);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        dao = new Books_DAO();
        read();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    public void read(){
        firebaseFirestore.collection("Books").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                dao.readDataBooks(_list,adapter);
            }
        });
    }
}
package com.example.lab6_androidnetworking.DAO;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lab6_androidnetworking.Adapter.Books_Adapter;
import com.example.lab6_androidnetworking.MainActivity;
import com.example.lab6_androidnetworking.Model.Books;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Books_DAO {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void create_Books(Books books, Context context) {
        CollectionReference collectionReference = db.collection("Books");
        DocumentReference documentReference = collectionReference.document();
        Map<String, Object> book = new HashMap<>();
        books.setID(documentReference.getId());

        book.put("ID", documentReference.getId());
        book.put("name", books.getName());
        book.put("message", books.getMessage());
        book.put("author", books.getAuthor());
        book.put("price", books.getPrice());
        book.put("link", books.getLink());
        documentReference.set(books).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Thêm Books Thành Công", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Thêm Books Khum Thành Công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void readDataBooks(List<Books> _list, Books_Adapter adapter) {
        CollectionReference collectionReference = db.collection("Books");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (_list != null) {
                    _list.clear();
                }
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> _arr = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : _arr) {
                        Books sp = d.toObject(Books.class);
                        _list.add(sp);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    public void delete_Books(String ID, Context context) {
        CollectionReference collectionReference = db.collection("Books");
        collectionReference.document(ID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Xóa Books Thành Công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xóa Books Thất Bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Edit_Books(Books books,String index, Context context){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Books").document(index)
                .set(books)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Update Books Thành Công !!!", Toast.LENGTH_SHORT).show();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Update Books Thành Công Thất Bại  !!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

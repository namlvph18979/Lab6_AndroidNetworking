package com.example.lab6_androidnetworking.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6_androidnetworking.DAO.Books_DAO;
import com.example.lab6_androidnetworking.DAO.File_DAO;
import com.example.lab6_androidnetworking.MainActivity;
import com.example.lab6_androidnetworking.Model.Books;
import com.example.lab6_androidnetworking.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Acti_createBooks extends AppCompatActivity {
    TextInputLayout name,message,author,price;
    ImageView imageView;
    Uri imageUri;
    StorageReference storageReference;

    String _name,_message,_author,_price,_link;
    //Date dNow;
    int check = 0;
    TextView create,cance;
    File_DAO file_dao;
    Books_DAO dao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_create_books);
        dao = new Books_DAO();
        file_dao = new File_DAO();
        storageReference = FirebaseStorage.getInstance().getReference();

        _link=null;
        name = findViewById(R.id.edt_new_name);
        message = findViewById(R.id.edt_new_messege);
        author = findViewById(R.id.edt_new_author);
        price = findViewById(R.id.edt_new_price);

        imageView = findViewById(R.id.Acti_createBooks_ImageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        create = findViewById(R.id.tv_createBooks);
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
                if (_link==null){
                    Toast.makeText(Acti_createBooks.this, "Chưa có ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    check++;
                }

                if (check==5){
                    Books books = new Books("id",_name,_message,_author,_link,Integer.parseInt(_price));
                    dao.create_Books(books,Acti_createBooks.this);
                    check=0;
                    finish();
                }else{
                    check=0;
                }
            }
        });
        cance = findViewById(R.id.tv_canceBooks);
        cance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_link!=null){
                    file_dao.Delete_image(_link,Acti_createBooks.this);
                    startActivity(new Intent(Acti_createBooks.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&data!=null&&data.getData()!=null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadImage();
        }
    }
    public void uploadImage(){
        StorageReference image = storageReference.child(imageUri.getLastPathSegment());
        image.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Acti_createBooks.this, "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show();
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        _link = uri.toString();
                        Log.e("TAG", "url: "+uri.toString() );
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Acti_createBooks.this, "Cập nhật ảnh thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
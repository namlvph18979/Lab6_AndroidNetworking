package com.example.lab6_androidnetworking.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class File_DAO {

    public void Delete_image(String urlImage, Context context){

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference gsReference=firebaseStorage.getReferenceFromUrl(urlImage);

        String url = gsReference.getPath();

        StorageReference storageRef = firebaseStorage.getReference();
        StorageReference desertRef = storageRef.child(url);
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Xóa ảnh thành công.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("TAG", "Xóa ảnh thất bại." );
            }
        });
    }
}

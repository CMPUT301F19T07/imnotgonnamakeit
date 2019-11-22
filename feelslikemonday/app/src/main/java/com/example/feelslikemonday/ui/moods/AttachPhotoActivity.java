package com.example.feelslikemonday.ui.moods;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.feelslikemonday.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AttachPhotoActivity extends AppCompatActivity {
    //Request codes
    public static final int REQUEST_CODE = 100;
    public static final int CAMERA_REQUEST = 9999;
    public static final int ALBUM_REQUEST = 1111;

    //Result codes
    public static final int RES_OK = 0;
    
    //Extra codes
    public static final String BITMAP_BYTE_ARRAY_EXTRA = "BITMAP_STRING_EXTRA";

    private ImageView imageView;
    private Button cancelButton;
    private Context PostImage;
    private Button saveButton;

    Bitmap returnBitmap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_photo);
        imageView =findViewById(R.id.attach_myphoto);
        cancelButton = findViewById(R.id.attach_cancel);
        saveButton = findViewById(R.id.save_image_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void openCamera(View view){

        Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);

    }
    public void openAlbum(View view){
        Intent intent =  new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,ALBUM_REQUEST);

    }
    public void saveImage(View view){
        if(returnBitmap != null){
            Intent output = new Intent();
            //Get image from the imageView, convert it into a byte array, and then pass it as an extra
            output.putExtra(BITMAP_BYTE_ARRAY_EXTRA,bitmapToByteArray(returnBitmap));
            setResult(RES_OK,output);
            finish();
        }
        else{
            Toast.makeText(PostImage, "No image to save",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Converts bitmap to string. Code obtained from https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
     * @param bitmap
     * @return
     */
    public byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        PostImage = getApplicationContext();
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST && resultCode == RESULT_OK ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            returnBitmap = bitmap;
            imageView.setImageBitmap(bitmap);
        }

        //if(requestCode==ALBUM_REQUEST && requestCode == RESULT_OK && null!=data){
        else if(requestCode==ALBUM_REQUEST && resultCode == RESULT_OK ){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //Copy bitmap to prevent recycling errors
                returnBitmap = selectedImage.copy(selectedImage.getConfig(),selectedImage.isMutable());
                imageView.setImageBitmap(returnBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PostImage, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(PostImage, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}

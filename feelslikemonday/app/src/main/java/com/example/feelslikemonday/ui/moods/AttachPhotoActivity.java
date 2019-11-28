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
/**
 * This class is responsible for adding a photo
 */
public class AttachPhotoActivity extends AppCompatActivity {
    //Request codes
    public static final int REQUEST_CODE = 100;
    public static final int CAMERA_REQUEST = 9999;
    public static final int ALBUM_REQUEST = 1111;

    //Result codes
    public static final int RES_OK = 0;
    
    //Extra codes
    public static final String BITMAP_BYTE_ARRAY_EXTRA = "BITMAP_STRING_EXTRA";

    //Maximum dimensions of the downscaled bitmap
    private final double MAX_DIM_SIZE = 350.0;


    private ImageView imageView;
    private Button cancelButton;
    private Context PostImage;
    private Button saveButton;

    private Bitmap returnBitmap = null;

    /**
     * This initializes AttachPhotoActivity
     * @param savedInstanceState
     * This is a previous saved state
     */
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

    /**
     * This opens camera
     * @param view
     * This is a view returned by onCreate()
     */
    public void openCamera(View view){

        Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST);

    }

    /**
     * This opens album
     * @param view
     * This is a view returned by onCreate()
     */
    public void openAlbum(View view){
        Intent intent =  new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,ALBUM_REQUEST);

    }

    /**
     * This saves image
     * @param view
     * This is a view returned by onCreate()
     */
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
     * Down scale the bitmap so that it can fit into firestore
     * @param srcBitmap
     * The bitmap that we want to downscale
     * @return
     * returns the downscaled bitmap
     */
    private Bitmap downscaleBitmap(Bitmap srcBitmap){
        double maxSrcDim = Math.max(srcBitmap.getHeight(),srcBitmap.getWidth());
        double scale = MAX_DIM_SIZE/maxSrcDim;
        return Bitmap.createScaledBitmap(srcBitmap,(int)(srcBitmap.getWidth()/scale),(int)(srcBitmap.getHeight()/scale),false);
    }

    /**
     * This converts bitmap to a byte array, which is used to store into firestore. Code obtained from https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
     * @param bitmap
     * This is a bitmap
     * @return
     *      return byte array string
     */
    private byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /**
     * This gives the requestCode you started it with, the resultCode it returned, and any additional data from it
     * @param requestCode
     * This is the integer request code originally supplied to startActivityForResult()
     * @param resultCode
     * This is the integer result code returned by the child activity through its setResult()
     * @param data
     * This is the result data
     */
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
                //Downscale the image so that it fits in Firestore
                returnBitmap = downscaleBitmap(returnBitmap);
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

package com.example.akshar.imagedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static com.example.akshar.imagedatabase.InsertImage.encodeToBase64;
import static com.example.akshar.imagedatabase.R.id.edtname;

public class UpdateImage extends AppCompatActivity {
    EditText edtName, edtNumber;
    Button btnInsert, btnGetPhot;

    ImageView imageView;
    Bitmap yourSelectedImage;
    boolean flag = false;
    Bitmap bitmapImage;
    String EncodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image);
        edtName = (EditText) findViewById(R.id.edtnameUpdate);
        edtNumber = (EditText) findViewById(R.id.edtNumberUpdate);
        btnInsert = (Button) findViewById(R.id.Updatedata);
        btnGetPhot = (Button) findViewById(R.id.GetPhotoUpdate);
        imageView = (ImageView) findViewById(R.id.imageViewUpdate);

        edtName.setText(getIntent().getStringExtra("name"));
        edtNumber.setText(getIntent().getStringExtra("number"));

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageDataStoreg imageDataStoreg = new ImageDataStoreg();
                SQliteHelper sqlHelper = new SQliteHelper(UpdateImage.this);
                bitmapImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                EncodeImage = encodeToBase64(bitmapImage, Bitmap.CompressFormat.JPEG, 100);
                imageDataStoreg.setName(edtName.getText().toString());
                imageDataStoreg.setId(getIntent().getStringExtra("id"));
                imageDataStoreg.setNumber(edtNumber.getText().toString());
                imageDataStoreg.setImage(EncodeImage);
                sqlHelper.updatedata(imageDataStoreg);
                startActivity(new Intent(UpdateImage.this, MainActivity.class));
                finish();
            }
        });
        btnGetPhot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {"Take Photo", "Choose from Gallary", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateImage.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                        } else if (items[item].equals("Choose from Gallary")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), 0);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode", requestCode + " test");
        switch (requestCode) {
            case 0:

                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);

                    cursor.close();
                    // Convert file path into bitmap image using below line.
                    yourSelectedImage = BitmapFactory.decodeFile(new File(filePath).getAbsolutePath());
                    Log.e("Selected", "Image: " + yourSelectedImage);
                    imageView.setImageBitmap(yourSelectedImage);
                    Log.e("State", filePath + "_test");
                    flag = true;
                    btnInsert.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    if (requestCode == 1) {
                        Bitmap imgurl = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(imgurl);
                        btnInsert.setVisibility(View.VISIBLE);
                    }
                }
        }

    }
}


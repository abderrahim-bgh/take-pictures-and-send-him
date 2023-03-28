package com.example.scanagent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView matrucule;
    AppCompatButton Scanner,send;
    private static final int pic_id = 123;
    byte[] byteArrayImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrucule= findViewById(R.id.matrucule);
        Scanner= findViewById(R.id.Scanner);


        Scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);


            }
        });


        String m="4883377474";


    }
    // This method will help to retrieve the image
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Set the image in imageview for display
            matrucule.setImageBitmap(photo);
            String matStr = "";
            //ITesseract tesseract = new Tesseract();
            BitmapDrawable draw = (BitmapDrawable) matrucule.getDrawable();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            matrucule.setImageBitmap(photo);
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
            byte[] byteArrayImage = baos.toByteArray();


            String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            String mat;

            mat=encodedImage;
            Toast.makeText(MainActivity.this,"code image : "+ mat, Toast.LENGTH_SHORT).show();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            {
                String change_promo="http://192.168.43.72:8080/api/v1/public/img";
                StringRequest request = new StringRequest(Request.Method.POST, change_promo,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast toast= Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT);
                                TextView txt_mat=findViewById(R.id.txt_mat);
                                txt_mat.setText(response);
                                toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                toast.show();                                      }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("mat",mat);

                        return params;

                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(request);
            }

            //Start ProgressBar first (Set visibility VISIBLE)
           /* Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[1];
                    field[0] = "mat";

                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = mat;


                   // StringRequest stringRequest= new StringRequest(DownloadManager.Request.Method.GET, url_Pub,

                    PutData putData = new PutData("http://192.168.43.78:8080/api/v1/public/img/", "POST", field, data);
                    if (putData.startPut()) {

                        if (putData.onComplete()) {
                            String result = putData.getResult();

                                Toast toast= Toast.makeText(MainActivity.this, " Success", Toast.LENGTH_LONG);
                                toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                toast.show();


                            //End ProgressBar (Set visibility to GONE)
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                    //End Write and Read data with URL
                }
            });
           FileOutputStream outStream = null;
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/fold");
            dir.mkdirs();
            String fileName = String.format("%d.jpg", System.currentTimeMillis());
            File outFile = new File(dir, fileName);
            try {
                outStream = new FileOutputStream(outFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            sendBroadcast(intent);

*/
        }
    }
}
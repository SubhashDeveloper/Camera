package subhash.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    Button bt, bt2;
    File output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView2);
        bt = (Button) findViewById(R.id.button);
        bt2 = (Button) findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try{
                    File f=new File(Environment.getExternalStorageDirectory()+"/pic");
                    if(!f.exists())
                        f.mkdir();
                    output=new File(f,new Date().toString()+".jpeg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(output));
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
                startActivityForResult(intent, 100);
                //methdo we need result , and we have to override
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            iv.setImageURI(Uri.fromFile(output));

        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Uri imgadd = data.getData();
            iv.setImageURI(imgadd);
        }

    }

    @Override
    //Permission grant code
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },10);

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}


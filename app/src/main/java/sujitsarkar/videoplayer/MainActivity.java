package sujitsarkar.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView video_list;
    VideoAdapter adapter;
    public static int REQUEST_PERMISSION = 1;
    File directory;
    boolean boolean_permission;
    public static ArrayList<File> fileArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        video_list = (RecyclerView) findViewById(R.id.video_list);

        //Phone memory and SD card path read...
        directory = new File("/mnt/");

        //Phone memory path read...
        //directory = new File("/storage/");

        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        video_list.setLayoutManager(manager);

        PermissionForVideo();
    }

    private void PermissionForVideo() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))){
                //
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION);
            }
        }
        else {
            boolean_permission = true;
            GetFile(directory);
            adapter = new VideoAdapter(getApplicationContext(),fileArrayList);
            video_list.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                boolean_permission = true;
                GetFile(directory);
                adapter = new VideoAdapter(getApplicationContext(),fileArrayList);
                video_list.setAdapter(adapter);
            }
            else {
                Toast.makeText(this, "Please allow the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

   public ArrayList<File> GetFile(File directory){
        File listFile[] = directory.listFiles();
        if (listFile != null && listFile.length > 0){
            for (int i=0; i<listFile.length; i++){
                if (listFile[i].isDirectory()){
                    GetFile(listFile[i]);
                }
                else{
                    boolean_permission = false;
                    if (listFile[i].getName().endsWith(".mp4")){
                        for (int j=0; j<fileArrayList.size(); j++){
                            if (fileArrayList.get(j).getName().equals(listFile[i].getName())){
                                boolean_permission = true;
                            }
                            else {

                            }
                        }
                        if (boolean_permission) {
                            boolean_permission = false;
                        }
                        else {
                            fileArrayList.add(listFile[i]);
                        }
                    }
                }
            }
        }
        return fileArrayList;
   }
}
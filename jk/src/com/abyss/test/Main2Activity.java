package com.abyss.test;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.abyss.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d("abyss", Environment.getDataDirectory().getPath());
        Log.d("abyss", Environment.getExternalStorageDirectory().getPath());
        String dirPath = getFilesDir().getAbsolutePath();
        Log.d("abyss", dirPath);
        File file = new File(dirPath);
// 일치하는 폴더가 없으면 생성
        if( !file.exists() ) {
            file.mkdirs();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
// txt 파일 생성
        String testStr = "ABCDEFGHIJK...";
        File savefile = new File(Environment.getExternalStorageDirectory().getPath() + "/band"+"/test.txt");
        try{
            FileOutputStream fos = new FileOutputStream(savefile);
            fos.write(testStr.getBytes());
            fos.close();
            Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
        } catch(IOException e){}

    }

}

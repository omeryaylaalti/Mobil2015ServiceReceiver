package com.example.ozlemyaylaalti.mobil2015servicereceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private TextView textView;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt(DownloadService.RESULT);

                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this, "İndirme işlemi tamamlandı. URL :" + string, Toast.LENGTH_LONG).show();
                    textView.setText("İndirme Tamamlandı.");
                } else {
                    Toast.makeText(MainActivity.this, "İndirme Yapılamadı", Toast.LENGTH_LONG).show();
                    textView.setText("İndirme Durduruldu.");
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,new IntentFilter(DownloadService.NOTIFICATION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public  void onClick(View view){
        Intent intent = new Intent(this,DownloadService.class);
        intent.putExtra(DownloadService.FILENAME, "index.html");
        intent.putExtra(DownloadService.URL, "http://www.vogella.com/index.html");
        startService(intent);
        textView.setText("Servis çalışmaya başladı");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

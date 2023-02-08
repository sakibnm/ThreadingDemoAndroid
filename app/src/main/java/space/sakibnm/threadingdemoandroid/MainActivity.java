package space.sakibnm.threadingdemoandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button buttonDoWork;
    private ProgressBar progressBar;
    private TextView textViewProgress;
    private ExecutorService threadPool;
    private Handler messageQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDoWork = findViewById(R.id.buttonDoWork);
        progressBar = findViewById(R.id.progressBar);
        textViewProgress = findViewById(R.id.textViewProgress);

        threadPool = Executors.newFixedThreadPool(1);

        messageQueue = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch(message.what){
                    case DoHeavyWork.STATUS_START:
                        Log.d("demo", "Worker is starting!");
                        break;
                    case DoHeavyWork.STATUS_END:
                        Log.d("demo", "Worker is done!");
                        break;
                    case DoHeavyWork.STATUS_PROGRESS:
                        Bundle receivedData = message.getData();
                        int progress = receivedData.getInt(DoHeavyWork.KEY_PROGRESS);
                        progressBar.setProgress(progress);
                        textViewProgress.setText((progress+1)+"");
                        Log.d("demo", "Progress of worker: "+(progress+1));
                        break;
                }
                return false;
            }
        });

        buttonDoWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threadPool.execute(
                        new DoHeavyWork("From UI: Do this carefully!", messageQueue)
                );
            }
        });
    }
}
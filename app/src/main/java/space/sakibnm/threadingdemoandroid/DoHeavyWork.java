package space.sakibnm.threadingdemoandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DoHeavyWork implements Runnable{
    public final static int STATUS_START = 0x001;
    public final static int STATUS_PROGRESS = 0x002;
    public final static int STATUS_END = 0x003;
    public final static String KEY_PROGRESS = "0x004";

    private String taskDescription;
    private Handler messageQueue;

    public DoHeavyWork(String message, Handler messageQueue){
        this.taskDescription = message;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        Log.d("demo", this.taskDescription+"");
        Message startMessage = new Message();
        startMessage.what = STATUS_START;
        messageQueue.sendMessage(startMessage);
        for (int i=0; i <100; i++){
            for (int j=0; j <10000000; j++){

            }
            Message progressMessage = new Message();
            progressMessage.what = STATUS_PROGRESS;
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_PROGRESS, i);
            progressMessage.setData(bundle);
            messageQueue.sendMessage(progressMessage);
        }
        Message endMessage = new Message();
        endMessage.what = STATUS_END;
        messageQueue.sendMessage(endMessage);
    }
}

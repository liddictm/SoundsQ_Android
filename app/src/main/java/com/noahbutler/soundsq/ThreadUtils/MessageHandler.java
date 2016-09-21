package com.noahbutler.soundsq.ThreadUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.noahbutler.soundsq.Activities.ShareActivity;
import com.noahbutler.soundsq.Constants;
import com.noahbutler.soundsq.Fragments.QueueBallFragment;
import com.noahbutler.soundsq.SoundPlayer.SoundPlayerController;
import com.noahbutler.soundsq.SoundPlayer.SoundQueue;

/**
 * Created by NoahButler on 1/6/16.
 */
public class MessageHandler extends Handler {

    /**
     * Method that handles incoming messages from threads
     * that are being sent to the UI thread.
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
        if(msg.getData().containsKey(Messenger.keys[0])) { // update views when a new sound has arrived
            signal_updateQueue();

        }else if(msg.getData().containsKey(Messenger.keys[1])) { // sound art has finished downloading
            signal_soundPackageDownloaded(msg);

        }else if(msg.getData().containsKey(Messenger.keys[3])) {
            signal_LoadingSuccess();

        }else if(msg.getData().containsKey(Messenger.notExists[0])) {
            signal_ShareFail();

        }else if(msg.getData().containsKey(Messenger.notExists[1])) {
            signal_requestFail();

        }
    }

    /**
     * Associated with Messenger.keys[0]
     * This is signaled by the DownStreamReceiver.
     *
     * It is signaled when the app needs to update the queue view because
     * a new sound has been added to the list
     */
    private void signal_updateQueue() {
        Constants.queueListView.invalidateViews();
        Constants.queueListAdapter.notifyDataSetChanged();
    }

    /**
     * Associated with Messenger.keys[1]
     * This is signaled by a SoundPackageDownloader object.
     *
     * It is signaled when the app needs to update a current sound on the queue
     * with the sound image (after it has been downloaded by the the SoundPackageDownloader).
     *
     * The image's (which has been cached on the device) location is sent
     * in the message as a string with the key: Messenger.keys[1].
     *
     * The method looks for the SoundPackage object associated with the image
     * (which is saved as the name of the song) and then sets the sound package image string
     * as the given one.
     *
     * The method then tell the queue view to update so that it now knows to display the given image.
     *
     * @param msg
     */
    private void signal_soundPackageDownloaded(Message msg) {
        String rawMsg = msg.getData().getString(Messenger.keys[1]);
        Log.e("HANDLER", "raw message: " + rawMsg);
            /* grab the correct sound package */
        for(int i = 0; i < SoundQueue.queue_packages.size(); i++) {
            if(SoundQueue.queue_packages.get(i).sound_url.contains(rawMsg.substring(0, rawMsg.lastIndexOf(".")))) {
                    /* update the sound package's sound image file to the downloaded one */
                SoundQueue.queue_packages.get(i).sendFileLocation(rawMsg);
            }
        }
            /* update our views to now display the sound image */
        Constants.queueListView.invalidateViews();
        Constants.queueListAdapter.notifyDataSetChanged();
    }

    private void signal_LoadingSuccess() {
        QueueBallFragment.loadingSuccess();
    }

    private void signal_ShareFail() {
        ShareActivity.failedShare();
    }

    private void signal_requestFail() {
        QueueBallFragment.failedRequest();
    }
}

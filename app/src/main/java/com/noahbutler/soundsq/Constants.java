package com.noahbutler.soundsq;

import android.widget.ListView;

import com.noahbutler.soundsq.Fragments.QueueListAdapter;
import com.noahbutler.soundsq.SoundPlayer.SoundPackage;
import com.noahbutler.soundsq.ThreadUtils.MessageHandler;
import com.noahbutler.soundsq.ThreadUtils.Messenger;

import java.util.ArrayList;

/**
 * Created by NoahButler on 9/18/15.
 */
public class Constants {
    /**
     * File that contains a cached queue id.
     */
    public static final String CACHE_FILE = "soundQ_queue_id_cache";
    /**
     * Amount of characters in a QUEUE ID
     */
    public static final int QUEUE_ID_LENGTH = 5;
    /**
     * Token of the current phone, given by GCM (Google Cloud Messaging). Used to send
     * down stream messages from our server to the correct phone.
     */
    public static String token = null;
    /**
     * List view that displays the queue.
     */
    public static ListView queueListView;
    /**
     * List Adapter that creates each element for the list view above.
     */
    public static QueueListAdapter queueListAdapter;
    /**
     * Receives signals from threads on the UI thread.
     * Then, runs the respected code for each type of message.
     */
    public static MessageHandler handler;
    /**
     * Sends signals created on different threads to send to the UI thread.
     */
    public static Messenger messenger = new Messenger();

}

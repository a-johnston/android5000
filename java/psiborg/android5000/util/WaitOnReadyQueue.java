package psiborg.android5000.util;

import android.util.Log;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WaitOnReadyQueue {
    private boolean ready;
    private Queue<Runnable> doWhenReadyList;

    public WaitOnReadyQueue() {
        doWhenReadyList = new ConcurrentLinkedQueue<>();
    }

    public void doWhenReady(Runnable r) {
        if (!ready) {
            doWhenReadyList.add(r);
        } else {
            r.run();
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
        runQueuedEvents();
    }

    public void runQueuedEvents() {
        while (!doWhenReadyList.isEmpty()) {
            doWhenReadyList.remove().run();
        }
    }

    public boolean getReady() {
        return ready;
    }

    public void clear() {
        doWhenReadyList.clear();
    }
}

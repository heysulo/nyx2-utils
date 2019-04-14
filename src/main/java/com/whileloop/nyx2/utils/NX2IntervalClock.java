/*
 * The MIT License
 *
 * Copyright 2019 Team whileLOOP.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.whileloop.nyx2.utils;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author sulochana
 */
public class NX2IntervalClock implements Runnable {

    public interface NX2IntervalClockCallback {

        void OnInterval(NX2IntervalClock clock);
    }

    private final NX2IntervalClockCallback callback;
    private ScheduledFuture shedule = null;
    private final NioEventLoopGroup attachedEventLoop;
    private boolean isPaused;
    private int interval;
    private TimeUnit timeUnit;

    public NX2IntervalClock(NioEventLoopGroup eventLoop, NX2IntervalClockCallback callback, int interval, TimeUnit unit) {
        this.callback = callback;
        this.attachedEventLoop = eventLoop;
        this.setDelay(interval, unit);
        this.isPaused = false;
    }

    @Override
    public void run() {
        if (this.isPaused) {
            return;
        }
        this.callback.OnInterval(this);
    }

    public void stop() {
        this.shedule.cancel(false);
    }

    public boolean isIsPaused() {
        return this.isPaused;
    }

    public void pause() {
        this.isPaused = true;
    }

    public void resume() {
        this.isPaused = false;
    }

    public void setDelay(int interval, TimeUnit unit) {
        if (this.shedule != null) {
            this.shedule.cancel(false);
        }
        this.interval = interval;
        this.timeUnit = unit;
        shedule = this.attachedEventLoop.scheduleWithFixedDelay(this, interval, interval, unit);
    }

    public long getIntervalToSeconds() {
        return this.timeUnit.toMillis(interval);
    }

}

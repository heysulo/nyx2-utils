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

import java.text.SimpleDateFormat;
import java.util.Date;

public class NyxLogger {

    public enum Loglevel {

        DEBUG(3),
        INFO(2),
        WARN(1),
        CRIT(0);

        public final int value;

        private Loglevel(int value) {
            this.value = value;
        }
    }

    private int verboseLevel = Loglevel.INFO.value;

    protected void debug(String message) {
        debug("%s", message);
    }

    protected void debug(String format, Object... args) {
        if (verboseLevel < Loglevel.DEBUG.value) {
            return;
        }
        formatter("DEBG", String.format(format, args));
    }

    protected void info(String message) {
        info("%s", message);
    }

    protected void info(String format, Object... args) {
        if (verboseLevel < Loglevel.INFO.value) {
            return;
        }
        formatter("INFO", String.format(format, args));
    }

    protected void warn(String message) {
        warn("%s", message);
    }

    protected void warn(String format, Object... args) {
        if (verboseLevel < Loglevel.WARN.value) {
            return;
        }
        formatter("WARN", String.format(format, args));
    }

    protected void crit(String message) {
        crit("%s", message);
    }

    protected void crit(String format, Object... args) {
        if (verboseLevel < Loglevel.CRIT.value) {
            return;
        }
        formatter("CRIT", String.format(format, args));
    }

    private void formatter(String logType, String formattedString) {
        System.out.println(String.format(
                "%s | %s | %s | %s | %s",
                getTimeStamp(),
                String.format("%s@%d", this.getClass().getSimpleName(), this.hashCode()),
                Thread.currentThread().getName(),
                logType,
                formattedString));
    }

    private String getTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd | HH:mm:ss.SSS z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public void setVerboseLevel(Loglevel level) {
        this.verboseLevel = level.value;

    }

}

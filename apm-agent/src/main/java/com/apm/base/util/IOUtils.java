package com.apm.base.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Jiang Qihong on 2018/4/9
 */
public final class IOUtils {

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
        }
    }
}

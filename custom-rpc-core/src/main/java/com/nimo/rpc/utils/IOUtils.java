package com.nimo.rpc.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.nimo.rpc.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64 加密工具
 */
public class Base64Utils {

    /**
     * 加密
     * @param s
     * @param charset
     * @return
     */
    public static String encode(String s, String charset){
        if (StringUtils.isEmpty(s)){
            return null;
        }
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodebytes = new byte[0];
        try {
            encodebytes = encoder.encode(s.getBytes("utf-8"));
            return new String(encodebytes,charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param s
     * @param charset
     * @return
     */
    public static String decode(String s,String charset){
        if (StringUtils.isEmpty(s)){
            return "";
        }
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodeBytes = decoder.decode(s);
        try {
            return new String(decodeBytes, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}

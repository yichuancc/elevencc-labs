package com.cc.lab9902.test06.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-29 20:49
 */
@Slf4j
public class LicenseInfoUtil {

    /**
     * 获取主机名
     */
    public static String getHostName() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有Mac地址
     */
    public static List<String> getMacAddressList() {
        List<String> macAddressList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    macAddressList.add(sb.toString());
                    System.out.println("MAC地址: " + sb.toString());
                }
            }
            return macAddressList;
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定时间格式的时间戳
     */
    public static long getTimestamp(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date expirationDate = dateFormat.parse(dateStr);
            return expirationDate.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

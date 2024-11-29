package com.cc.lab9902.test06.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-29 20:49
 */
@Slf4j
public class LicenseUtil {


    public static String generateLicenseNumber01(String status, String macAddress, String hostName, long startTime, long endTime) {
        try {
            String combinedInfo = macAddress + hostName + startTime + endTime;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(combinedInfo.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return (status + "-" + Long.toHexString(startTime) + "-" + sb + "-" + Long.toHexString(endTime)).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("生成序列号失败");
        }
        return null;
    }

    public static boolean validateLicenseNumber01(String assignedLicenseNumber, String macAddress, String hostName) {
        List<String> list = Arrays.asList(assignedLicenseNumber.split("-"));
        String status = list.get(0);
        long startTime = Long.parseLong(list.get(1), 16);
        long endTime = Long.parseLong(list.get(list.size() - 1), 16);
        if (StrUtil.equals("00A00", list.get(0))) {
            if (endTime < new Date().getTime()) {
                return false;
            }
        }
        String regeneratedLicenseNumber = generateLicenseNumber01(status, macAddress, hostName, startTime, endTime);
        return assignedLicenseNumber.equalsIgnoreCase(regeneratedLicenseNumber);
    }
}

package com.cc.lab9902.test06.license;

import com.cc.lab9902.test06.utils.LicenseUtil;

import java.util.Date;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2024-11-29 21:19
 */
public class ValidateLicenseTest {
    public static void main(String[] args) {
        String permanent = "11C22";
        String temporary = "00A00";
        String licenseNumber01 = LicenseUtil.generateLicenseNumber01(permanent, "84-7B-57-42-06-35", "eleven", new Date().getTime(), new Date().getTime());
        System.out.println(licenseNumber01);
        assert licenseNumber01 != null;
        boolean b = LicenseUtil.validateLicenseNumber01(licenseNumber01, "84-7B-57-42-06-35", "eleven");
        System.out.println(b);
    }
}

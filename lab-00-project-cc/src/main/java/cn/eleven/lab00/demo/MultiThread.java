package cn.eleven.lab00.demo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-09-16 18:22
 */
public class MultiThread {
    public static void main(String[] args) {
//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
//        for (ThreadInfo info : threadInfos) {
//            System.out.println(info.getThreadId() + ": " + info.getThreadName());
//        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

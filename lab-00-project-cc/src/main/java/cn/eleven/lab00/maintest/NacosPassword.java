package cn.eleven.lab00.maintest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @program: elevencc-labs
 * @description:
 * @author: yic
 * @create: 2023-07-24 10:44
 */
public class NacosPassword {

    /**
     * 1. V7@K#9D!F3Z2
     * 2. X5$G#1J@T9R6
     * 3. R4!P#7C9Q@L2
     * 4. M2@F#6R3G8H1
     * 5. D9!H#2J7K@T6
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("NACOS@szjyzy2023"));
    }
}

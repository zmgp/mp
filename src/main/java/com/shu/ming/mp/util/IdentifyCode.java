package com.shu.ming.mp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


@Component
@Slf4j
public class IdentifyCode {


    /*生成6位字母数字验证码*/
    public static String code() {
        Random random = new Random();
        StringBuilder sendCode =new StringBuilder();
        int[] nums = new int[6];
        for (int i = 0; i < 6; i++) {
            do {
                nums[i] = random.nextInt(123);
                if (nums[i] >= 0 && nums[i] <= 9) {
                    sendCode.append(nums[i]);
                    break;
                } else if (nums[i] >= 65 && nums[i] <= 90) {
                    char num = (char) nums[i];
                    sendCode.append(num);
                    break;
                } else if (nums[i] >= 97 && nums[i] <= 122) {
                    char num = (char) nums[i];
                    sendCode.append(num);
                    break;
                }
            } while (true);
        }
       return sendCode.toString();
    }
}

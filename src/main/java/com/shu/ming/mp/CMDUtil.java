package com.shu.ming.mp;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author JGod
 * @create 2021-11-12-12:42
 */
@Slf4j
public class CMDUtil {

    public static void main(String[] args) throws Exception {
        startRedisServer("D:\\Dai\\Redis-x64-3.2.100\\redis-server.exe D:\\Dai\\Redis-x64-3.2.100\\redis.windows6379.conf");
    }

    public static void startRedisServer(String cmd) throws Exception {
         cmd(cmd);
    }

    private static void cmd(String command) throws Exception{
        Process p = Runtime.getRuntime().exec(command);
        // 获取命令的输出流
        // 这里使用了BufferedReader进行输入，因为BufferedReader 有读取一行的方法，处理字符串比较方便
        BufferedReader out = new BufferedReader(
                new InputStreamReader(p.getInputStream(), "GBK"));
        String outline = null;
        while ((outline = out.readLine()) != null) {
            System.out.println(outline);
        }

        //// 获取命令的错误输出流
        BufferedReader err = new BufferedReader(
                new InputStreamReader(p.getErrorStream(), "GBK"));

        String errline = null;
        while ((errline = err.readLine()) != null) {
            System.out.println(errline);
        }

        // 导致当前线程等待，如有必要，一直要等到由该 Process
        // 对象表示的进程已经终止。如果已终止该子进程，此方法立即返回。如果没有终止该子进程，调用的线程将被阻塞，直到退出子进程，根据惯例，0 表示正常终止
        p.waitFor();

        System.out.println("状态值为：" + p.exitValue()); // 输出命令的返回值（执行状态，0为成功）
    }
}

package com.lots.lotswxxw.util;

import com.lots.lotswxxw.domain.vo.JsonResult;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* PortScanUtil
* @author: lots
* @date: 2021/4/6 15:36
*/
@SuppressWarnings({"ALL", "AlibabaThreadPoolCreation"})
public class PortScanUtil {
    boolean flag = true;
    List<String> list = new ArrayList<String>();

    public static void main(String[] args) {
        PortScanUtil portScanDemo = new PortScanUtil();

        String ip = "47.244.21.169";
        //方式2
        Set<Integer> portSet = new LinkedHashSet<Integer>();
        Integer[] ports = new Integer[]{21, 22, 23, 25, 26, 69, 80, 110, 143,
                443, 465, 995, 1080, 1158, 1433, 1521, 2100, 3128, 3306, 3389, 6379,
                7001, 8080, 8081, 8888, 9000, 9080, 9090, 9100, 9200, 9300, 9090, 43958};
        portSet.addAll(Arrays.asList(ports));
        portScanDemo.scanLargePorts(ip, portSet, 5, 800);
//        portScanDemo.scanLargePorts(ip,0,10000,5, 800);
    }

    public JsonResult getPort(String ip, Integer start, Integer end) {
        if (flag) {
            flag = false;
            try {
                if (start == 0 && end == 0) {
                    Set<Integer> portSet = new LinkedHashSet<Integer>();
                    Integer[] ports = new Integer[]{21, 22, 23, 25, 26, 69, 80, 110, 143,
                            443, 465, 995, 1080, 1158, 1433, 1521, 2100, 3128, 3306, 3389, 6379,
                            7001, 8080, 8081, 8888, 9080, 9090, 9100, 9200, 9300, 9090, 43958};
                    portSet.addAll(Arrays.asList(ports));
                    List<String> strings = scanLargePorts(ip, portSet, 5, 800);
                    return new JsonResult(strings);
                } else {
                    List<String> strings = scanLargePorts(ip, start, end, 5, 800);
                    return new JsonResult(strings);
                }
            } catch (Exception e) {
                return new JsonResult("出现不明原因错误" + e.getMessage());
            } finally {
                flag = true;
            }

        }

        return new JsonResult("其他ip正在被扫描");
    }

    /**
     * 多线程扫描目标主机一个段的端口开放情况
     *
     * @param ip           待扫描IP或域名,eg:180.97.161.184 www.zifangsky.cn
     * @param startPort    起始端口
     * @param endPort      结束端口
     * @param threadNumber 线程数
     * @param timeout      连接超时时间
     */
    public List<String> scanLargePorts(String ip, int startPort, int endPort,
                                       int threadNumber, int timeout) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNumber; i++) {
            ScanMethod1 scanMethod1 = new ScanMethod1(ip, startPort, endPort,
                    threadNumber, i, timeout);
            threadPool.execute(scanMethod1);
        }
        threadPool.shutdown();
        // 每秒中查看一次是否已经扫描结束
        while (true) {
            if (threadPool.isTerminated()) {
                System.out.println("扫描结束");
                return list;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 多线程扫描目标主机指定Set端口集合的开放情况
     *
     * @param ip           待扫描IP或域名,eg:180.97.161.184 www.zifangsky.cn
     * @param portSet      待扫描的端口的Set集合
     * @param threadNumber 线程数
     * @param timeout      连接超时时间
     */
    public List<String> scanLargePorts(String ip, Set<Integer> portSet,
                                       int threadNumber, int timeout) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNumber; i++) {
            ScanMethod2 scanMethod2 = new ScanMethod2(ip, portSet,
                    threadNumber, i, timeout);
            threadPool.execute(scanMethod2);
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                System.out.println("扫描结束");
                return list;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 扫描方式一：针对起始结束端口，进行逐个扫描
     */
    class ScanMethod1 implements Runnable {
        private String ip; // 目标IP
        private int startPort, endPort, threadNumber, serial, timeout; // 起始和结束端口，线程数，这是第几个线程，超时时间

        /**
         * 初始化
         *
         * @param ip           待扫描IP或域名
         * @param startPort    起始端口
         * @param endPort      结束端口
         * @param threadNumber 线程数
         * @param serial       标记是第几个线程
         * @param timeout      连接超时时间
         */
        public ScanMethod1(String ip, int startPort, int endPort,
                           int threadNumber, int serial, int timeout) {
            this.ip = ip;
            this.startPort = startPort;
            this.endPort = endPort;
            this.threadNumber = threadNumber;
            this.serial = serial;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            int port = 0;
            try {
                InetAddress address = InetAddress.getByName(ip);
                Socket socket;
                SocketAddress socketAddress;
                for (port = startPort + serial; port <= endPort; port += threadNumber) {
                    socket = new Socket();
                    socketAddress = new InetSocketAddress(address, port);
                    try {
                        socket.connect(socketAddress, timeout); // 超时时间
                        socket.close();

                        System.out.println("端口 " + port + " ：开放");
                        list.add("端口 " + port + " ：开放");
                    } catch (IOException e) {
                        // System.out.println("端口 " + port + " ：关闭");
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 扫描方式二：针对一个待扫描的端口的Set集合进行扫描
     */
    private class ScanMethod2 implements Runnable {
        private String ip; // 目标IP
        private Set<Integer> portSet; // 待扫描的端口的Set集合
        private int threadNumber, serial, timeout; // 线程数，这是第几个线程，超时时间

        public ScanMethod2(String ip, Set<Integer> portSet, int threadNumber,
                           int serial, int timeout) {
            this.ip = ip;
            this.portSet = portSet;
            this.threadNumber = threadNumber;
            this.serial = serial;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            int port = 0;
            Integer[] ports = portSet.toArray(new Integer[portSet.size()]); // Set转数组
            try {
                InetAddress address = InetAddress.getByName(ip);
                Socket socket;
                SocketAddress socketAddress;
                if (ports.length < 1) {
                    return;
                }
                for (port = 0 + serial; port <= ports.length - 1; port += threadNumber) {
                    socket = new Socket();
                    socketAddress = new InetSocketAddress(address, ports[port]);
                    try {
                        socket.connect(socketAddress, timeout);
                        socket.close();

                        System.out.println("端口 " + ports[port] + " ：开放");
                        list.add("端口 " + ports[port] + " ：开放");
                    } catch (IOException e) {
                        // System.out.println("端口 " + ports[port] + " ：关闭");
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }

    }
}

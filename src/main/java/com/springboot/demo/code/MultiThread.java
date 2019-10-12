package com.springboot.demo.code;

import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试多线程
 * 拆分文件  以及操作拆分文件
 */
public class MultiThread {

    private final static long DEFAULT_SIZE = 32L;  //默认32M进行分割

    private final static ExecutorService pool = Executors.newCachedThreadPool();

    private final static AtomicInteger count = new AtomicInteger();

    private static BlockingQueue<Runnable> queue =  new ArrayBlockingQueue<>(10);
    private static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(100,100,1,TimeUnit.SECONDS, queue);


    public static void main(String[] args){

        /*int num = splitFile("C:\\Users\\周升升\\Desktop\\map.txt");
        for(int i = 0; i < num; i++){
            pool.execute(() -> {
                handleData("C:\\Users\\周升升\\Desktop\\maptarget" + count.addAndGet(1) + ".txt");
            });
        }

        pool.shutdown();*/

//        handleBracket("{{},{},{},{[[{},{}]");
        for (int i = 0; i < 100; i++) {
            poolExecutor.execute(() -> {CSBUtils.getSignInfo();});
        }
        poolExecutor.shutdown();
    }

    private static void handleBracket(String content){
        content = content.trim();
        StringBuilder sb = new StringBuilder();
//        char[] open = new char[20];
//        int index = 0;
        for (int i = 0; i < content.length(); i++){
            char c = content.charAt(i);
            if(c == '{' || c == '['){
                sb.append(c);
//                open[index++] = c;
            }else if((c == '}' || c == ']' )&& sb.length() > 0){
//                if(open[index - 1] == '{' || open[index - 1] == '['){
//                    open[index--] = ' ';
//                }
                if(sb.charAt(sb.length() - 1) == '{' || sb.charAt(sb.length() - 1) == '['){
                    sb.deleteCharAt(sb.length() - 1);
                }
            }else{
                continue;
            }
        }
//        for (int i = index - 1; i >= 0; i--){
//            System.out.print((char)(open[i] + 2) + " ");
//        }
        System.out.println(sb.toString());
    }

    public static void handleData(String fileName){
        File file = new File(fileName);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拆分文件
     * @param targetFile
     */
    public static int splitFile(String targetFile){
        InputStream in = null;
        OutputStream out = null;
        int num = -1;
        try{
            //计算分割文件数量
            File file = new File(targetFile);
            if(!file.exists()){
                file.createNewFile();
                if(file.isDirectory()){
                    throw new IOException("指定路径不是文件!");
                }
            }
            Long size = file.length() >> 20; //1024*1024  MB

            if(size > DEFAULT_SIZE){
                num = (int) ((size & (DEFAULT_SIZE - 1)) == 0 ? (size >> 5) : ((size >> 5) + 1));
                if(num == 1) return num;
            }
            in = new FileInputStream(file);
            String fileName = file.getName();
            String ext = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < num; i++) {
                sb.delete(0, sb.length());
                File splitFile = new File(sb.append(file.getParent()).append("\\").
                        append(fileName).append("_data").append(i + 1).append(ext).toString());
                if(!splitFile.exists()){
                    splitFile.createNewFile();
                }
                byte[] data = new byte[8 * 1024 * 1024];
                out = new FileOutputStream(splitFile);
                int len = -1;
                while((len = in.read(data)) != -1){
                    out.write(data, 0, len);
                    if(splitFile.length() >> 20 >= DEFAULT_SIZE) break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(in != null){
                    in.close();
                }
                if(out!= null){
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return num;
    }
}

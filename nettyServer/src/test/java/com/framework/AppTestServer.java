package com.framework;

import com.framework.config.RedisUtils;
import com.framework.model.BusinessException;
import com.framework.model.ResultInfo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/1 18:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTestServer {

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisUtils redisUtils;

    @Test
    public void testRedisLock() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        String lockKey = "111";

        System.out.println(redisUtils.exists(lockKey));

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                RLock lock = redissonClient.getLock(lockKey);
                boolean lockFlag = false;
                try {
                    long l = System.currentTimeMillis();
                    lockFlag = lock.tryLock(10, 8, TimeUnit.SECONDS);
                    System.out.println(System.currentTimeMillis() - l);
                    if (!lockFlag) {//未获得锁
                        System.out.println(Thread.currentThread().getId() + "未获得锁");
                    } else {
                        System.out.println(Thread.currentThread().getId() + "获得锁");
                    }

                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getId() + "获取锁失败");
                    e.printStackTrace();
                } finally {

                    if(lockFlag){
                        lock.unlock();
                    }

               /*     try {
                        lock.unlock();
                    }catch (Exception e){

                    }*/
                }


            });
        }

        Thread.sleep(300000l);
    }


    /**
     * pdf 转word
     * @param args
     */
/*    public static void main(String[] args) {
        try{
            String pdfFile = "C:\\Users\\20190322\\Desktop\\1.pdf";
//            String pdfFile = "E:\\工作目录\\育新\\iSecure Center综合安防管理平台配置手册.PDF";
            PDDocument doc = PDDocument.load(new File(pdfFile));
            int pagenumber = doc.getNumberOfPages();
            pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
            String fileName = pdfFile + ".doc";
            File file = new File(fileName);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);// 排序
            stripper.setStartPage(1);// 设置转换的开始页
            stripper.setEndPage(pagenumber);// 设置转换的结束页
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
            System.out.println("pdf转换word成功！");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }*/
}

package com.zhang.specific;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static java.lang.System.out;

/**
 * Java 7 新特性
 *
 * @author yuyang.zhang
 */
public class Java7NewSpecific {

    public static void main(String[] args) {
        // 1. Switch增强  java7开始Switch可以使用字符串
        String select = "selectAll";
        switch (select) {
            case "selectOne":
                out.println("select One..");
                break;
            case "selectTwo":
                out.println("select two..");
                break;
            case "selectAll":
                out.println("select All...");
                break;
            default:
                out.println("default...");
        }

        // 2. 数值可加下划线(1_000_000)
        int money = 1_000_000_000;
        out.println(money);

        // 3. 二进制字面量
        int binaryNum = 0b1000;
        out.println("二进制字面量: "+ binaryNum);

        // 4. 泛型简化
        // 上下文推导 --> lambda
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        out.println("泛型简化:  "+ list);

        // 5. catch异常合并
        // java7 之前
        try{
            throw new NullPointerException();
        }catch (NullPointerException e){
            System.out.println("error...");
        }catch (IndexOutOfBoundsException e){
            System.out.println("error...");
        }
        // java7
        try{
            throw new NullPointerException();
            // 异常不能存在包含关系,否则就报错了
        }catch (NullPointerException | IndexOutOfBoundsException e){
            System.out.println("error...");
        }

        // 6. Try-with-resource
        // java.lang.AutoCloseable
        fileCopy(new File("source.txt"), new File("target.txt"));
        // java的自动关闭有一个问题  TODO

        // 7. fork-join
        // 思想: 大任务分解成小任务，然后小任务又可以继续分解，然后每个小任务分别计算出结果再合并起来，最后将汇总的结果作为大任务结果
        // Fork/Join采用“工作窃取模式”，当执行新的任务时他可以将其拆分成更小的任务执行，并将小任务加到线程队列中，然后再从一个随即线程中偷一个并把它加入自己的队列中
        // ForkJoinTask
        //   -- RecursiveTask 有返回值
        //   -- RecursiveAction 没有返回值
        forkJoinSum(0L,1_000_000_000_0L);
        normalSum(0L,1_000_000_000_0L);
    }

    /**
     * 文件拷贝
     * @param source 源文件
     * @param target 目标文件
     */
    private static void fileCopy(File source, File target){
        try (FileInputStream fileInput = new FileInputStream(source);
             FileOutputStream fileOutput = new FileOutputStream(target);){
            byte[] b = new byte[1024];
            int len = 0;
            while((len = fileInput.read(b)) != -1){
                fileOutput.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * fork join 测试
     */
    private static void forkJoinSum(long start,long end){
        long begin = System.currentTimeMillis();
        // 实现ForkJoin 就必须有ForkJoinPool的支持
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        MyForkJoinTask task = new MyForkJoinTask(start,end);
        long invoke = forkJoinPool.invoke(task);
        System.out.println("forkJoinSum invoke = " + invoke+"  time: " + (System.currentTimeMillis()-begin));
    }

    /**
     * 普通 测试
     */
    private static void normalSum(long start,long end){
        long begin = System.currentTimeMillis();
        long sum = 0L;
        for (long i = start; i <= end; i++) {
            sum+=i;
        }
        System.out.println("normalSum invoke = " + sum+"  time: " + (System.currentTimeMillis()-begin));
    }
}

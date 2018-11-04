package com.zhang.specific;

import java.util.concurrent.RecursiveTask;

/**
 * 自定义的fork-join 任务
 */
public class MyForkJoinTask extends RecursiveTask<Long> {

    //起始值
    private Long start;

    //结束值
    private Long end;

    // 临界值
    public static final Long THRESHOLD = 1_000_00L;

    public MyForkJoinTask(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - start) <= THRESHOLD) {
            // 如果拆分完毕就相加
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 没有拆分完毕就开始拆分
            // 计算的两个值的中间值
            Long middle = (end + start) / 2;
            // 拆分，并压入线程队列
            MyForkJoinTask right = new MyForkJoinTask(start, middle);
            right.fork();
            // 拆分，并压入线程队列
            MyForkJoinTask left = new MyForkJoinTask(middle + 1, end);
            left.fork();
            // 合并
            return right.join() + left.join();
        }
    }
}

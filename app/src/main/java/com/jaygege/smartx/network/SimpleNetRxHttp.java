//package com.jaygege.smartx.network;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import rx.Observable;
//import rx.android.schedulers.AndroidSchedulers;
//import rx.schedulers.Schedulers;
//
///**
// * Created by geyan on 2018/9/21
// * <p>
// * 线程池
// */
//public abstract class SimpleNetRxHttp extends AbstractNetRxHttp {
//
//    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
//    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
//    private static final int MAXIMUM_POOL_SIZE = 128;
//    private static final int KEEP_ALIVE = 1;
//
//    private static final BlockingQueue<Runnable> apiWorkQueue = new LinkedBlockingQueue<>();
//
//    private static final ThreadFactory apiThreadFactory = new ThreadFactory() {
//        private final AtomicInteger mCount = new AtomicInteger(1);
//
//        public Thread newThread(Runnable r) {
//            Thread thread = new Thread(r, "NetRxHttpThread #" + mCount.getAndIncrement());
//            thread.setPriority(Thread.NORM_PRIORITY - 1);
//            return thread;
//        }
//    };
//
//    public static final ThreadPoolExecutor apiExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
//            KEEP_ALIVE, TimeUnit.SECONDS, apiWorkQueue, apiThreadFactory);
//
//    /**
//     * 定义执行方法所在线程
//     */
//    @Override
//    protected Observable createObservable() {
//        return create().subscribeOn(Schedulers.from(apiExecutor)).observeOn(AndroidSchedulers.mainThread());
//    }
//
//    protected abstract Observable create();
//}

package com.didekin.rxjava;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.schedulers.Schedulers;

import static com.didekin.Utils.log;

/**
 * User: pedro@didekin
 * Date: 13/12/16
 * Time: 17:57
 */
public class InmediateScheduler {

    private static void doWorker(Scheduler.Worker worker)
    {
        log("Main start");

        worker.schedule(() -> {

                    log(" Outer start");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        log(e.getMessage());
                    }

                    worker.schedule(() -> {

                                log("  Inner start");

                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                    log(e.getMessage());
                                }
                                log("  Inner end");
                            }
                    );

                    log(" Outer end");
                }
        );
        log("Main end");
        worker.unsubscribe();
    }

    private static void checkInmediate()
    {
        Scheduler scheduler = Schedulers.immediate();
        Scheduler.Worker worker = scheduler.createWorker();
        doWorker(worker);
    }

    // The output is identical to that from checkInmediate_B.
    private static void checkInmediate_B()
    {
        log("Main start");
        log(" Outer start");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log(e.getMessage());
        }

        log("  Inner start");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log(e.getMessage());
        }

        log("  Inner end");
        log(" Outer end");
        log("Main end");

    }

    private static void checkTrampoline()
    {
        Scheduler scheduler = Schedulers.trampoline();
        Scheduler.Worker worker = scheduler.createWorker();
        doWorker(worker);
    }

    public static void main(String[] args)
    {
        log("============== Beginning checkInmediate() ============== ");
        checkInmediate();
        log("============== Beginning checkInmediate_B() ============== ");
        checkInmediate_B();
        log("============== Beginning checkTrampoline() ============== ");
        checkTrampoline();
    }
}

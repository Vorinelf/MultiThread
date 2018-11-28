
import static java.lang.Thread.sleep;

public interface MultiThread {
    static void main(String[] args) {
        boolean k = true;        //Если к=true, то ситуация deadlock, если k=false - код выполняется
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        Thread[] threads = new Thread[2];
        threads[0] = new Thread(new Runnable() {
            public void run() {
                synchronized (lock1) {
                    System.out.println("Поток 1 захватил монитор 1");
                    try {
                        while (k) // Здесь тоже можно менять "k" на "!k" для снятия deadlock
                        {
                            sleep(10);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Поток 1 пытается захватить монитор 2");
                }
                synchronized (lock2) {
                    System.out.println("Поток 1 захватил монитор 2");


                }
            }

        });
        threads[1] = new Thread(new Runnable() {
            public void run() {
                synchronized (lock2) {
                    System.out.println("Поток 2 захватил монитор 2");
                }
                System.out.println("Поток 2 пытается захватить монитор 1");
                synchronized (lock1) {
                    System.out.println("Поток 2 захватил монитор 1");

                }
            }

        });
        threads[0].start();
        threads[1].start();
    }
}


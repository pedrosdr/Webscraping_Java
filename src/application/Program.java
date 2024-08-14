package application;

import scraping.Scraping;

public class Program
{
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            Scraping scraping = new Scraping(
                    0, 8796,
                    "./escolas.csv",
                    "./percentuais_t1.csv",
                    "./chromedriver.exe",
                    "./progress_t1.txt",
                    "./exceptions_t1.txt",
                    "Thread 1",
                    "2023"
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            Scraping scraping = new Scraping(
                    8796, 17592,
                    "./escolas.csv",
                    "./percentuais_t2.csv",
                    "./chromedriver.exe",
                    "./progress_t2.txt",
                    "./exceptions_t2.txt",
                    "Thread 2",
                    "2023"
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            Scraping scraping = new Scraping(
                    17592, 26388,
                    "./escolas.csv",
                    "./percentuais_t3.csv",
                    "./chromedriver.exe",
                    "./progress_t3.txt",
                    "./exceptions_t3.txt",
                    "Thread 3",
                    "2023"
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t3.start();
//
//        Thread t4 = new Thread(() -> {
//            Scraping scraping = new Scraping(
//                    29139, 38852,
//                    "./escolas.csv",
//                    "./percentuais_t4.csv",
//                    "./chromedriver.exe",
//                    "./progress_t4.txt",
//                    "./exceptions_t4.txt",
//                    "Thread 4",
//                    "2023"
//            );
//            try {
//                scraping.execute();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        t4.start();
//
//        Thread t5 = new Thread(() -> {
//            Scraping scraping = new Scraping(
//                    3356, 4195,
//                    "./escolas_refatoracao.csv",
//                    "./percentuais_t5.csv",
//                    "./chromedriver.exe",
//                    "./progress_t5.txt",
//                    "./exceptions_t5.txt",
//                    "Thread 5"
//            );
//            try {
//                scraping.execute();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        t5.start();
//
//        Thread t6 = new Thread(() -> {
//            Scraping scraping = new Scraping(
//                    4195, 5032,
//                    "./escolas_refatoracao.csv",
//                    "./percentuais_t6.csv",
//                    "./chromedriver.exe",
//                    "./progress_t6.txt",
//                    "./exceptions_t6.txt",
//                    "Thread 6"
//            );
//            try {
//                scraping.execute();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        t6.start();


        t1.join();
//        t2.join();
//        t3.join();
//        t4.join();
//        t5.join();
//        t6.join();
    }
}

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

        t1.join();
        t2.join();
        t3.join();
    }
}

package application;

import scraping.Scraping;

public class Program
{
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Scraping scraping = new Scraping(
                    19042, 64869,
                    "./escolas.csv",
                    "./percentuais_t1.csv",
                    "./chromedriver.exe",
                    "./progress_t1.txt",
                    "./exceptions_t1.txt",
                    "Thread 1"
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
                    68131, 112257,
                    "./escolas.csv",
                    "./percentuais_t2.csv",
                    "./chromedriver.exe",
                    "./progress_t2.txt",
                    "./exceptions_t2.txt",
                    "Thread 2"
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
                    114525, 159644,
                    "./escolas.csv",
                    "./percentuais_t3.csv",
                    "./chromedriver.exe",
                    "./progress_t3.txt",
                    "./exceptions_t3.txt",
                    "Thread 3"
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t3.start();
    }
}

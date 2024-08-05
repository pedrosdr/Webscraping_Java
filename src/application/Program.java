package application;

import scraping.Scraping;

public class Program
{
    public static void main(String[] args) throws InterruptedException {

        Thread t4 = new Thread(() -> {
            Scraping scraping = new Scraping(
                    0, 24985,
                    "./escolas_t4.csv",
                    "./percentuais_t4.csv",
                    "./chromedriver.exe",
                    "./progress_t4.txt",
                    "./exceptions_t4.txt",
                    "Thread 4"
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t4.start();

        Thread t5 = new Thread(() -> {
            Scraping scraping = new Scraping(
                    0, 24985,
                    "./escolas_t5.csv",
                    "./percentuais_t5.csv",
                    "./chromedriver.exe",
                    "./progress_t5.txt",
                    "./exceptions_t5.txt",
                    "Thread 5"
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t5.start();

        Thread t6 = new Thread(() -> {
            Scraping scraping = new Scraping(
                    0, 24990,
                    "./escolas_t6.csv",
                    "./percentuais_t6.csv",
                    "./chromedriver.exe",
                    "./progress_t6.txt",
                    "./exceptions_t6.txt",
                    "Thread 6"
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t6.start();
    }
}

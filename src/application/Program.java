package application;

import scraping.ScrapingAmostras;
import scraping.TipoAmostra;

public class Program
{
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            ScrapingAmostras scraping = new ScrapingAmostras(
                    0, 2,
                    "./escolas.csv",
                    "./percentuais_t1.csv",
                    "./chromedriver.exe",
                    "./progress_t1.txt",
                    "./exceptions_t1.txt",
                    "Thread 1",
                    "2023",
                    TipoAmostra.BRASIL
            );
            try {
                scraping.execute();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();

//        Thread t2 = new Thread(() -> {
//            ScrapingAmostras scraping = new ScrapingAmostras(
//                    3938, 7876,
//                    "./escolas.csv",
//                    "./percentuais_t2.csv",
//                    "./chromedriver.exe",
//                    "./progress_t2.txt",
//                    "./exceptions_t2.txt",
//                    "Thread 2",
//                    "2023",
//                    TipoAmostra.BRASIL
//            );
//            try {
//                scraping.execute();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        t2.start();
//
//        Thread t3 = new Thread(() -> {
//            ScrapingAmostras scraping = new ScrapingAmostras(
//                    7876, 11813,
//                    "./escolas.csv",
//                    "./percentuais_t3.csv",
//                    "./chromedriver.exe",
//                    "./progress_t3.txt",
//                    "./exceptions_t3.txt",
//                    "Thread 3",
//                    "2023",
//                    TipoAmostra.BRASIL
//            );
//            try {
//                scraping.execute();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        t3.start();



        t1.join();
//        t2.join();
//        t3.join();
    }
}

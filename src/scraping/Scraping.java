package scraping;

import data.DataFrame;
import data.DataRow;
import data.DataUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Scraping
{
    private int fromIndex;
    private int toIndex;
    private String outputPath;
    private String inputPath;
    private String driverPath;
    private String logPath;
    private String thread;
    private String exceptionPath;
    private DataFrame percentuais_saeb;
    private String ano;

    public Scraping(
            int fromIndex,
            int toIndex,
            String inputPath,
            String outputPath,
            String driverPath,
            String logPath,
            String exceptionPath,
            String thread,
            String ano
    )
    {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.driverPath = driverPath;
        this.logPath = logPath;
        this.thread = thread;
        this.exceptionPath = exceptionPath;
        this.percentuais_saeb = new DataFrame();
        this.ano = ano;
    }

    public void execute() throws InterruptedException {
        List<String> base = DataUtils.getCodes(inputPath);
        String[] codigos = base.subList(fromIndex, toIndex).toArray(new String[0]);
        int dataCount = 0;
        long startTime = System.currentTimeMillis();

        System.setProperty("webdriver.chrome.driver", driverPath);

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        for(int i = 0; i < codigos.length;)
        {
            String codigo = codigos[i];
            try
            {
                driver.get(String.format("http://saeb.inep.gov.br/saeb/resultado-final-externo/boletim?anoProjeto=%s&coEscola=%s", this.ano, codigo));
//                WebElement anchor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dados-escola']")));

                // Verificar se há dados
                WebElement anchor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='mat-tab-body-wrapper']")));

                if(!anchor.getAttribute("innerHTML").contains("Nível 0"))
                {
                    i++;
                    dataCount++;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String dtString = LocalDateTime.now().format(dtf);
                    long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // em segundos
                    double speed = 3600.0 * dataCount / elapsedTime;
                    int recordsLeft = codigos.length - dataCount;
                    double hoursLeft = recordsLeft / speed;

                    StringBuilder sb = new StringBuilder();
                    sb.append(" --> ")
                            .append("! Not found on (").append(this.thread).append("), ")
                            .append(codigo)
                            .append(", ").append(String.format("%.2f", 100.0 * dataCount / codigos.length)).append("%")
                            .append(", ").append(dataCount).append(" records")
                            .append(", ").append(String.format("%d", elapsedTime)).append(" s")
                            .append(", ").append(String.format("%.2f", hoursLeft)).append(" hours left")
                            .append(", ").append(String.format("%.1f", speed)).append(" records/hour")
                            .append(", ").append(dtString);
                    System.out.println(sb.toString());
                    DataUtils.logProgress(logPath, sb.toString());
                    if(dataCount % 10 == 0)
                    {
                        percentuais_saeb.toCSV(outputPath);
                        System.out.println("   |--> (" + this.thread + "} Arquivo CSV salvo.");
                        DataUtils.logProgress(logPath, "   |--> (" + this.thread + "} Arquivo CSV salvo.");
                    }
                    continue;
                }

                List<String> disciplinas = new ArrayList<>();
                List<String> etapas = new ArrayList<>();

                WebElement abas_disciplina = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"cdk-accordion-child-2\"]/div/mat-tab-group/mat-tab-header/div[2]/div/div")));

                if(abas_disciplina.getAttribute("innerHTML").contains("Matemática")) {
                    disciplinas.add("Matemática");
                }

                if(abas_disciplina.getAttribute("innerHTML").contains("Língua Portuguesa")) {
                    disciplinas.add("Língua Portuguesa");
                }

                WebElement abas_etapas = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"mat-tab-content-0-0\"]/div/div/mat-tab-group/mat-tab-header/div[2]/div/div")));
                if(abas_etapas.getAttribute("innerHTML").contains("5º ano")) {
                    etapas.add("5º ano");
                }

                if(abas_etapas.getAttribute("innerHTML").contains("9º ano")) {
                    etapas.add("9º ano");
                }

                for(String disciplina : disciplinas)
                {
                    WebElement aba_disciplina = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//div[@class=\"mat-tab-label-content\"][text()[contains(., \"%s\")]]", disciplina))));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", aba_disciplina);

                    for(int index_etapa = 0; index_etapa < etapas.size();)
                    {
                        String etapa = etapas.get(index_etapa);

                        WebElement aba_etapa = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//div[@class=\"mat-tab-label-content\"][text()[contains(., \"%s\")]]", etapa))));
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", aba_etapa);

                        Thread.sleep(1000);
                        WebElement tr = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[.//th[text()=\"Nível 0\"]]/tbody/tr[1]")));
                        List<WebElement> tds = tr.findElements(By.xpath(".//child::td"));

                        DataFrame df = new DataFrame();
                        for(int j = 1; j < tds.size(); j++)
                        {
                            WebElement td = tds.get(j);
                            DataRow row = new DataRow();
                            row.setCodigo(codigo);
                            row.setAno(this.ano);
                            row.setEtapa(etapa);
                            row.setDisciplina(disciplina);
                            row.setPercentual(td.getAttribute("innerHTML").trim().replace("%", ""));
                            row.setNivel(String.valueOf(j-1));
                            df.append(row);
                        }

                        if(disciplina.equals("Língua Portuguesa") && etapa.equals("5º ano") && df.size() != 10)
                        {
                            System.out.println("        LP AI: número de nívels diferente de 10 - Continuando com a mesma etapa");
                            continue;
                        }

                        if(disciplina.equals("Língua Portuguesa") && etapa.equals("9º ano") && df.size() != 9)
                        {
                            System.out.println("        LP AF: número de nívels diferente de 9 - Continuando com a mesma etapa");
                            continue;
                        }

                        if(disciplina.equals("Matemática") && etapa.equals("5º ano") && df.size() != 11)
                        {
                            System.out.println("        MAT AI: número de nívels diferente de 11 - Continuando com a mesma etapa");
                            continue;
                        }

                        if(disciplina.equals("Matemática") && etapa.equals("9º ano") && df.size() != 10)
                        {
                            System.out.println("        MAT AF: número de nívels diferente de 10 - Continuando com a mesma etapa");
                            continue;
                        }

                        percentuais_saeb.concat(df);
                        index_etapa++;
                    }
                }

                dataCount++;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String dtString = LocalDateTime.now().format(dtf);
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // em segundos
                double speed = 3600.0 * dataCount / elapsedTime;
                int recordsLeft = codigos.length - dataCount;
                double hoursLeft = recordsLeft / speed;

                StringBuilder sb = new StringBuilder();
                sb.append(" --> ")
                        .append("(").append(this.thread).append("), ")
                        .append(codigo)
                        .append(", ").append(String.format("%.2f", 100.0 * dataCount / codigos.length)).append("%")
                        .append(", ").append(dataCount).append(" records")
                        .append(", ").append(String.format("%d", elapsedTime)).append(" s")
                        .append(", ").append(String.format("%.2f", hoursLeft)).append(" hours left")
                        .append(", ").append(String.format("%.1f", speed)).append(" records/hour")
                        .append(", ").append(dtString);
                System.out.println(sb.toString());
                DataUtils.logProgress(logPath, sb.toString());

                if(dataCount % 10 == 0)
                {
                    percentuais_saeb.toCSV(outputPath);
                    System.out.println("   |--> (" + this.thread + "} Arquivo CSV salvo.");
                    DataUtils.logProgress(logPath, "   |--> (" + this.thread + "} Arquivo CSV salvo.");
                }
                i++;
            }
            catch(Exception ex)
            {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String dtString = LocalDateTime.now().format(dtf);
                String message = "--> (" + dtString + ")"+ "  !!! Exceção na (" + this.thread + "), continuando do mesmo Código: " + codigo + ", Mensagem: " + ex.getMessage();
                System.out.println(message);
                DataUtils.logProgress(this.exceptionPath, message);
            }
        }

        percentuais_saeb.toCSV(outputPath);
        System.out.println("End!");
        DataUtils.logProgress(logPath, "(" + this.thread + "} End.");
    }
}

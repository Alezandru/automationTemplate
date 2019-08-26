package serenity.tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import serenity.steps.GoogleSteps;
import serenity.utils.Waitings;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


@RunWith(SerenityRunner.class)
public class GoogleTest {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public GoogleSteps googleSteps;

    Waitings wait;

    @Before
    public void setUp() {
        webdriver.manage().window().maximize();
    }

    @Test
    @Ignore
    @Issue("Test to test the framework is working.")
    public void searchForTermInGoogle() {
        String SEARCH_TERM = "TEST";
        googleSteps.googlePageIsOpen();
        googleSteps.searchForTermInGoogle(SEARCH_TERM);
        googleSteps.checkTheResultPage(SEARCH_TERM);
    }

    @Test
    @Issue("Interacting with BOM objects: Browser Alert popups")
    public void cancelBrowserAlerts() {
        // Test strings
        String alertText = "Test text for ALERT popup";
        // Test page is open
        googleSteps.googlePageIsOpen();
        // Creating a Javascript Executor object
        JavascriptExecutor jse = (JavascriptExecutor) webdriver;

        // Creating a Alert popup
        jse.executeScript("alert(\"" + alertText + "\")");
        wait.waitForSeconds(1);
        // Getting Alert popup text
        String alertTextFromBrowser = webdriver.switchTo().alert().getText();
        // Asserting the Alert popup text
        Assert.assertEquals(alertText, alertTextFromBrowser);
        // "Accept" the Alert popup
        webdriver.switchTo().alert().accept();
        // "Dismiss" the Alert popup
//        webdriver.switchTo().alert().dismiss();

    }

    @Test
    @Issue("Interacting with BOM objects: Browser Prompt popups")
    public void cancelBrowserPrompt() {
        // Test strings
        String promptText = "Test text for PROMPT popup";
        // Test page is open
        googleSteps.googlePageIsOpen();
        // Creating a Javascript Executor object
        JavascriptExecutor jse = (JavascriptExecutor) webdriver;

        // Creating a Prompt popup
        jse.executeScript("prompt(\"" + promptText + "\")");
        wait.waitForSeconds(1);
        // Getting Alert popup text
        String promptTextFromBrowser = webdriver.switchTo().alert().getText();
        // Asserting the Alert popup text
        Assert.assertEquals(promptText, promptTextFromBrowser);
        // "Accept" the Prompt popup
        webdriver.switchTo().alert().accept();
        // "Dismiss" the Prompt popup
//        webdriver.switchTo().alert().dismiss();

    }

    @Test
    @Issue("Interacting with BOM objects: Browser Confirmation popups")
    public void cancelBrowserConfirm() {
        // Test string
        String confirmText = "Test text for CONFIRMATION popup";
        // Test page is open
        googleSteps.googlePageIsOpen();
        // Creating a Javascript Executor object
        JavascriptExecutor jse = (JavascriptExecutor) webdriver;

        // Creating a Confirmation popup
        jse.executeScript("confirm(\"" + confirmText + "\")");
        wait.waitForSeconds(1);
        // Getting Alert popup text
        String confirmTextFromBrowser = webdriver.switchTo().alert().getText();
        // Asserting the Alert popup text
        Assert.assertEquals(confirmText, confirmTextFromBrowser);
        // "Accept" the Confirmation popup
//        webdriver.switchTo().alert().accept();
        // "Dismiss" the Confirmation popup
        webdriver.switchTo().alert().dismiss();

        webdriver.quit();

    }

    @Test
    public void scheduledTest1() throws InterruptedException {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "\n"
                        + "Tread's name: " + Thread.currentThread().getName());
                cancelBrowserConfirm();
            }
        };
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        Timer timer = new Timer("Timer");
        long delay = 2000L;
        long period = 1000L * 60L * 60L * 24L;
        long period2 = 1000L * 10L;

//        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        executor.scheduleAtFixedRate(repeatedTask, delay, period2, TimeUnit.MILLISECONDS);
        Thread.sleep(delay + period2 * 10);
        executor.shutdown();
    }

}

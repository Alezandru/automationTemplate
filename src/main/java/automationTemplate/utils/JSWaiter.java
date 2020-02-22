package automationTemplate.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

@Slf4j
public class JSWaiter {

    /*
     * Waits for testing SPA web-apps
     *
     * 'document.readyState' - describes the loading state of the document
     * loading - document is still loading
     * interactive - document has finished loading but images, stylesheets and frames are still loading
     * complete - document and all sub-resources have finished loading
     *
     * jQuery library - event handling, CSS animations, Ajax, etc.
     * 'jQuery.active' - if no ajax calls are running 'jQuery.active==0'
     *
     * For Angular apps check if 'AngularJS' is available and has finished all requests
     *
     * */

    private static WebDriver jsWaitDriver;
    private static WebDriverWait jsWait;
    private static JavascriptExecutor jsExec;

    public JSWaiter() {
        setDriver(getDriver());
    }

    //Get the driver:
    public static void setDriver(WebDriver driver) {
        jsWaitDriver = driver;
        jsWait = new WebDriverWait(jsWaitDriver, 10);
        jsExec = (JavascriptExecutor) jsWaitDriver;
    }

    private void waitForJQueryLoad() {
        try {
            ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) getDriver())
                    .executeScript("return jQuery.active") == 0);

            boolean jqueryReady = (Boolean) jsExec.executeScript("return jQuery.active==0");

            if (!jqueryReady) {
                jsWait.until(jQueryLoad);
            }

        } catch (WebDriverException ignored) {
        }
    }

    /*
    *
    * */
    private void waitForJSLoad() {

        try {
            ExpectedCondition<Boolean> jsLoad = driver -> (((JavascriptExecutor) getDriver())
                    .executeScript("return document.readyState")
                    .toString().equals("complete"));

            boolean jqueryReady = jsExec.executeScript("return document.readyState")
                    .toString().equals("complete");

            if (!jqueryReady) {
                jsWait.until(jsLoad);
            }
        } catch (WebDriverException ignored) {
        }
    }

//    private boolean waitForJStoLoad() {
//
//        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
//        // wait for jQuery
//        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
//            @Override
//            public Boolean apply(WebDriver webDriver) {
//                try {
//                    return ((Long) jsExec.executeScript("return jQuery.active") == 0);
//                } catch (WebDriverException ignored) {
//                    return true;
//                }
//            }
//        };
//        // wait for Javascript
//        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
//            @Override
//            public Boolean apply(WebDriver webDriver) {
//                return jsExec.executeScript("return document.readyState")
//                        .toString().equals("complete");
//            }
//        };
//
//        return wait.until(jQueryLoad) && wait.until(jsLoad);
//    }

    private void waitForAngularLoad() {
        String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
        angularLoads(angularReadyScript);
    }

    private void waitUntilJSReady() {
        boolean jsReady = !jsExec.executeScript("return document.readyState")
                .toString().equals("complete");
        if (jsReady) {
            pool(20);

            waitForJSLoad();

            pool(20);
        }
    }

    private void waitUntilJQueryReady() {
        Boolean jQueryDefined = (Boolean) jsExec.executeScript("return typeof jQuery != 'undefined'");
        if (jQueryDefined) {
            pool(20);

            waitForJQueryLoad();

            pool(20);
        }
    }

    private void waitUntilAngularReady() {
        try {
            Boolean angularUnDefined = (Boolean) jsExec.executeScript("return window.angular === undefined");
            if (!angularUnDefined) {
                Boolean angularInjectorUnDefined = (Boolean) jsExec.executeScript("return angular.element(document).injector() === undefined");
                if (!angularInjectorUnDefined) {
                    pool(20);

                    waitForAngularLoad();
                    waitForAngularLoad();

                    pool(20);
                }
            }
        } catch (WebDriverException ignored) {
        }
    }

    private void waitUntilAngular5Ready() {
        try {
            Object angular5Check = jsExec.executeScript("return getAllAngularRootElements()[0].attributes['ng-version']");
            if (angular5Check != null) {
                Boolean angularPageLoaded = (Boolean) jsExec.executeScript("return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1");
                if (!angularPageLoaded) {
                    pool(20);

                    waitForAngular5Load();

                    pool(20);
                }
            }
        } catch (WebDriverException ignored) {
        }
    }

    private void waitForAngular5Load() {
        String angularReadyScript = "return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1";
        angularLoads(angularReadyScript);
    }

    private void angularLoads(String angularReadyScript) {
        try {
            ExpectedCondition<Boolean> angularLoad = driver -> Boolean.valueOf(((JavascriptExecutor) driver)
                    .executeScript(angularReadyScript).toString());

            boolean angularReady = Boolean.valueOf(jsExec.executeScript(angularReadyScript).toString());

            if (!angularReady) {
                jsWait.until(angularLoad);
            }
        } catch (WebDriverException ignored) {
        }
    }

    public void waitAllRequests() {

    }

    private void pool(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

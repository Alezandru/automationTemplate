package serenity.tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import serenity.steps.GoogleSteps;


@RunWith(SerenityRunner.class)
public class GoogleTest {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public GoogleSteps googleSteps;

    @Before
    public void setUp(){
        webdriver.manage().window().maximize();
    }

    @Test
    public void searchForTermInGoogle(){
        String SEARCH_TERM = "TEST";
        googleSteps.googlePageIsOpen();
        googleSteps.searchForTermInGoogle(SEARCH_TERM);
        googleSteps.checkTheResultPage(SEARCH_TERM);
    }

    @Test
    @Issue("#WIKI-1")
    public void cancelAlertsFromBom(){
        googleSteps.googlePageIsOpen();
    }

}

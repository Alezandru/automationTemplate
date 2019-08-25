package serenity.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://www.google.com/")
public class GooglePage extends PageObject {

    @FindBy(name = "q")
    private WebElementFacade searchBar;

    public void searchForTerm(String searchCriteria) {
        searchBar.sendKeys(searchCriteria);
        searchBar.sendKeys(Keys.ENTER);
    }

    public void verityResultsPageTitle(String containedText){
        String pageText = getDriver().getTitle();
        Assert.assertTrue(pageText.contains(containedText));
    }


}

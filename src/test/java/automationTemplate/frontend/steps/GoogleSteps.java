package automationTemplate.frontend.steps;

import net.thucydides.core.annotations.Step;
import automationTemplate.frontend.pages.GooglePage;

public class GoogleSteps {

    GooglePage googlePage;

    @Step
    public void googlePageIsOpen(){
        googlePage.open();
    }

    @Step
    public void searchForTermInGoogle(String searchCriteria){
        googlePage.searchForTerm(searchCriteria);
    }

    @Step
    public void checkTheResultPage(String containedText){
        googlePage.verityResultsPageTitle(containedText);
    }
}

package serenity.utils;

import net.serenitybdd.core.pages.PageObject;

public class Waitings extends PageObject {

    public void waitForSeconds(int seconds){
        waitABit(seconds * 1000);
    }

}

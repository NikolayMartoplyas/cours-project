package ru.netology.page;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CashPurchasePage {
    private final SelenideElement form = $(".form");
    private final SelenideElement payment = $(byText("Оплата по карте"));
    private final ElementsCollection inputsColection = form.$$("input");
    private final SelenideElement buttonNext = form.$(byText("Продолжить"));
    private final SelenideElement messageSuccessful = $(".notification_status_ok .notification__content");
    private final SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private final SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private final SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private final SelenideElement expiredCardError = $(byText("Истек срок действия карты")).parent().$(".input__sub");
    private final SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private final SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");


    public void enterCardInfo(int index, String value){
        inputsColection.get(index).setValue(value);
    }
    public void invalidCardInfo(DataHelper.CardInfo info){
        enterCardInfo(0, info.getNumberCard());
        enterCardInfo(1, info.getMonth());
        enterCardInfo(2, info.getYear());
        enterCardInfo(3, info.getOwner());
        enterCardInfo(4, info.getCvc());
        buttonNext.click(ClickOptions.withTimeout(Duration.ofSeconds(30)));
    }
    public void emptyFieldNumberCard(DataHelper.CardInfo info){
        enterCardInfo(0, "");
        enterCardInfo(1, info.getMonth());
        enterCardInfo(2, info.getYear());
        enterCardInfo(3, info.getOwner());
        enterCardInfo(4, info.getCvc());
        buttonNext.click();
    }
    public void emptyFieldMonth(DataHelper.CardInfo info){
        enterCardInfo(0, info.getNumberCard());
        enterCardInfo(1, "");
        enterCardInfo(2, info.getYear());
        enterCardInfo(3, info.getOwner());
        enterCardInfo(4, info.getCvc());
        buttonNext.click();
    }
    public void emptyFieldYear(DataHelper.CardInfo info){
        enterCardInfo(0, info.getNumberCard());
        enterCardInfo(1, info.getMonth());
        enterCardInfo(2, "");
        enterCardInfo(3, info.getOwner());
        enterCardInfo(4, info.getCvc());
        buttonNext.click();
    }
    public void emptyFieldOwner(DataHelper.CardInfo info){
        enterCardInfo(0, info.getNumberCard());
        enterCardInfo(1, info.getMonth());
        enterCardInfo(2, info.getYear());
        enterCardInfo(3, "");
        enterCardInfo(4, info.getCvc());
        buttonNext.click();
    }
    public void emptyFieldCVC(DataHelper.CardInfo info){
        enterCardInfo(0, info.getNumberCard());
        enterCardInfo(1, info.getMonth());
        enterCardInfo(2, info.getYear());
        enterCardInfo(3, info.getOwner());
        enterCardInfo(4, "");
        buttonNext.click();
    }

    public void validCardInfo(DataHelper.CardInfo info){
        invalidCardInfo(info);
        messageSuccessful.shouldBe(visible, Duration.ofSeconds(15));
    }
    public void popUpMessage(String expectedText){
        cardNumberError.shouldHave(text(expectedText))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
    public void errorNumberCard(String expectedText){
        cardNumberError.shouldHave(text(expectedText))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
    public void errorMonth(String expectedText){
        monthError.shouldHave(text(expectedText))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
    public void errorYear(String expectedText){
        yearError.shouldHave(text(expectedText))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
    public void cardHasExpired(String expectedText){
        expiredCardError.shouldHave(text(expectedText))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
    public void errorOwner(String expectedText){
        ownerError.shouldHave(text(expectedText))
                .shouldBe(visible, Duration.ofSeconds(15));
    }
    public void errorCVC(String expectedText){
        cvcError.shouldHave(text(expectedText))
                .shouldBe(visible, Duration.ofSeconds(15));
    }

}

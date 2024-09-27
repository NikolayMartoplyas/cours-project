package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {
    private final SelenideElement purchaseButton = $(byText("Купить"));
    private final SelenideElement payment = $(byText("Оплата по карте"));


    public CashPurchasePage clickOnBuy() {
        purchaseButton.click();
        payment.shouldBe(visible, Duration.ofSeconds(15));
        return new CashPurchasePage();
    }
}

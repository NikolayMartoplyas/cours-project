package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CashPurchasePage;
import ru.netology.page.HomePage;
import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class FormTest {
    CashPurchasePage cashPurchasePage;
    HomePage homePage;

    protected void initialize() {
        homePage = new HomePage();
        cashPurchasePage = homePage.clickOnBuy();
    }


    //перед запуском всех тестов
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    //после завершения всех тестов
    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
        cleanDatabase();
    }
    // перед каждым тестом  в классе
    @BeforeEach
    void setUp(){
        cashPurchasePage = open("http://localhost:8080", CashPurchasePage.class);
        Configuration.timeout = 10000;
    }
    //запрос в базу данных на проверку сумму покупки
    @Test
    void mustCheckThatThePurchaseAmountIsAppropriate(){
        var approved = DataHelper.makingPurchase();
        initialize();
        cashPurchasePage.validCardInfo(approved);
        Integer amount = SQLHelper.getPaymentAmount();
        String amountStr = amount.toString();
        String expectedAmount = "45_000";
        assertEquals(expectedAmount, amountStr);
    }

    // валидный тест карты approved
    @Test
    void theCardDataFormMustBeFilledOut(){
        var approved = DataHelper.getCardInfoApproved();
        initialize();
        cashPurchasePage.validCardInfo(approved);

    }
    // валидный тест карты decliend
    @Test
    void theCardTransactionShouldBeRejected(){
        var decliend = DataHelper.getCardInfoDecliend();
        initialize();
        cashPurchasePage.invalidCardInfo(decliend);
        cashPurchasePage.popUpMessage("Карта заблокирована, операция отклонена банком");
    }
    //невалидный формат карты
    @Test
    void invalidNumberCard(){
        var approved = DataHelper.invalidCard();
        initialize();
        cashPurchasePage.invalidCardInfo(approved);
        cashPurchasePage.errorNumberCard("Неверный формат");
    }
    //невалидный формат месяц
    @Test
    void invalidMonth(){
        var approved = DataHelper.invalidMonth();
        initialize();
        cashPurchasePage.invalidCardInfo(approved);
        cashPurchasePage.errorMonth("Неверно указан срок действия карты");
    }
    //невалидный формат год
    @Test
    void invalidYear(){
        var approved = DataHelper.invalidYear();
        initialize();
        cashPurchasePage.invalidCardInfo(approved);
        cashPurchasePage.errorYear("Истёк срок действия карты");
    }
    //поле владелец на Русском
    @Test
    void invalidOwner(){
        var approved = DataHelper.invalidOwner();
        initialize();
        cashPurchasePage.invalidCardInfo(approved);
        cashPurchasePage.errorOwner("Неверный формат");
    }
    //невалидный CVC
    @Test
    void invalidCVC(){
        var approved = DataHelper.invalidCVC();
        initialize();
        cashPurchasePage.invalidCardInfo(approved);
        cashPurchasePage.errorCVC("Неверный формат");
    }
    //поле владелец введены спецсиволы
    @Test
    void ownerFieldSpecialCharactersEntered(){
        var approved = DataHelper.fieldOwnerSpecialCharacters();
        initialize();
        cashPurchasePage.invalidCardInfo(approved);
        cashPurchasePage.errorOwner("Неверный формат");
    }
    // поле номер карты пустое
    @Test
    void theCardNumberFieldIsEmpty(){
        var approved = DataHelper.getCardInfoApproved();
        initialize();
        cashPurchasePage.emptyFieldNumberCard(approved);
        cashPurchasePage.errorNumberCard("Неверный формат");
    }
    //поле месяц пустое
    @Test
    void theMonthFieldIsEmpty(){
        var approved = DataHelper.getCardInfoApproved();
        initialize();
        cashPurchasePage.emptyFieldMonth(approved);
        cashPurchasePage.errorMonth("Неверный формат");
    }
    //поле год пустое
    @Test
    void theYearFieldIsEmpty(){
        var approved = DataHelper.getCardInfoApproved();
        initialize();
        cashPurchasePage.emptyFieldYear(approved);
        cashPurchasePage.errorYear("Неверный формат");
    }
    //поле владелец пустое
    @Test
    void theOwnerFieldIsEmpty(){
        var approved = DataHelper.getCardInfoApproved();
        initialize();
        cashPurchasePage.emptyFieldOwner(approved);
        cashPurchasePage.errorOwner("Поле обязательно для заполнения");
    }
    //поле CVC пустое
    @Test
    void theCVCFieldIsEmpty(){
        var approved = DataHelper.getCardInfoApproved();
        initialize();
        cashPurchasePage.emptyFieldCVC(approved);
        cashPurchasePage.errorCVC("Неверный формат");
    }

}

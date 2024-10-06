package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.Random;


public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        private String numberCard;
        private String month;
        private String year;
        private String owner;
        private String cvc;
    }

    public static String specialCharacters() {
        String[] characters = {
                "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+", "=", "[", "]", "{", "}", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", ".", "?", "/"
        };
        return characters[new Random().nextInt(characters.length)];
    }


    public static CardInfo getCardInfoApproved() {
        return new CardInfo("1111 2222 3333 4444", getRandomMonthValue(), getRandomYearValue(), getRandomOwnerEN(), getRandomCVC());
    }

    public static CardInfo getCardInfoDecliend() {
        return new CardInfo("5555 6666 7777 8888", getRandomMonthValue(), getRandomYearValue(), getRandomOwnerEN(), getRandomCVC());
    }

    public static CardInfo invalidCard() {
        return new CardInfo(getRandomCVC(), getRandomMonthValue(), "26", getRandomOwnerEN(), getRandomCVC());
    }

    public static CardInfo invalidMonth() {
        return new CardInfo("1111 2222 3333 4444", getRandomNumber(), "26", getRandomOwnerEN(), getRandomCVC());
    }

    public static CardInfo invalidYear() {
        return new CardInfo("1111 2222 3333 4444", getRandomMonthValue(), getRandomNumber(), getRandomOwnerEN(), getRandomCVC());
    }

    public static CardInfo invalidOwner() {
        return new CardInfo("1111 2222 3333 4444", getRandomMonthValue(), "26", getRandomOwnerRU(), getRandomCVC());
    }

    public static CardInfo invalidCVC() {
        return new CardInfo("1111 2222 3333 4444", getRandomMonthValue(), "26", getRandomOwnerEN(), getRandomNumber());
    }

    public static CardInfo fieldOwnerSpecialCharacters() {
        return new CardInfo("1111 2222 3333 4444", getRandomMonthValue(), "26", specialCharacters(), getRandomCVC());
    }

    //временный метод для проверки в базе данных, так как в приложении есть баг что в поле год нельзя ввести год > 29
    //после исправления бага метод "makingPurchase" нужно удалить
    public static CardInfo makingPurchase() {
        return new CardInfo("1111 2222 3333 4444", getRandomMonthValue(), "26", getRandomOwnerEN(), getRandomCVC());
    }

    public static String getRandomNumber() {
        int randomNumber = faker.number().numberBetween(13, 23);
        return String.valueOf(randomNumber);
    }

    public static String getRandomMonthValue() {
        int randomNumber = faker.number().numberBetween(1, 13);
        return String.format("%02d", randomNumber);
    }

    public static String getRandomYearValue() {
        int randomNumber = faker.number().numberBetween(30, 50);
        return String.valueOf(randomNumber);
    }

    public static String getRandomOwnerEN() {
        Faker faker1 = new Faker(new Locale("en"));
        return faker1.name().fullName();
    }

    public static String getRandomOwnerRU() {
        Faker faker1 = new Faker(new Locale("ru"));
        return faker1.name().fullName();
    }

    public static String getRandomCVC() {
        return Faker.instance().numerify("###");
    }


}

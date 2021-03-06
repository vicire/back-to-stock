package com.example.service;

import com.example.model.Product;
import com.example.model.ProductCategory;
import com.example.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BackToStockServiceImplTest {
    private static BackToStockService backToStockService;
    private static User generalUser;
    private static User premiumYoungUser;
    private static User oldUser;
    private static User premiumOldUser;
    private static User generalUser1;
    private static User nullUser;
    private static User userWithNullField;
    private static Product medical;
    private static Product book;
    private static Product digital;
    private static Product randomProduct;
    private static Product nullProduct;
    private static Product productWithNullField;

    @BeforeAll
    public static void init() {
        generalUser = new User();
        generalUser.setAge(49);
        generalUser.setName("Bob");
        generalUser.setPremium(false);
        premiumYoungUser = new User();
        premiumYoungUser.setAge(20);
        premiumYoungUser.setName("Sofia");
        premiumYoungUser.setPremium(true);
        oldUser = new User();
        oldUser.setAge(71);
        oldUser.setName("Bohdan");
        oldUser.setPremium(false);
        premiumOldUser = new User();
        premiumOldUser.setAge(71);
        premiumOldUser.setName("Alice");
        premiumOldUser.setPremium(true);
        generalUser1 = new User();
        generalUser1.setAge(70);
        generalUser1.setName("Fred");
        generalUser1.setPremium(false);
        nullUser = null;
        userWithNullField = new User();
        userWithNullField.setName(null);
        userWithNullField.setAge(30);
        userWithNullField.setPremium(true);
        medical = new Product("CovidShield", ProductCategory.MEDICAL);
        book = new Product("Harry Potter and the goblet of fire", ProductCategory.BOOKS);
        digital = new Product("petcube", ProductCategory.DIGITAL);
        randomProduct = new Product("Game of the thrones", ProductCategory.BOOKS);
        nullProduct = null;
        productWithNullField = new Product(null, null);
        backToStockService = new BackToStockServiceImpl(medical, book, digital);
    }

    @Test
    void testSubscribeAndUnsubscribeOnMedicalProducts_Ok() {
        backToStockService.subscribe(generalUser, medical);
        backToStockService.subscribe(generalUser1, medical);
        backToStockService.subscribe(oldUser, medical);
        backToStockService.subscribe(premiumOldUser, medical);
        backToStockService.subscribe(premiumYoungUser, medical);
        List<User> expected = new ArrayList<>();
        expected.add(oldUser);
        expected.add(premiumOldUser);
        expected.add(premiumYoungUser);
        expected.add(generalUser);
        expected.add(generalUser1);
        Assertions.assertEquals(expected, backToStockService.subscribedUsers(medical));
        backToStockService.unsubscribe(generalUser1, medical);
        backToStockService.unsubscribe(premiumOldUser, medical);
        expected.remove(generalUser1);
        expected.remove(premiumOldUser);
        Assertions.assertEquals(expected, backToStockService.subscribedUsers(medical));
    }

    @Test
    void testSubscribeAndUnsubscribeOnBookProducts_Ok() {
        backToStockService.subscribe(generalUser, book);
        backToStockService.subscribe(generalUser1, book);
        backToStockService.subscribe(oldUser, book);
        backToStockService.subscribe(premiumOldUser, book);
        backToStockService.subscribe(premiumYoungUser, book);
        List<User> expected = new ArrayList<>();
        expected.add(premiumOldUser);
        expected.add(premiumYoungUser);
        expected.add(oldUser);
        expected.add(generalUser);
        expected.add(generalUser1);
        Assertions.assertEquals(expected, backToStockService.subscribedUsers(book));
        backToStockService.unsubscribe(oldUser, book);
        backToStockService.unsubscribe(generalUser1, book);
        expected.remove(oldUser);
        expected.remove(generalUser1);
        Assertions.assertEquals(expected, backToStockService.subscribedUsers(book));
    }

    @Test
    void testSubscribeAndUnsubscribeOnDigitalProducts_Ok() {
        backToStockService.subscribe(generalUser, digital);
        backToStockService.subscribe(premiumOldUser, digital);
        backToStockService.subscribe(generalUser1, digital);
        backToStockService.subscribe(oldUser, digital);
        backToStockService.subscribe(premiumYoungUser, digital);
        List<User> expected = new ArrayList<>();
        expected.add(premiumOldUser);
        expected.add(premiumYoungUser);
        expected.add(oldUser);
        expected.add(generalUser);
        expected.add(generalUser1);
        Assertions.assertEquals(expected, backToStockService.subscribedUsers(digital));
        backToStockService.unsubscribe(generalUser, digital);
        backToStockService.unsubscribe(premiumOldUser, digital);
        backToStockService.unsubscribe(oldUser, digital);
        expected.remove(generalUser);
        expected.remove(premiumOldUser);
        expected.remove(oldUser);
        Assertions.assertEquals(expected, backToStockService.subscribedUsers(digital));
    }

    @Test
    void testFormingSubscriptionWithNullProduct_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> new BackToStockServiceImpl(nullProduct));
        Assertions.assertThrows(RuntimeException.class,
                () -> new BackToStockServiceImpl(productWithNullField));
    }

    @Test
    void testSubscribeNullUser() {
        Assertions.assertThrows(RuntimeException.class,
                () -> backToStockService.subscribe(nullUser, medical));
        Assertions.assertThrows(RuntimeException.class,
                () -> backToStockService.subscribe(nullUser, digital));
    }

    @Test
    void testSubscribeUserWithNullFields() {
        Assertions.assertThrows(RuntimeException.class,
                () -> backToStockService.subscribe(userWithNullField, book));
        Assertions.assertThrows(RuntimeException.class,
                () -> backToStockService.subscribe(userWithNullField, medical));
    }

    @Test
    void testGettingUsersFromProductNotInTheList_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> backToStockService.subscribedUsers(randomProduct));
    }

    @Test
    void testSubscribeAndUnsubscribeUsersFromProductNotInTheList_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> backToStockService.subscribe(premiumOldUser, randomProduct));
        backToStockService.subscribe(oldUser, book);
        Assertions.assertThrows(RuntimeException.class,
                () -> backToStockService.unsubscribe(oldUser, randomProduct));
    }
}
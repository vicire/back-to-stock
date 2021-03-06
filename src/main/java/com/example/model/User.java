package com.example.model;

import java.util.Objects;

public class User {
    private String name;
    private boolean premium;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, premium, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return Objects.equals(user.name, name) && user.age == age && user.premium == premium;
    }

    @Override
    public String toString() {
        return "User - {"
                + " name: " + name
                + ", premium: " + premium
                + ", age: " + age + " }";
    }
}

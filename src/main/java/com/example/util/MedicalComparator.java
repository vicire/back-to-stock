package com.example.util;

import com.example.model.User;
import java.util.Comparator;

public class MedicalComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if ((o1.getAge() > 70 || o1.isPremium()) && (o2.getAge() > 70 || o2.isPremium())) {
            return 0;
        } else if (o1.getAge() > 70 || o1.isPremium()) {
            return -1;
        } else if (o2.getAge() > 70 || o2.isPremium()) {
            return 1;
        }
        return 0;
    }
}

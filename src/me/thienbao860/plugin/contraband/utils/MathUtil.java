package me.thienbao860.plugin.contraband.utils;

import me.thienbao860.plugin.contraband.object.CShopItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathUtil {

    public static List<CShopItem> randomArray(List<?> arr, int size) {
        List<CShopItem> contains = new ArrayList<>();
        for (int i = 0; i < (Math.min(arr.size(), size)); i++) {
            Random rand = new Random();
            while (true) {
                CShopItem o = (CShopItem) arr.get(rand.nextInt(arr.size()));
                if (!contains.contains(o)) {
                    contains.add(o);
                    break;
                }

            }
        }
        return contains;

    }

    public static boolean inRandomChance(double percent) {
        return Math.random() < percent / 100;
    }
}

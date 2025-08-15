package ru.ntwz.worldborder.module;

import java.util.List;

public class Functions {
    public static Object getRandomFromList(List<?> list) {
        return list.get((int) (Math.random() * list.size()));
    }
}

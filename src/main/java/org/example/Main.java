package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<Integer,Integer> usersRating = Map.of(
                1,1000,
                2,1100,
                3,1200,
                4,1300,
                5,1600,
                6,1900,
                7,800,
                8,1300,
                9,1500,
                10,1400
        );
        for (int i = 0; i < 10000; i++) {
            Set<Integer> generatedRoles = new HashSet<>();
            Random r = new Random();
            while (generatedRoles.size() < 3) {
                generatedRoles.add(r.nextInt(10) + 1);
            }
            Map<Integer, Boolean> usersRoles = new HashMap<>();
            for (var player : usersRating.keySet()) {
                if (generatedRoles.contains(player)) {
                    usersRoles.put(player, true);
                } else {
                    usersRoles.put(player, false);
                }
            }
            usersRating = EloRating.calculate(usersRating, usersRoles, r.nextBoolean());
        }
        System.out.println("It`s over");
    }
}
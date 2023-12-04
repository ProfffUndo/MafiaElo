package org.example;

import java.util.HashMap;
import java.util.Map;

public class EloRating {

    /**
     * Constructor
     */
    public EloRating() {
    }

    public static int determineK(int rating) {
        int K;
        if (rating < 2000) {
            K = 32;
        } else if (rating < 2400) {
            K = 24;
        } else {
            K = 16;
        }
        return K;
    }

    public static Map<Integer, Integer> calculate(Map<Integer, Integer> usersRating,
                                                          Map<Integer, Boolean> usersRoles,
                                                          Boolean mafiaWin){
        Map<Integer, Integer> finalUsersRating = usersRating;
        double mafiaRating = (double) usersRoles.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .mapToInt(x -> finalUsersRating.get(x.getKey()))
                .sum() /3;
        double citizensRating = (double) usersRoles.entrySet()
                .stream()
                .filter(x -> !x.getValue())
                .mapToInt(x -> finalUsersRating.get(x.getKey()))
                .sum() /7;

        Map<Integer,Integer> newUsersRating = new HashMap<>();

        for (var player:
             usersRating.keySet()) {
            if (usersRoles.get(player) && mafiaWin) {
                double playerRating = usersRating.get(player);
                double exponent = (playerRating - citizensRating) / 400;
                double expectedOutcome = (1 / (1 + (Math.pow(10, -exponent))));
                int K = determineK(usersRating.get(player));
                int newRating = (int) Math.round(usersRating.get(player) + K * (1 - expectedOutcome));
                newUsersRating.put(player, newRating);
            }
            if (usersRoles.get(player) && !mafiaWin) {
                double playerRating = usersRating.get(player);
                double exponent = (playerRating - citizensRating) / 400;
                double expectedOutcome = (1 / (1 + (Math.pow(10, -exponent))));
                int K = determineK(usersRating.get(player));
                int newRating = (int) Math.round(usersRating.get(player) + K * (0 - expectedOutcome));
                newUsersRating.put(player, newRating);
            }
            if (!usersRoles.get(player) && mafiaWin) {
                double playerRating = usersRating.get(player);
                double exponent = (playerRating - mafiaRating) / 400;
                double expectedOutcome = (1 / (1 + (Math.pow(10, -exponent))));
                int K = determineK(usersRating.get(player));
                int newRating = (int) Math.round(usersRating.get(player) + K * (0 - expectedOutcome));
                newUsersRating.put(player, newRating);
            }
            if (!usersRoles.get(player) && !mafiaWin) {
                double playerRating = usersRating.get(player);
                double exponent = (playerRating - mafiaRating) / 400;
                double expectedOutcome = (1 / (1 + (Math.pow(10, -exponent))));
                int K = determineK(usersRating.get(player));
                int newRating = (int) Math.round(usersRating.get(player) + K * (1 - expectedOutcome));
                newUsersRating.put(player, newRating);
            }
        }
        usersRating = newUsersRating;
        return usersRating;
    }
}

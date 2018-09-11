package edu.wctc.eligrow;

import java.io.File;
import java.util.*;

/**
 * Generates account numbers and statistics per item number
 * @author eplig
 * @version 1.0
 */
public class Main {

    private final static FileInput cardAccts = new FileInput("movie_cards.csv");
    private final static FileInput cardPurchases = new FileInput("movie_purchases.csv");
    private final static FileInput cardRatings = new FileInput("movie_rating.csv");
    private static ArrayList<double[]> sort = new ArrayList<>();
    private static Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        String line;
        String[] fields;
        int[] nums = new int[2];
        double[] ratings = new double[2];
        System.out.format("%8s  %-18s %6s %6s %10s\n","Account","Name", "Movies", "Points", "Avg Rating");
        while ((line = cardAccts.fileReadLine()) != null) {
            fields = line.split(",");
            findPurchases(fields[0], nums);
            findRatings(fields[0], ratings);
            System.out.format("00%6s  %-18s  %2d   %4d  %10.1f\n",fields[0],fields[1], nums[0], nums[1], (ratings[1]/nums[0]));

        }
        cardRatings.fileClose();
        ratingTally();
    }

    /**
     * Finds purchases and points
     * @param acct String: Account number
     * @param nums Integer array: Array to place numbers generated.
     */
    public static void findPurchases(String acct, int[] nums) {
        nums[0] = 0;
        nums[1] = 0;
        String line;
        String[] fields;
        boolean done = false;
        while (((line = cardPurchases.fileReadLine()) != null) && !(done)) {
            fields = line.split(",");
            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                nums[0]++;
                nums[1] += Integer.parseInt(fields[2]);
            }

        }
    }

    /**
     * Finds number of movies and ratings
     * @param acct Number: Account number
     * @param nums Double array: Array to place numbers generated
     */
    public static void findRatings(String acct, double[] nums) {
        nums[0] = 0;
        nums[1] = 0;
        String line;
        String[] fields;
        boolean done = false;
        while (((line = cardRatings.fileReadLine()) != null) && !(done)) {
            fields = line.split(",");


            if (fields[0].compareTo(acct) > 0) {
                done = true;
            }
            else if (fields[0].equals(acct)) {
                nums[0]++;
                nums[1] += Double.parseDouble(fields[1]);

            }



        }
        /**
         * Tallies number of ratings
         */
        }
        public static void ratingTally(){
            FileInput cardRatings = new FileInput("movie_rating.csv");
            String line = "";
            int totalOfRating = 0;
            List<String> ratings = new ArrayList<String>();
            while (((line = cardRatings.fileReadLine()) != null)) {
                line = line.substring(line.indexOf(",") + 1);
                ratings.add(line);
            }
            Collections.sort(ratings);
            int rating = 1;
            System.out.println("Rating   Total");
            for(int i = 0; i < ratings.size(); i++){
                totalOfRating++;
                int rating1 = Integer.parseInt(ratings.get(i));

                if(rating1 != rating){
                    System.out.format("%4d   %-2d\n", rating, totalOfRating);
                    totalOfRating = 0;
                    rating++;
                }
            }
            System.out.format("%4d   %-2d\n", rating, totalOfRating);
            totalOfRating = 0;
        }
    }
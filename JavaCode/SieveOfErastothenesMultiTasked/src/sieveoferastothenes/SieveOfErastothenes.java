/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieveoferastothenes;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author raffa
 */
public class SieveOfErastothenes {

    static volatile Number[] numbers;
    static boolean verbose = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        if (args.length == 2 && args[1].contains("verbose")) {
            verbose = true;
        }

        int n = Integer.parseInt(args[0]);
        numbers = new Number[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = new Number(positionToNumber(i));
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executor.execute(new PrimeFinder(2, numbers, executor));

        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
        }

        if (verbose) {
            int count = 1;

            System.out.print("2 ");

            for (int i = 0; i < numbers.length; i++) {
                if (numbers[i].prime) {
                    System.out.print(numbers[i].value + " ");
                    count++;
                }
            }

            System.out.println();
            System.out.println("Prime numbers found: " + count);
        }
    }

    public static int numberToPosition(int n) {
        return n - 3;
    }

    public static int positionToNumber(int p) {
        return p + 3;
    }
}

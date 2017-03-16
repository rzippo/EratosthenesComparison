/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieveoferastothenes;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author raffa
 */
public class SieveOfErastothenes {

    static Number[] numbers;
    static boolean verbose = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            return;
        }

        if (args.length == 2 && args[1] == "verbose") {
            verbose = true;
        }

        int n = Integer.parseInt(args[0]);
        numbers = new Number[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = new Number(positionToNumber(i));
        }

        int currentPrime = 2;

        do {
            for (int i = 2;; i++) {
                int nextMultiple = currentPrime * i;
                if (nextMultiple < positionToNumber(n)) {
                    numbers[numberToPosition(nextMultiple)].prime = false;
                } else {
                    break;
                }
            }

            currentPrime = nextPrime(numbers, currentPrime);
        } while (currentPrime > 0);

        if (verbose) {
            int count = 0;

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

    public static int nextPrime(Number[] numbers, int currentPrime) {
        for (int i = numberToPosition(currentPrime + 1); i < numbers.length; i++) {
            if (numbers[i].prime) {
                return numbers[i].value;
            }
        }

        return 0;
    }

    public static int numberToPosition(int n) {
        return n - 3;
    }

    public static int positionToNumber(int p) {
        return p + 3;
    }

}

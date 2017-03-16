/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieveoferastothenes;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author raffa
 */
public class PrimeFinder implements Runnable
{

    int basePrime;
    int id;
    volatile Number[] numbers;
    ExecutorService executor;

    public PrimeFinder(int basePrime, Number[] numbers, ExecutorService executor)
    {
        this.id = ThreadSeqID.get();
        this.basePrime = basePrime;
        this.numbers = numbers;
        this.executor = executor;
    }

    public void run()
    {
        //System.out.println("Thread for " + basePrime + " started");

        boolean lastPrime = true;

        for (int i = basePrime - 2; i < numbers.length; i++)
        {
            Number n = numbers[i];
            if (n.prime)
            {
                if (n.value % this.basePrime == 0)
                {
                    //It's not prime
                    n.prime = false;
                    n.lkc.lock();
                    try
                    {
                        n.checks++;
                        n.cond.signal();
                    } finally
                    {
                        n.lkc.unlock();
                    }
                } else
                {
                    n.lkc.lock();
                    try
                    {
                        if (lastPrime)
                        {
                            while (n.checks < this.id - 1)
                            {
                                n.cond.await();
                            }

                            if (n.prime)
                            {
                                //System.out.println("Prime found: " + n.value + " by " + basePrime + " ID " + id );

                                n.checks++;
                                lastPrime = false;

                                executor.execute(new PrimeFinder(n.value, numbers, executor));
                            }
                        } else
                        {
                            n.checks++;
                            n.cond.signal();
                        }
                    } catch (InterruptedException e)
                    {
                        System.out.println("Thread " + this.id + " interrupted");
                        return;
                    } finally
                    {
                        n.lkc.unlock();
                    }
                }
            }
        }

        if (lastPrime)
        {
            executor.shutdown();
        }
    }
}

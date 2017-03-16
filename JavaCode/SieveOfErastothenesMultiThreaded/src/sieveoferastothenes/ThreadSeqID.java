/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieveoferastothenes;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author raffa
 */
public class ThreadSeqID {
    private static final AtomicInteger nextID = new AtomicInteger(1);
    private static final ThreadLocal<Integer> threadID = new ThreadLocal<Integer>()
    {
        @Override
        protected Integer initialValue()
        {
            return nextID.getAndIncrement();
        }
    };
    
    public static int get()
    {
        return threadID.get();
    }  
}

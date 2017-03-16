/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieveoferastothenes;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/**
 *
 * @author raffa
 */
public class Number {
    public int value;
    public int checks = 0;
    public boolean prime = true;
    
    public Lock lkc = new ReentrantLock();
    public Condition cond = lkc.newCondition();
    
    public Number(int value)
    {
        this.value = value;
    }
}

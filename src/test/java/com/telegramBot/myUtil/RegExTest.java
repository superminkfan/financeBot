package com.telegramBot.myUtil;

import org.junit.Test;

public class RegExTest {

    @Test
    public void testReg()
    {

        System.out.println("Cool check:");


        System.out.println(RegEx.checkWithRegExp("111111 hui"));
        System.out.println(RegEx.checkWithRegExp("111111 хуй"));
        System.out.println(RegEx.checkWithRegExp("хуй 111111"));
        System.out.println(RegEx.checkWithRegExp("hui 111111"));
    }

    @Test
    public void testRegEx2()
    {
        System.out.println("Cool check too:");


        System.out.println(RegEx.devideByHalf("111111 hui"));
        System.out.println(RegEx.devideByHalf("222222 хуй"));
        System.out.println(RegEx.devideByHalf("хуй 3333333"));
        System.out.println(RegEx.devideByHalf("hui 444444"));
        System.out.println(RegEx.devideByHalf("hui"));
        System.out.println(RegEx.devideByHalf("хуй"));
    }
}

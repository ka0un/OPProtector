/*    */ package org.kasun.opprotector.Utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class ColorUtils
/*    */ {
/*    */   public static String colorGradient(String string) {
/* 12 */     String message = string.replace("", " ");
/* 13 */     int spaceCount = message.length() - message.replaceAll(" ", "").length();
/*    */     
/* 15 */     List<Integer> colors = new ArrayList<>(Arrays.asList(new Integer[] { Integer.valueOf(100), Integer.valueOf(101), Integer.valueOf(102), Integer.valueOf(103), Integer.valueOf(104), Integer.valueOf(105), Integer.valueOf(141), Integer.valueOf(140), Integer.valueOf(139), Integer.valueOf(138), Integer.valueOf(137), Integer.valueOf(136), Integer.valueOf(172), Integer.valueOf(173), Integer.valueOf(174), Integer.valueOf(175), Integer.valueOf(176), Integer.valueOf(177), Integer.valueOf(213), Integer.valueOf(212), Integer.valueOf(211), Integer.valueOf(210), Integer.valueOf(209), Integer.valueOf(208) }));
/* 16 */     String finalMessage = message;
/* 17 */     for (int i = 0; i < spaceCount; i++) {
/* 18 */       Random random = new Random();
/* 19 */       int rd = random.nextInt(24);
/* 20 */       String color = "\033[38;5;" + colors.get(rd) + "m";
/*    */       
/* 22 */       finalMessage = finalMessage.replaceFirst("\\s+", color);
/*    */     } 
/* 24 */     return finalMessage;
/*    */   }
/*    */ }


/* Location:              D:\Downloads\ConsoleColors-V1 (1).jar!\net\godead\consolecolors\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
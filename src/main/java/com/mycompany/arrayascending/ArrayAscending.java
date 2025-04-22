/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.arrayascending;

import java.util.Arrays;

public class ArrayAscending {

    public static void main(String[] args) {
        int [] numbers = {5,2,8,1,9,7,6,10,3};
        
        Arrays.sort(numbers);
        
        System.out.println("Sorted in Ascending order: "+Arrays.toString(numbers)+"");
        
    }
}


package csc301HW8;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Integer;

public class SortingExercises
{
    /**
     * Code loosely based on 
     *   https://github.com/Grinnell-CSC207/sorting
     * 
     * place elements in data into order
     *
     * @param data array of positive integer values
     */
 public static void mergeSort(int [ ] vals)
    {
        // obtain sorted array
        int [] newVals = mergeSortKernel (vals);
        // copy array back to original array
        for (int i = 0; i < vals.length; i++)
            vals [i] = newVals [i];
    }

    private static int [] mergeSortKernel (int [] vals)
    {
        // Base case: Singleton arrays need not be sorted.
        if (vals.length <= 1)
            {
                return vals.clone();
            } // if length <= 1
        else
            {
                int mid = vals.length / 2;
                int [] left  = mergeSortKernel(Arrays.copyOfRange(vals, 0, mid));
                int [] right = mergeSortKernel(Arrays.copyOfRange(vals, mid, vals.length));
                return  merge(left, right);
            } // recursive case: More than one element
    } 

    // method to merge two arrays to yield a new third array
    private static int [] merge (int [] first, int [] second) {
        int [] result = new int [first.length + second.length];
        int i1 = 0;  // index for first array
        int i2 = 0;  // index for second array
        int r  = 0;  // index for result array

        while (r < first.length + second.length)
            {
                if ((i2 >= second.length) || (i1 < first.length && first[i1] < second[i2]))
                    {
                        result [r] = first[i1];
                        i1++;
                        r++;
                    }
                else
                    {
                        result [r] = second[i2];
                        i2++;
                        r++;
                    }
            }
        return result;
    }  // merge


    /**
     * Code loosely based on 
     *   http://web.engr.oregonstate.edu/~budd/Books/jds/info/src/jds/sort/RadixSort.java
     * updated for Java 7, with use of alternative collections and 
     * reliance on autoboxing and autounboxing
     * 
     * place elements in data into order
     *
     * @param data array of positive integer values
     */
    public static void radixSort (int [ ] data) {
        boolean flag = true;
        int divisor = 1;
        ArrayList<LinkedList> buckets = new ArrayList<LinkedList>(10);
        for (int i = 0; i < 10; i++)
            buckets.add(i, new LinkedList<Integer>());

        while (flag) {
            flag = false;
            // first copy the values into buckets
            for (int i = 0; i < data.length; i++) {
                int hashIndex = (data[i] / divisor) % 10;
                if (hashIndex > 0) flag = true;
                buckets.get(hashIndex).addLast(data[i]);
            }
            // then copy the values back into vector
            divisor *= 10;
            int i = 0;
            for (int j = 0; j < 10; j++) {
                while (! buckets.get(j).isEmpty()) {
                    data[i] = (Integer) buckets.get(j).removeFirst();
                    i++;
                }
            }
        }
    }

public static void main (String [] args) 
    throws Exception 
  {

    System.out.println ("timing of several sorting algorithms "
                        + "on various size data sets");
    int size;
    int dataset, i;
    System.out.println ("Array Size      Merge     Radix");
    System.out.println ("Array Size       Sort      Sort");

    for (dataset = 0; dataset < 3; dataset++)
      { switch (dataset) 
              {/* table header */
              case 0:  System.out.println ("\nAscending Data");
                  break;
              case 1:  System.out.println ("\nRandom Data");
                  break;
  
              case 2:  System.out.println ("\nDescending Data");
                  break;
              }
  
          //size = 10;
          for (size = 10000; size < 2570000; size*=2)
            {
                System.out.printf ("%10d:  ", size);
                /* set up data arrays */
                int [] a = new int [size];
                int [] b = new int [size];

                switch (dataset) 
                    {
                    case 0: //  ascending data
                        for (i = 0; i < size; i++) 
                            a[i] = b[i] = i;
                        break;
                    case 1:  // random data
                        for (i = 0; i < size; i++) 
                            a[i] = b[i] = (int)(Math.random()*1000000);
                        break;
   
                    case 2: //  descending data
                        for (i = 0; i < size; i++) 
                            a[i] = b[i] = size-i;
                        break;
                    } 


                long start_time;
                long end_time;      
    
                // time and check basic radix sort 
                start_time = System.currentTimeMillis();
                mergeSort (a);
                end_time = System.currentTimeMillis();
          
                for (i = 1; i < size; i++) /* check array properly sorted */
                    if (a[i-1] > a[i])
                        System.out.println ("error in mergeSort");

                System.out.printf ("%7d", end_time - start_time);

                // time and check radix sort with Max computation, Math.Pow
                start_time = System.currentTimeMillis();
                radixSort (b);
                end_time = System.currentTimeMillis();
          
                for (i = 0; i < size; i++) /* check array properly sorted */
                    if (a[i] != b[i])
                        System.out.println ("error in radixSort");

              System.out.printf ("%10d", end_time - start_time);
              
              System.out.println ();
          } // size loop
      } // dataset loop
  } // main





}
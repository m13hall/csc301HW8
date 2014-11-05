package csc301HW8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Integer;

public class SortingExercises {
	
/*	  *//**
     * Code loosely based on 
     *   https://github.com/Grinnell-CSC207/sorting
     * 
     * place elements in data into order
     *
     * @param data array of positive integer values
     *//*
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

*/
	
	/**
	 * Code loosely based on https://github.com/Grinnell-CSC207/sorting
	 * 
	 * place elements in data into order
	 *
	 * @param data
	 *            array of positive integer values
	 */
	public static void mergeSort(int[] vals) {
		// obtain sorted array
		int[] copyTo = vals.clone();
		mergeSortKernel(vals, copyTo, 0, vals.length - 1);
		for (int i = 0; i < vals.length - 1; i++) {
			if (vals[i] > vals[i + 1]) {
				for (int j = 0; j < vals.length; j++) {
					vals[j] = copyTo[j];
				}
				break;
			}
		}
	}

	// left is the first element being looked at
	// right is the last element being looked at
	private static void mergeSortKernel(int[] copyFrom, int[] copyTo, int left,
			int right) {
		
		 /* Copying a new array out every time is vastly innefficient, both
		 * space-wise and time-wise, especially since this happens usually twice
		 * at eat calling of mergeSortKernel. We're changing it to use indicies
		 * so it only
		 * */
		 
		// Base case: Singleton arrays need not be sorted.
		if (left == right) {
			// stop
		} // if length <= 1
		else {

			/*if ((right - left) % 2 == 1) { // even number of elements
				int mid = (left + right) / 2;
				mergeSortKernel(copyTo, copyFrom, left, mid);
				mergeSortKernel(copyTo, copyFrom, mid + 1, right);
				merge(copyFrom, copyTo, left, mid, right);
			} else {
				right--;
				int mid = (int) Math.floor((left + right) / 2);
				mergeSortKernel(copyTo, copyFrom, left, mid);
				mergeSortKernel(copyTo, copyFrom, mid + 1, right);
				merge(copyFrom, copyTo, left, mid, right);
				merge(copyTo, copyFrom, left, right, right + 1);
				for (int i = 0; i < copyTo.length; i++) {
					copyTo[i] = copyFrom[i];
				}

			}*/

		} // recursive case: More than one element
	}

	// method to merge two arrays to yield a new third array
	private static void merge(int[] copyFrom, int[] copyTo, int left, int mid,
			int right) {
		int i1 = left; // index for first part of copyFrom
		int i2 = mid + 1; // index for second part of copyFrom
		int r = left; // index for copyTo

		while (r <= right) {
			if ((i2 > right) || (i1 <= mid && copyFrom[i1] < copyFrom[i2])) {
				copyTo[r] = copyFrom[i1];
				i1++;
				r++;
			} else {
				copyTo[r] = copyFrom[i2];
				i2++;
				r++;
			}
		}
	} // merge

	/**
	 * Code loosely based on
	 * http://web.engr.oregonstate.edu/~budd/Books/jds/info
	 * /src/jds/sort/RadixSort.java updated for Java 7, with use of alternative
	 * collections and reliance on autoboxing and autounboxing
	 * 
	 * place elements in data into order
	 *
	 * @param data
	 *            array of positive integer values
	 */
	public static void radixSort(int[] data) {
		boolean reachedLastDigit = false; // renamed for clarity
		int divisor = 1;
		int[][] buckets = new int[10][data.length];
		int[] iterators = new int[10];
		Arrays.fill(iterators, -1);
		/*
		 * avoid Arraylists of Integers, which do a lot of autoboxing/boxing
		 * also, by allocating all the possible memory we're going to need now,
		 * we avoid having to allocate memory with each addition to the
		 * ArrayList
		 */

		while (!reachedLastDigit) {
			reachedLastDigit = true;
			// first copy the values into buckets
			for (int i = 0; i < data.length; i++) {
				int hashIndex = (data[i] / divisor) % 10;
				if (hashIndex > 0) {
					reachedLastDigit = false;
				}
				buckets[hashIndex][++iterators[hashIndex]] = data[i];
				// now without arraylists, this addition takes no memory
				// allocation
			}
			// then copy the values back into vector
			if (!reachedLastDigit) {
				divisor *= 10;
				int i = 0;

				for (int bucket = 0; bucket < 10; bucket++) {
					for (int elmInBucket = 0; elmInBucket <= iterators[bucket]; elmInBucket++) {
							data[i] = buckets[bucket][elmInBucket];
							buckets[bucket][elmInBucket] = 0;
							// now we do no casting (ie allocating a new
							// Integer)
							i++;
					}
				}
				Arrays.fill(iterators, -1);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		int arr[] = { 11, 9, 8, 5, 6, 4, 3, 2, 1 };
		// mergeSort(arr);
		System.out.println("timing of several sorting algorithms "
				+ "on various size data sets");
		int size;
		int dataset, i;
		System.out.println("Array Size      Merge     Radix");
		System.out.println("Array Size       Sort      Sort");

		for (dataset = 0; dataset < 3; dataset++) {
			switch (dataset) {/* table header */
			case 0:
				System.out.println("\nAscending Data");
				break;
			case 1:
				System.out.println("\nRandom Data");
				break;

			case 2:
				System.out.println("\nDescending Data");
				break;
			}

			// size = 10;
			for (size = 4; size < 40; size *= 2) {
				System.out.printf("%10d:  ", size);
				/* set up data arrays */
				int[] a = new int[size];
				int[] b = new int[size];

				switch (dataset) {
				case 0: // ascending data
					for (i = 0; i < size; i++)
						a[i] = b[i] = i;
					break;
				case 1: // random data
					for (i = 0; i < size; i++)
						a[i] = b[i] = (int) (Math.random() * 100);
					break;

				case 2: // descending data
					for (i = 0; i < size; i++)
						a[i] = b[i] = size - i;
					break;
				}

				long start_time;
				long end_time;

				// time and check basic radix sort
				
				  start_time = System.currentTimeMillis(); mergeSort(a);
				  end_time = System.currentTimeMillis();
				  
				  for (i = 1; i < size; i++)
				 

				/* check array properly sorted */
				
				  if (a[i - 1] > a[i])
				  System.out.println("error in mergeSort");
				  
				  System.out.printf("%7d", end_time - start_time);
				 

				// time and check radix sort with Max computation, Math.Pow
				start_time = System.currentTimeMillis();
				radixSort(b);
				end_time = System.currentTimeMillis();

				for (i = 0; i < size; i++)
					// check array properly sorted
					if (a[i] != b[i]){
						System.out.println(Arrays.toString(b));
						System.out.println("error in radixSort");
					}
				System.out.printf("%10d", end_time - start_time);
				System.out.println();
			} // size loop
		} // dataset loop
	} // main

}
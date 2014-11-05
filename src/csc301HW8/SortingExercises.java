package csc301HW8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.Integer;

public class SortingExercises {

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
		int[] scratch = vals.clone();

		int mergingIntoSize = 2;
		int mergingFromSize = 1;
		boolean valsIsCorrect = true;
		int left;
		while (mergingIntoSize <= vals.length) {
			for (left = 0; left + mergingIntoSize <= vals.length; left += mergingIntoSize) {
				// merge subarrays of size subarray
				if (valsIsCorrect) {
					merge(vals, scratch, left, left + mergingFromSize, left
							+ mergingIntoSize - 1);
				} else {
					merge(scratch, vals, left, left + mergingFromSize, left
							+ mergingIntoSize - 1);
				}
			}
			
			//clean up extra values
			if(vals.length - mergingIntoSize != 0 && mergingFromSize > 1){
				if (valsIsCorrect) {
					merge(vals, scratch, left, left + mergingFromSize, vals.length - 1);
				} else {
					merge(scratch, vals, left, left + mergingFromSize, vals.length - 1);
				}
			}

			mergingIntoSize *= 2;
			mergingFromSize *= 2;
			valsIsCorrect = !valsIsCorrect; // keep track of whether vals has
											// the sorted values or not
		}
		
		// clean up
		if (vals.length - (mergingIntoSize / 2) != 0) {
			if (valsIsCorrect) {
				merge(vals, scratch, 0, mergingIntoSize / 2, vals.length - 1);
			} else {
				merge(scratch, vals, 0, mergingIntoSize / 2, vals.length - 1);
			}
			valsIsCorrect = !valsIsCorrect;
		}
	
		if (!valsIsCorrect) {
			for(int i = 0; i < vals.length; i++){
				vals[i] = scratch[i];
			}
		}
	}

	private static void merge(int[] copyFrom, int[] copyTo, int left,
			int firstElmSecondSubarray, int right) {
		int i1 = left; // index for first part of copyFrom
		int i2 = firstElmSecondSubarray; // index for second part of copyFrom
		int r = left; // index for copyTo
		int stopAt = right;

		while (r <= stopAt) {
			if ((i2 > stopAt)
					|| (i1 < firstElmSecondSubarray && copyFrom[i1] < copyFrom[i2])) {
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
			for (size = 10000; size < 2570000; size*=2) {
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

				start_time = System.currentTimeMillis();
				mergeSort(a);
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
				 if (a[i] != b[i]) {
				 System.out.println(Arrays.toString(b));
				 System.out.println("error in radixSort");
				 }
				 System.out.printf("%10d", end_time - start_time);
				System.out.println();
			} // size loop
		} // dataset loop
	} // main

}

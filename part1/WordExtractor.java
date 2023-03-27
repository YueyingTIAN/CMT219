import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

class WordExtractor {

    /* 

    The mergeSort function 
    
    Input: an ArrayList of strings, start and end position of mergesort, and a helper list for merging
    Output: the ArrayList of strings will be sorted in ascending alphabetical order
    
    */

    public static void mergeSort(ArrayList<String> words, int start, int end, String[] helper, int[] num_comparison) {
        
        // If start >= end, then there is no need to sort

        if (start < end) {

            // Set a mid position, and recursively call mergeSort twice 
            // to sort the two half parts: start to mid, and mid+1 to end

            int mid = (start + end) / 2;

            mergeSort(words, start, mid, helper, num_comparison);
            mergeSort(words, mid+1, end, helper, num_comparison);

            // Merge the sorted two half parts after sorting them 

            merge(words, start, end, helper, num_comparison);
        }
    }

    public static void merge(ArrayList<String> words, int start, int end, String[] helper, int[] num_comparison) {

        // Merge the two sorted parts: start to mid, and mid+1 to end

        int mid = (start + end) / 2;

        int i = start, j = mid + 1;
        int index = start;

        // If ith word is less than jth word in alphabetical order,
        // then put ith word into helper,
        // otherwise put jth word into helper

        while (i <= mid && j <= end) {
            if (words.get(i).compareToIgnoreCase(words.get(j)) < 0) {
                helper[index++] = words.get(i++);
            } else {
                helper[index++] = words.get(j++);
            }
            ++num_comparison[0];
        }

        // If the fisrt half part still has words,
        // then put all the words in the first half into helper

        while (i <= mid) {
            helper[index++] = words.get(i++);
            ++num_comparison[0];
        }

        // If the second half part still has words,
        // then put all the words in the second half into helper

        while (j <= end) {
            helper[index++] = words.get(j++);
            ++num_comparison[0];
        }

        // Copy the merged words in helper to the original ArrayList of strings

        for (int k = start; k <= end; k++) {
            words.set(k, helper[k]);
            ++num_comparison[0];
        }
    }

    public static void main(String args[]) {

        // vocabulary is a HashSet of strings, to store the key words in a given file.
        // Using a HashSet will provide O(1) time for checking whether a single
        // word is in the vocabulary

        HashSet<String> vocabulary = new HashSet<String>();

        // valid_words is a ArrayList of strings, to store the valid words from
        // a given document based on the vocabulary

        ArrayList<String> valid_words = new ArrayList<String>();

        // First, try to open the given vocabulary file,
        // and add each word (line) in the vocabulary file into the HashSet of strings,
        // using try and catch for situations like files not existing (e.g., wrong file names),

        try {
            FileInputStream filestream = new FileInputStream("./google-10000-english-no-swears.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(filestream));

            String strline;

            while ((strline = br.readLine()) != null) {
                vocabulary.add(strline.toLowerCase());
            }

            filestream.close();

        } catch(Exception exception) {
            System.err.println(exception.getMessage());
        }

        // Next, try to open the document, 
        // and split each line into words,
        // and then check if each word is in the vocabulary (HashSet),
        // if yes then add the word into valid_words (ArrayList),
        // otherwise discard the word.

        // Similarly, try and catch is used to handler exceptions.

        try {
            FileInputStream filestream = new FileInputStream("./Input219.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(filestream));

            String strline;

            while ((strline = br.readLine()) != null) {
                String[] words = strline.split("\\W+");

                for (String word: words) {
                    if (vocabulary.contains(word.toLowerCase())) {
                        valid_words.add(word);
                    }
                }
            }

            filestream.close();

        } catch(Exception exception) {
            System.err.println(exception.getMessage());
        }

        // Part A): print the key words in the document based on the vocabulary
        
        System.out.println(valid_words);

        /* ====================================== */

        // Part B): Sorting

        // num_valid_words is the number of valid words in the documents based on the vocabulary

        int num_valid_words = valid_words.size();

        // step_size is set to 100, since we sort the first 100, 200, ... words

        int step_size = 100;

        // helper is used for merge sort 

        String[] helper = new String[num_valid_words];

        // copied_valid_words is to maintain the unsorted valid word list

        String[] copied_valid_words = new String[num_valid_words];

        for (int i = 0; i < num_valid_words; i++) {
            copied_valid_words[i] = valid_words.get(i);
        }

        // Variables for calculating time and count of moves/comparisons

        long start_time, end_time, time_elapsed;
        int[] num_comparison = new int[1];

        // The for loop, each iteration sort some number of words (first 100, 200, ... words)

        for (int k = 100; k <= num_valid_words + step_size; k += step_size) {

            // At the beginning, use copied_valid_words to make valid_words unsorted

            for (int i = 0; i < num_valid_words; i++) {
                valid_words.set(i, copied_valid_words[i]);
            }

            // The number of words to be sorted cannot be larger than num_valid_words

            int num_words_sorted = Math.min(k, num_valid_words);

            // Set the count of moves/comparisons to 0,
            // using an int[] variable since Java passes by value for int variables

            num_comparison[0] = 0;

            // Record the time before sorting

            start_time = System.nanoTime();

            // Call the merge sort function to sort the first num_words_sorted words in valid_words
            
            mergeSort(valid_words, 0, num_words_sorted-1, helper, num_comparison);

            // Record the time after sorting
            
            end_time = System.nanoTime();

            // Calculate the time used for sorting
            
            time_elapsed = end_time - start_time;

            // Print the number of words being sorted, the time used, and the count of moves/comparisons.

            System.out.println("Sorting the first " + num_words_sorted + " words took " + time_elapsed + " nanoseconds, and " + num_comparison[0] + " moves/comparisons.");

        }

        // Print the whole sorted valid word list

        System.out.println(valid_words);

    }
}
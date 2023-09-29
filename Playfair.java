import java.util.ArrayList;

public class Playfair {
    private String key;
    private String word;
    private static final char[] alphabet = {'A', 'B', 'C', 'D', 'E',
                                            'F', 'G', 'H', 'I', 'K',
                                            'L', 'M', 'N', 'O', 'P',
                                            'Q', 'R', 'S', 'T', 'U',
                                            'V', 'W', 'X', 'Y', 'Z'};

    public Playfair(String key, String word) {
        this.key = key.toUpperCase();
        this.word = word.toUpperCase();
    }

    public String encrypt() {
        StringBuilder sb = new StringBuilder();
        char[][] matrix = generateMatrix();
        ArrayList<String> digrams = generateDigrams();

        for (String digram : digrams) {
            int[] letter1 = find(matrix, digram.charAt(0) == 'j' ? 'i' : digram.charAt(0));
            int[] letter2 = find(matrix, digram.charAt(1) == 'j' ? 'i' : digram.charAt(1));

            if (letter1[0] == letter2[0]) {
                sb.append(matrix[letter1[1] == matrix.length - 1 ? 0 : letter1[1] + 1][letter1[0]]);
                sb.append(matrix[letter2[1] == matrix.length - 1 ? 0 : letter2[1] + 1][letter2[0]]);
                continue;
            }

            if (letter1[1] == letter2[1]) {
                sb.append(matrix[letter1[1]][letter1[0] == matrix.length - 1 ? 0 : letter1[0] + 1]);
                sb.append(matrix[letter2[1]][letter2[0] == matrix.length - 1 ? 0 : letter2[0] + 1]);
                continue;
            }

            sb.append(matrix[letter1[1]][letter2[0]]);
            sb.append(matrix[letter2[1]][letter1[0]]);
        }

        return sb.toString();
    }

    private char[][] generateMatrix() {
        ArrayList<Character> subtractedAlphabet = subtract(alphabet, key.toCharArray());
        char[] keyArr = removeDuplicates(key.toCharArray());
        char[][] matrix = new char[5][5];
        int i = 0;
        int j = 0;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (i < keyArr.length) {
                    matrix[row][col] = keyArr[i];
                    i++;
                    continue;
                }

                matrix[row][col] = subtractedAlphabet.get(j);
                j++;
            }
        }

        return matrix;
    }

    private ArrayList<String> generateDigrams() {
        ArrayList<String> digrams = new ArrayList<String>();

        for (int i = 0; i < word.length(); i += 2) {
            String str = i != word.length() - 1 ? word.substring(i, i + 2) : word.substring(i, i + 1) + "X";

            if (str.charAt(0) == str.charAt(1)) {
                word = word.substring(0, i + 1) + "X" + word.substring(i + 1, word.length());
                str = str.charAt(0) + "X";
            }

            digrams.add(str);
        }

        return digrams;
    }

    private ArrayList<Character> subtract(char[] arr1, char[] arr2) {
        ArrayList<Character> newArr = new ArrayList<Character>();

        for (int i = 0; i < arr1.length; i++) {
            if (!contains(arr2, arr1[i])) {
                newArr.add(arr1[i]);
            }
        }

        return newArr;
    }

    private char[] removeDuplicates(char[] arr) {
        ArrayList<Character> duplicates = new ArrayList<Character>();

        for(int i = 0; i < arr.length; i++) {
            for(int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    duplicates.add(arr[i]);
                }
            }
        }
        
        char[] newArr = new char[arr.length - duplicates.size()];
        int j = 0;

        for(int i = 0; i < arr.length; i++) {
            if (duplicates.contains(arr[i])) {
                duplicates.remove((Object) arr[i]);
                continue;
            }

            newArr[j] = arr[i];
            j++;
        }

        return newArr;
    }

    private boolean contains(char[] arr, char value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return true;
            }
        }

        return false;
    }

    private int[] find(char[][] matrix, char c) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == c) {
                    return new int[] {col, row};
                }
            }
        }

        return new int[0];
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/*
 * This class contains a Linux inbuilt dictionary
 * and method check_for_word 
 * that is used for checking whether the word is actually in the 
 * dictionary or not
 * and finally returns true if it is there 
 * and false if it is not there.
 * 
 * 
 * 
 */
class WordChecker {
    public static boolean check_for_word(String word) {
        // System.out.println(word);
        try {
            @SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(
                    "/usr/share/dict/american-english"));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.indexOf(word) != -1) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(check_for_word("hai"));
    }
}
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

// requires the book to be in proper original Gutenberg format (just download it from the site, do not clean it)

public class AlphabetizedBook {
    private String title;
    private String author;
    private ArrayList<String> words; // unique and alphabetized
    private ArrayList<Integer> counts; // parallel to words

    public AlphabetizedBook(String fileName) throws IOException {
        words = new ArrayList<String>();
        counts = new ArrayList<Integer>();
        Scanner s = new Scanner(new File(fileName));
        boolean reachedStart = false;
        while (s.hasNextLine()) {
            String line = s.nextLine().trim();
            if (!reachedStart) {
                if (line.length() >= 5 && line.substring(0, 6).equals("Title:")) {
                    this.title = line.substring(7);
                }
                if (line.length() >= 7 && line.substring(0, 7).equals("Author:")) {
                    this.author = line.substring(8);
                }
                if (line.length() >= 34 && line.substring(0, 34).equals("*** START OF THE PROJECT GUTENBERG")) {
                    reachedStart = true;
                }
            } else {
                if (line.length() >= 32 && line.substring(0, 32).equals("*** END OF THE PROJECT GUTENBERG")) {
                    break;
                }
                String[] words = line.split(" ");
                for (String w : words) {
                    if (w.length() > 0) {
                        if (!this.words.contains(w)) {
                            int loc = this.findAlphaLoc(w);
                            this.words.add(loc, w);
                            this.counts.add(loc, 1);
                        } else {
                            this.counts.set(this.words.indexOf(w), this.counts.get(this.words.indexOf(w)) + 1);
                        }
                    }
                }

            }
        }

    }

    // finds spot to insert a word in alphabetical order
    private int findAlphaLoc(String word) {
        for (int i = 0; i < this.words.size(); i++) {
            if (this.words.get(i).compareTo(word) > 0) {
                return i;
            }
        }
        return this.words.size();
    }

    // add your methods below
}

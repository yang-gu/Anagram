package delta.anagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnagramLister {
    private List<List<String>> accumulator;
    Set<String> anags;
    private List<String> file;

    public AnagramLister(List<String> myFile) {
        accumulator = new ArrayList<List<String>>();
        anags = new HashSet<String>();
        file = myFile;
        for (String s : myFile) {
            checkString(s);
        }
    }

    private void checkString(String checked) {
        boolean isNewAnagram;

        char[] chars = checked.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);

        if (anags.contains(sorted)) {
            insertExists(sorted, checked);
        } else {
            anags.add(sorted);
            insertNew(sorted, checked);
        }

    }

    private void insertExists(String sorted, String checked) {
        List<String> ms = getAnag(sorted);
        List<String> ls = ms.subList(1, ms.size());
        if (!ls.contains(checked)) {
            ls.add(checked);
        }
    }

    private void insertNew(String sorted, String checked) {
        List<String> ls = new ArrayList<String>();
        ls.add(sorted);
        ls.add(checked);
        accumulator.add(ls);
    }

    private List<String> getAnag(String sorted) {
        for (int i = 0; i < accumulator.size(); i++) {
            if (accumulator.get(i).get(0).equals(sorted)) {
                return accumulator.get(i);
            }
        }
        return new ArrayList<String>();
    }

    public List<List<String>> getOutput() {
        List<List<String>> lls = new ArrayList<List<String>>();
        for (List<String> l : accumulator) {
            if (l.size() > 2) {
                List<String> ls = new ArrayList<String>();
                for (int i = 1; i < l.size(); i++) {
                    ls.add(l.get(i));
                }
                lls.add(ls);
            }
        }
        return lls;
    }
}

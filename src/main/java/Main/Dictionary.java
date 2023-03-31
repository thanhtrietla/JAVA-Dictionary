package Main;

import java.util.*;
import java.util.function.Supplier;

public class Dictionary<T extends Map<String, String>> {
    private final T vietnameseToEnglish;
    private final T englishToVietnamese;
    private boolean isVietnameseToEnglish;
    private List<String> favoriteVietnameseWords;
    private List<String> favoriteEnglishWords;

    public Dictionary(boolean isVietnameseToEnglish, Supplier<T> mapSupplier) throws Exception{
        this.isVietnameseToEnglish = isVietnameseToEnglish;
        this.vietnameseToEnglish = mapSupplier.get();
        this.englishToVietnamese = mapSupplier.get();
        favoriteVietnameseWords = new ArrayList<String>();
        favoriteEnglishWords = new ArrayList<String>();

        
        XMLReader.readXmlFile("Viet_Anh.xml", this.vietnameseToEnglish);
        XMLReader.readXmlFile("Anh_Viet.xml", this.englishToVietnamese);

    }
    
    public String findWord(String word) {
        Map<String, String> map;
        if (isVietnameseToEnglish) {
            map = vietnameseToEnglish;
        } else {
            map = englishToVietnamese;
        }
        String result = map.get(word);
        if (result == null) {
            result = "Không tìm thấy từ này!";
        }
        return result;
    }

    public String switchLanguage() {
        String currentLanguage;
        if (isVietnameseToEnglish) currentLanguage = "Tiếng Việt";
        else currentLanguage = "Tiếng Anh";
        isVietnameseToEnglish = !isVietnameseToEnglish;
        return currentLanguage;       
    }
    
    public void addWord(String word, String meaning) {
        if (isVietnameseToEnglish) vietnameseToEnglish.put(word.toLowerCase(), meaning);
        else englishToVietnamese.put(word.toLowerCase(), meaning);       
    }
    
    public String deleteWord(String word) {
        Map<String, String> map;
        if (isVietnameseToEnglish) {
            map = vietnameseToEnglish;
        } else {
            map = englishToVietnamese;
        }
        
        if (map.containsKey(word)) {
            map.remove(word);
            return "Xóa thành công!";
        }
        return "Từ không tồn tại!";
    }
    
    public void addToFavorites(String word) {
        List<String> favoriteWords;
        if (isVietnameseToEnglish) {
            favoriteWords = favoriteVietnameseWords;
        } else {
            favoriteWords = favoriteEnglishWords;
        }
        if (!favoriteWords.contains(word)) {
            favoriteWords.add(word);
        }
    }
    
    public void sortFavoritesAscending() {
        List<String> favoriteWords;
        if (isVietnameseToEnglish) {
            favoriteWords = favoriteVietnameseWords;
        } else {
            favoriteWords = favoriteEnglishWords;
        }
        Collections.sort(favoriteWords);
    }

    public void sortFavoritesDescending() {
        List<String> favoriteWords;
        if (isVietnameseToEnglish) {
            favoriteWords = favoriteVietnameseWords;
        } else {
            favoriteWords = favoriteEnglishWords;
        }
        Collections.sort(favoriteWords, Collections.reverseOrder());
    }

    public String favoriteWordsToString() {
        List<String> favoriteWords;
        if (isVietnameseToEnglish) {
            favoriteWords = favoriteVietnameseWords;
        } else {
            favoriteWords = favoriteEnglishWords;
        }
        StringBuilder sb = new StringBuilder();
        for (String word : favoriteWords) {
        sb.append(word).append("\n");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        Dictionary<TreeMap<String, String>> dict = new Dictionary<>(true, TreeMap::new);
        
        MainFrame frame = new MainFrame(dict);
        frame.setVisible(true);

    }
}

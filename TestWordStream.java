import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestWordStream {
  public static void main(String[] args) {
    String filename =null ;
    Stream<String> StringStream;
    Stream<Integer> WordCountStream;
    
    System.out.println("Count of Words in the file");
    StringStream = readWords("C:\\Users\\hp\\Desktop\\words");
    System.out.println(StringStream.count());
    
    System.out.println("Top 100 Words in the file");
    StringStream = readWords("C:\\Users\\hp\\Desktop\\words");
    StringStream.limit(100).forEach(p -> System.out.println(p));
    
    System.out.println("Words have more than 22 letters in the file");
    StringStream = readWords("C:\\Users\\hp\\Desktop\\words");
    
    StringStream.filter(x -> x.length()>= 22).forEach(p -> System.out.println(p));
    
    System.out.println("Words Which are Palindrome");
    StringStream = readWords("C:\\Users\\hp\\Desktop\\words");
    StringStream.filter(x -> isPalindrome(x)).forEach(p ->  System.out.println(p));
    
    
    System.out.println("Words Which are Palindrome Parallel Stream");
    StringStream = readWordsParallelStream("C:\\Users\\hp\\Desktop\\words");
    StringStream.filter(x -> isPalindrome(x)).forEach(p ->  System.out.println(p));
    
    System.out.println("Words Count Stream");
    StringStream = readWords("C:\\Users\\hp\\Desktop\\words");
    List<Integer> wordLength = new ArrayList<Integer>();
    StringStream.forEach(p -> wordLength.add(p.length()));
    WordCountStream = wordLength.stream();
    int min = WordCountStream.min(Integer::compare).get();
    System.out.println("Min length of Words in file is: "+ min);
    int max = wordLength.stream().max(Integer::compare).get();
    System.out.println("max length of Words in file is: "+ max);
    IntSummaryStatistics stats =wordLength
    	    .stream()
    	    .mapToInt(Integer::intValue)
    	    .summaryStatistics();
    
    System.out.println("Average length of Words in file is: "+ stats.getAverage());
  }

  public static Stream<String> readWords(String filename) {
    try {
    	List<String> list = new ArrayList<String>();
    	Stream<String> test ;
    	int count=0;
    	String strCurrentLine;
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      while ((strCurrentLine = reader.readLine()) != null) {

    	  	list.add(strCurrentLine);
    	   
    	    count++;
    	   }
      test = list.stream();
     
      return test; 
    } catch (IOException exn) { 
      return Stream.<String>empty();
    }
  }
  
  public static Stream<String> readWordsParallelStream(String filename) {
	    try {
	    	List<String> list = new ArrayList<String>();
	    	Stream<String> test ;
	    	int count=0;
	    	String strCurrentLine;
	      BufferedReader reader = new BufferedReader(new FileReader(filename));
	      while ((strCurrentLine = reader.readLine()) != null) {

	    	  	list.add(strCurrentLine);
	    	   
	    	    count++;
	    	   }
	      test = list.parallelStream();
	     
	      return test; 
	    } catch (IOException exn) { 
	      return Stream.<String>empty();
	    }
	  }

  public static boolean isPalindrome(String originalString) {
	  String reversedString = new StringBuilder(originalString).reverse().toString();
	  
      return originalString.equals(reversedString);
  }

  public static Map<Character,Integer> letters(String s) {
    Map<Character,Integer> res = new TreeMap<>();
    // TO DO: Implement properly
    return res;
  }
}

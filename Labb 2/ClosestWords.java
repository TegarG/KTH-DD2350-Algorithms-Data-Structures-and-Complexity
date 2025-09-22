/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

  int partDist(String w1, String w2, int w1len, int w2len) {
    if (w1len == 0)
      return w2len; // Tomt ord i w1, krävs w2len insättningar för att bygga ordet
    if (w2len == 0)
      return w1len; // Tomt ord i w2, krävs w1len borttagningar för att få ordet

    // matcha/substituera sista teckent
    int res = partDist(w1, w2, w1len - 1, w2len - 1) + 
	  (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1); 

    // gör w1 kortare => borttagning i w1
    int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;

    // gör w2 kortare => insättning i w1
    int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
    
      return res;
  }

  int distance(String w1, String w2) {
    return partDist(w1, w2, w1.length(), w2.length());
  }

  public ClosestWords(String w, List<String> wordList) {
    for (String s : wordList) {
      int dist = distance(w, s);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}

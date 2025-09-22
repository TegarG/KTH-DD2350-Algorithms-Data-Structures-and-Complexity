/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.math.*;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

  //original function
  // int partDist(String w1, String w2, int w1len, int w2len) {
  //   if (w1len == 0)
  //     return w2len; // Tomt ord i w1, krävs w2len insättningar för att bygga ordet
  //   if (w2len == 0)
  //     return w1len; // Tomt ord i w2, krävs w1len borttagningar för att få ordet

  //   // matcha/substituera sista teckent
  //   int res = partDist(w1, w2, w1len - 1, w2len - 1) + 
	//   (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1); 

  //   // gör w1 kortare => borttagning i w1
  //   int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
  //   if (addLetter < res)
  //     res = addLetter;

  //   // gör w2 kortare => insättning i w1
  //   int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
  //   if (deleteLetter < res)
  //     res = deleteLetter;
  //     return res;
  // }

  // first improvment: create dynprogmatrix and save values
  int[][] M;

  int partDist1(String w1, String w2, int w1len, int w2len) {
    if (M[w1len][w2len] != -1){
      return M[w1len][w2len];
    }

    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;

    int res = partDist(w1, w2, w1len - 1, w2len - 1) + 
	  (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1); 

    int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;

    int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
      return res;
  }

  //second implementation: store all values in the matrix
  int partDist2(String w1, String w2, int w1len, int w2len) {
    for(int i=1; i < w1len + 1; i++){
      for(int j=1; j < w2len + 1; j++){
        M[i][j] = M[i-1][j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);
        
        if(M[i][j] > M[i-1][j] + 1)
          M[i][j] = M[i-1][j] + 1;
        
        if(M[i][j] > M[i][j-1] + 1)
          M[i][j] = M[i][j-1] + 1;
      }
    }
    return M[w1len][w2len];
  }

  //Third implementation: use two arrays
  int[] row1;
  int[] row2;

  int partDist(String w1, String w2, int w1len, int w2len) {
    row1 = new int[w2len + 1];
    row2 = new int[w2len + 1];

    for(int i=0; i < w2.length() + 1; i++){
      row1[i] = i;
      //System.out.println(row1[i]);
    }

    for(int i=1; i < w1len + 1; i++){
      row2[0] = i;

      for(int j=1; j < w2len + 1; j++){
        row2[j] = row1[j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);
        
        if(row2[j] > row2[j-1] + 1)
          row2[j] = row2[j-1] + 1;
        
        if(row2[j] > row1[j] + 1)
          row2[j] = row1[j] + 1;
        
      }

      row1 = row2;
      row2 = new int[w2len + 1];
    }
    return row1[w2len];
  }

  int distance(String w1, String w2) {

    // use for implementation 1 and 2
    /* 
    M = new int[w1.length() + 1][w2.length() + 1];
    M[0][0] = 0;

    // create dynprogmatrix, set first row and column to be 0 to length 
    // rest is -1
    for(int i=1; i < w1.length() + 1; i++){
      for(int j=1; j < w2. length() + 1; j++){
        M[0][j] = j;
        M[i][j] = -1;
      }
      M[i][0] = i;  
    }*/

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

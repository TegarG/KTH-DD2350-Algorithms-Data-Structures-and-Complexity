/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;
  int[][] M;
  String previousw1 = null;
  String previousw2 = null;
  int maxw1 = 0;
  int maxw2 = 0;

  int partDist(String w1, String w2, int w1len, int w2len) {
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

  /// Optimization 1: Save all solved subproblems in m*n matrix
  int partDist1(String w1, String w2, int w1len, int w2len) {

    // If subproblem already solved, return answer
    if(M[w1len][w2len] != -1){
      return M[w1len][w2len];
    }

    if (w1len == 0){
      return w2len;
    }
    if (w2len == 0){
      return w1len;
    }
    int res = partDist(w1, w2, w1len - 1, w2len - 1) + 
	(w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);
    int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;
    int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
    // Save answer in matrix
    M[w1len][w2len] = res;
    return res;
  }

  // Optimization 2: Save all subproblems in matrix. Solve all subproblems iteratively in matrix by checking previous subproblems.
  int partDist2(String w1, String w2, int w1len, int w2len) {
    int m = w1len+1;
    int n = w2len+1;
    M = new int[m][n]; // Create m*n matrix storing subproblems

    // Base case: Set first row and col to i or j
    for (int i = 0; i < m; i++) {
        M[i][0] = i;
    }

    for (int j = 0; j < n; j++) {
        M[0][j] = j;
    }

    // Solve matrix row for row
    for (int i = 1; i<m; i++) {
        for (int j = 1; j<n; j++) {

          // Match or substitution
          int res = M[i-1][j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);

          int addLetter = M[i-1][j] + 1;
          if (res > addLetter) {
              res = addLetter;
          }
          int deleteLetter = M[i][j-1] + 1;
          if (res > deleteLetter) {
              res = deleteLetter;
          }

          // Store the result with the least cost
          M[i][j] = res;
        }
    }

    // Return min distance
    return M[m-1][n-1];
  }

  /// Optimization 3: Store only the 2 rows in matrix, the previous and current row.
  int partDist3(String w1, String w2, int w1len, int w2len) {
    int m = w1len + 1;
    int n = w2len + 1;

    int[] current = new int[n];
    int[] previous = new int[n];

    // Base case: Set previous row to i
    for (int i = 0; i < n; i++){
      previous[i] = i;
    }

    //Solve matrix row for row, but keep only the previous and current rows.
    for (int i = 1; i < m; i++){
      // Base case: Set first index in current row to i.
      current[0] = i;
      for (int j = 1; j < n; j++) {

          // Match or substitution
          int res = previous[j - 1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);

          int addLetter = previous[j] + 1;
          if (res > addLetter) {
              res = addLetter;
          }
          int deleteLetter = current[j-1] + 1;
          if(res > deleteLetter){
            res = deleteLetter;
          }
          //Store the result with the least cost
          current[j] = res;
      }

      // Switch rows
      int[] temp = previous;
      previous = current;
      current = temp;
    }

    // Return min distance
    return previous[n-1];
  }

  int partDist4(String w1, String w2, int w1len, int w2len) {
    int m = w1len + 1;
    int n = w2len + 1;

    // Keep track of previous row
    int[] previous = new int[n];

    // Base case: Set previous row to i
    for (int i = 0; i < n; i++) {
        previous[i] = i;
    }

    // Solve matrix row by row
    for (int i = 1; i < m; i++) {
        // Left diagonal cell will be previous first index
        int diagonal  = previous[0];
        // Left cell will be left diagonal cell incremented by one
        int left = previous[0]++;
        for (int j = 1; j < n; j++) {
            int addOrDelete = Math.min(left, previous[j]) + 1;
            int matchOrSub = diagonal + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);

            // Left cell will be current cell
            left = Math.min(addOrDelete, matchOrSub);
            // Left upper diagonal cell will be current top cell
            diagonal = previous[j];
            // Top cell will be current left cell in next iteration
            previous[j] = left;
        }
    }
    return previous[n-1];
  }

  int partDist5(String w1, String w2, int w1len, int w2len) {
    int startIndex = 0;
    int endIndex1 = w1len;
    int endIndex2 = w2len;

    while (startIndex < endIndex1 && startIndex < endIndex2 && w1.charAt(startIndex) == w2.charAt(startIndex)) { 
        startIndex++;
    }

    while (startIndex < endIndex1 && startIndex < endIndex2 && w1.charAt(endIndex1-1) == w2.charAt(endIndex2-1)) { 
        endIndex1--;
        endIndex2--;
    }

    w1len = endIndex1 - startIndex;
    w2len = endIndex2 - startIndex;

    w1 = w1.substring(startIndex, endIndex1);
    w2 = w2.substring(startIndex, endIndex2);

    int m = w1len + 1;
    int n = w2len + 1;

    // Keep track of previous row
    int[] previous = new int[n];

    // Base case: Set previous row to i
    for (int i = 0; i < n; i++) {
        previous[i] = i;
    }

    // Solve matrix row by row
    for (int i = 1; i < m; i++) {
        // Left diagonal cell will be previous first index
        int diagonal  = previous[0];
        // Left cell will be left diagonal cell incremented by one
        int left = previous[0]++;
        for (int j = 1; j < n; j++) {
            int addOrDelete = Math.min(left, previous[j]) + 1;
            int matchOrSub = diagonal + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);

            // Left cell will be current cell
            left = Math.min(addOrDelete, matchOrSub);
            // Left upper diagonal cell will be current top cell
            diagonal = previous[j];
            // Top cell will be current left cell in next iteration
            previous[j] = left;
        }
    }
    return previous[n-1];
  }

  int partDist6(String w1, String w2, int w1len, int w2len) {
    
    // Longest common prefix index
    int lcp = 1;
    // If there is a previous w1 and w2
    if(previousw1 != null && previousw2 != null && previousw1.equals(w1)){
      int minLength = Math.min(w2len, previousw2.length());
      for(int i = 0; i < minLength; i++){
        if(previousw2.charAt(i) != w2.charAt(i)) break;
        lcp++; // Count the nr of common letters
      }
    }

    // Set new previous words
    previousw1 = w1;
    previousw2 = w2;

    int m = w1len+1;
    int n = w2len+1;

    // Solve matrix row for row
    for (int i = 1; i<m; i++) {
        for (int j = lcp; j<n; j++) {

          // Match or substitution
          int res = M[i-1][j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);

          int addLetter = M[i-1][j] + 1;
          if (res > addLetter) {
              res = addLetter;
          }
          int deleteLetter = M[i][j-1] + 1;
          if (res > deleteLetter) {
              res = deleteLetter;
          }

          // Store the result with the least cost
          M[i][j] = res;
        }
    }

    // Return min distance
    return M[m-1][n-1];
  }

  int distance(String w1, String w2) {

    /// Optimization 1: Memoization with same algorithm
    // M = new int[w1.length()+1][w2.length()+1];

    // for (int i = 0; i < w1.length()+1; i++) {
    //     M[i][0] = i;
    // }

    // for (int j = 0; j < w2.length()+1; j++) {
    //     M[0][j] = j;
    // }

    // for(int i = 1; i < w1.length()+1; i++){
    //   for(int j = 1; j < w2.length()+1; j++){
    //     M[i][j] = -1;
    //   }
    // }
    //return partDist1(w1, w2, w1.length(), w2.length());

    /// Optimization 2: Non recursive with memoization
    // return partDist2(w1, w2, w1.length(), w2.length());

    /// Optimization 3: Memory efficient and less memory overhead, 2 arrays
    // return partDist3(w1, w2, w1.length(), w2.length());


    /// Optimization 4: 1 array
    // return partDist4(w1, w2, w1.length(), w2.length());

    // return partDist5(w1, w2, w1.length(), w2.length());

    return partDist6(w1, w2, w1.length(), w2.length());

    /// No optimization
    //return partDist(w1, w2, w1.length(), w2.length());
  }

  public ClosestWords(String w, List<String> wordList) {
    // Initialize matrix M
    int m = w.length() + 1; // Length of w1
    int n = 0;
    for (String s : wordList) {
        if (n < s.length()){
          n = s.length();
        }
    }
    M = new int[m][n+1];

    // Base case: Set first row and col to i or j
    for (int i = 0; i < m; i++) {
        M[i][0] = i;
    }

    for (int j = 0; j < n+1; j++) {
        M[0][j] = j;
    }
    for (String s : wordList) {
      int dist = distance(w, s);
      //if (maxw1 < w.length()) maxw1 = w.length();
      //if (maxw2 < s.length()) maxw2 = s.length();
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
    //System.out.println("Word 1: " + maxw1 + ", Word 2: " + maxw2);
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}

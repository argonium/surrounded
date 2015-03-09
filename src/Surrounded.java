/**
 * This class has a public method to extract a snippet of text from
 * a longer text string.  The method is:
 *
 *    public static String getSurroundingWords(final String text,
 *                                             final int charIndex,
 *                                             final int numWords)
 *
 * The arguments are the string to search, the index of a character
 * in the word to have the snippet centered around, and the number
 * of words to include in the snippet.
 * 
 * @author mwallace
 * @version 1.0
 */
public final class Surrounded
{
  /**
   * Default constructor.
   */
  private Surrounded()
  {
    super();
  }
  
  
  /**
   * This method returns the beginning of the line parameter,
   * just enough so that it contains the first nWordCount
   * number of words.
   * 
   * @param line the input string
   * @param nWordCount the number of words at the beginning to include
   * @return the portion of line containing nWordCount number of
   *         words, counting from the beginning
   */
  private static String getFirstWords(final String line,
                                      final int nWordCount)
  {
      // Check the inputs for a null or empty string, or
      // a requested word count less than one
      if ((line == null) || (line.length() < 1))
      {
          return "";
      }
      else if (nWordCount < 1)
      {
          return "";
      }
      
      // Set up our variables
      int index = 0;  // current index in the input string
      int currentWordCount = 0; // current number of words reached
      final int nLineLength = line.length(); // input string length
      
      // Iterate through the input string until we've hit enough
      // words to satisfy the input requirements or we're at
      // the end of the input string, whichever comes first
      do
      {
          // Look for starting spaces
          while ((index < nLineLength) && (line.charAt(index) == ' '))
          {
              ++index;
          }
          
          // If we haven't hit the end of the line yet, we've
          // hit a non-space character
          if (index < nLineLength)
          {
              // Increment our counter
              ++currentWordCount;
              
              // Find the index of the next space
              index = line.indexOf(' ', index);
              
              // Check if no space was found
              if (index < 0)
              {
                  // No more spaces, so set the index to the end
                  // of the input string
                  index = nLineLength;
              }
          }
      } while ((index < nLineLength) && (currentWordCount < nWordCount));
      
      // Return the substring requested by the user
      return line.substring(0, index).trim();
  }
  
  
  /**
   * Returns the index of the first non-space character before
   * this index, in the argument text.
   * 
   * @param text the text to search
   * @param startIndex the starting index
   * @return the index of the first non-space character before
   *         the startIndex index
   */
  private static int findPreviousNonspace(final String text,
                                          final int startIndex)
  {
      // Check the input
      if ((text == null) || (startIndex < 0))
      {
          return -1;
      }
      
      // Check for an invalid value for startIndex
      if (startIndex >= text.length())
      {
          throw new RuntimeException("Error 17 in findPreviousNonspace");
      }
      
      // Find the first non-space before this character
      int charIndex = startIndex;
      while ((charIndex >= 0) && (text.charAt(charIndex) == ' '))
      {
          --charIndex;
      }
      
      // Return the index
      return charIndex;
  }
  
  
  /**
   * Returns the index of the first non-space character after
   * this index, in the argument text.
   * 
   * @param text the text to search
   * @param startIndex the starting index
   * @return the index of the first non-space character after
   *         the startIndex index
   */
  private static int findNextNonspace(final String text,
                                      final int startIndex)
  {
      // Check the input
      if ((text == null) || (startIndex < 0))
      {
          return -1;
      }
      
      // Find the first non-space after this character
      int charIndex = startIndex;
      final int nLen = text.length();
      while ((charIndex < nLen) && (text.charAt(charIndex) == ' '))
      {
          ++charIndex;
      }
      
      // Check if we hit the end of the string
      if (charIndex == nLen)
      {
          // We did, so return -1
          return -1;
      }
      
      // Return the index
      return charIndex;
  }
  
  
  /**
   * Returns the index of the start of the word before the
   * current point.
   * 
   * @param text the text to search
   * @param startIndex the index to start at
   * @return the index of the start of the word before startIndex
   */
  private static int findStartOfPreviousWord(final String text,
                                             final int startIndex)
  {
      // Check the input
      if ((text == null) || (startIndex <= 0))
      {
          return -1;
      }
      
      // Find the index of the first, previous non-space.  If
      // the current character is a non-space, nothing is done.
      int index = findPreviousNonspace(text, startIndex - 1);
      if (index < 0)
      {
          // There are no words before this
          return -1;
      }
      
      // Look for the start of the current word
      while ((index >= 0) && (text.charAt(index) != ' '))
      {
          --index;
      }
      
      // If we went below zero, then the word starts at 0
      if (index < 0)
      {
          return 0;
      }
      
      // Else, just return the value + 1
      return (index + 1);
  }
  
  
  /**
   * Returns the index of the end of the word after the
   * current point.
   * 
   * @param text the text to search
   * @param startIndex the index to start at
   * @return the index of the end of the word after startIndex
   */
  private static int findEndOfNextWord(final String text,
                                       final int startIndex)
  {
      // Check the input
      if ((text == null) || (startIndex < 0))
      {
          return -1;
      }
      
      // Find the index of the next non-space.  If
      // the current character is a non-space, nothing is done.
      int index = text.indexOf(' ', startIndex);
      if (index < 0)
      {
          // There are no more words
          return -1;
      }
      
      // We have the index of the next space, so find the
      // index of the next non-space
      index = findNextNonspace(text, index);
      if (index < 0)
      {
          // There are no words after this
          return -1;
      }
      
      // We're at the start of the next word, so find its end
      index = text.indexOf(' ', index);
      if (index < 0)
      {
          // The line ends with this word, so return the index of the
          // character at the end of the index string
          return (text.length() - 1);
      }
      
      // We found a space, so return one before that
      return (index - 1);
  }
  
  
  /**
   * Check whether the specified word indices are valid.
   * 
   * @param startIndex the starting word index
   * @param endIndex the ending word index
   * @param textLen the length of the input text
   * @return whether the indices are valid
   */
  private static boolean wordBoundariesAreValid(final int startIndex,
                                                final int endIndex,
                                                final int textLen)
  {
    // Check for invalid word indices
    if ((startIndex < 0) || (startIndex > endIndex)
        || (endIndex >= textLen) || (endIndex < 1))
    {
      return false;
    }
    
    // The indices must be valid
    return true;
  }
  
  
  /**
   * Finds the start of the current word, using the character
   * pointed to by charIndex.
   * 
   * @param text the text to search
   * @param length the length of text
   * @param charIndex the index of a character in text
   * @return the start of the current word
   */
  private static int findStartOfCurrentWord(final String text,
                                            final int length,
                                            final int charIndex)
  {
    // Check if charIndex points to a space
    int middleIndex = charIndex;
    while ((middleIndex < length) && (text.charAt(middleIndex) == ' '))
    {
      // It does, so search for a non-space
      ++middleIndex;
    }
    
    // Check the value for middleIndex
    if (middleIndex >= length)
    {
      // We got to the end of the input string, so backtrack until we get to
      // a non-space
      --middleIndex;
      while ((middleIndex >= 0) && (text.charAt(middleIndex) == ' '))
      {
        --middleIndex;
      }
      
      // Check the value for middleIndex
      if (middleIndex < 0)
      {
        // There don't seem to be any words here
        middleIndex = -1;
      }
    }
    
    // Check for a bad value of middleIndex
    if (middleIndex < 0)
    {
      return middleIndex;
    }
    
    // If we reach this point, we're pointing to a character.
    // Find the start of the string.
    int tempIndex = middleIndex - 1;
    while ((tempIndex >= 0) && (text.charAt(tempIndex) != ' '))
    {
      // Keep going backwards until we hit a space
      --tempIndex;
    }
    
    // Update middleIndex
    middleIndex = tempIndex + 1;
    
    // Return the calculated index
    return middleIndex;
  }
  
  
  /**
   * Return a snippet of text, containing numWords number of
   * words.  The snippet will, ideally, be centered around the
   * word located at middleIndex.  Leading and trailing ellipses
   * will be prepended and appended, respectively, if there
   * are additional leading and trailing words.
   * 
   * @param text the text to return a snippet from
   * @param charIndex the index of a character in the word to have at
   *                    the center of the snippet
   * @param numWords the total number of words to return
   * @return a snippet of text, containing up to numWords number of words
   */
  public static String getSurroundingWords(final String text,
                                           final int charIndex,
                                           final int numWords)
  {
      // Check the input
      if ((text == null) || (charIndex < 0) || (numWords < 1))
      {
          // Bad input, so there's nothing to do
          return "";
      }
      
      // Save the length of text
      final int nLength = text.length();
      if (nLength < 1)
      {
        // The string is empty
        return "";
      }
      
      // Make sure we're starting off at the start of a word
      int middleIndex = findStartOfCurrentWord(text, nLength, charIndex);
      if (middleIndex < 0)
      {
        // There is no word in the string to start at
        return "";
      }
      
      // Store the current index of the previous and next words
      int nPrevWordIndex = Math.max((middleIndex - 1), 0);
      int nNextWordIndex = Math.max(text.indexOf(' ', middleIndex) - 1,
                                    middleIndex);
      
      // Store the number of previous and next word counts
      int nPrevWordCount = 0;
      int nNextWordCount = 1;
      
      // Store whether to continue processing
      boolean bContinue = true;
      boolean bContinuePrevSearch = true;
      boolean bContinueNextSearch = true;
      
      // Parse the text, looking for more words before and after
      // middleIndex
      while ((bContinue) && ((nPrevWordCount + nNextWordCount) < numWords))
      {
          // Look for another previous word
          if (bContinuePrevSearch)
          {
              // Find the start of the previous word
              int nTempIndex = findStartOfPreviousWord(text, nPrevWordIndex);
              if (nTempIndex >= 0)
              {
                  // We found a match, so update the number of previous words
                  nPrevWordIndex = nTempIndex;
                  ++nPrevWordCount;
              }
              else
              {
                  bContinuePrevSearch = false;
              }
          }
          
          // Look for another next word if we haven't met our quota yet
          if ((bContinueNextSearch)
              && ((nPrevWordCount + nNextWordCount) < numWords))
          {
              // Find the start of the next word
              int nTempIndex = findEndOfNextWord(text, nNextWordIndex);
              if (nTempIndex >= 0)
              {
                  // We found a match, so update the number of next words
                  nNextWordIndex = nTempIndex;
                  ++nNextWordCount;
              }
              else
              {
                  bContinueNextSearch = false;
              }
          }
          
          // Update bContinue
          bContinue = (bContinuePrevSearch || bContinueNextSearch);
      }
      
      // Check the indices
      if (!wordBoundariesAreValid(nPrevWordIndex, nNextWordIndex, nLength))
      {
        // They're invalid, so something went wrong.  Return the
        // first n words.
        return (getFirstWords(text, numWords));
      }
      
      // Declare the string that gets returned
      StringBuilder sb = new StringBuilder(250);
      
      // If there is more text before the first word, prepend ellipsis
      if (nPrevWordIndex > 0)
      {
          sb.append("...");
      }
      
      // Build the substring of text, using the first and last word
      // indices, adding the word starting at the last word index
      sb.append(text.substring(nPrevWordIndex, nNextWordIndex + 1).trim());
      
      // If there is more text after the last word, add ellipsis
      if (nNextWordIndex < (nLength - 1))
      {
          sb.append("...");
      }
      
      // Return the generated string
      return sb.toString();
  }
  
  
  /**
   * Main entry point for the application.
   * 
   * @param args arguments supplied by the user
   */
  public static void main(final String[] args)
  {
    String s = getSurroundingWords(
                 "Here is a string of words forming a sentence.", 10, 3);
    System.out.println(s);
  }
}

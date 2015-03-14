# surrounded
The Surrounded class lets a developer extract a portion of text from a longer string. The extracted portion is based on the number of words surrounding a particular character. The output is similar to the results returned by Google: a snippet of text centered around a particular word. This class has one public method:

```
  public static String getSurroundingWords(final String text,
                                           final int charIndex,
                                           final int numWords)
```

The arguments are (respectively): the text to extract a snippet from, the index of a character in the word that the snippet will be centered around, and the number of words to include in the snippet (using spaces to delineate word boundaries).

For example, this sample code:

```
  String s = Surrounded.getSurroundingWords(
               "Here is a string of words forming a sentence.", 10, 3);
  System.out.println(s);
```

will generate this output:

```
  ...a string of...
```

As you can see, the code will prepend (and/or append) ellipses to the snippet if the start (and/or end) of the input text is not included in the result.

The source code is released under the MIT license.

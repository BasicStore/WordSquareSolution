Feature: Test Word Square

  Scenario: Ensure word squares use only words existing in the given dictionary
	    Given a dictionary of words
	    When a request is made to find a word square set of 4 letter words 
	    And a character set "eeeeddoonnnsssrv" is specified from which to construct word squares     
 			Then a word square of 4 words from the dictionary will be produced by the word square producer
 
  Scenario: Ensure word squares are actually word squares
 			Given a dictionary of words
 			When a request is made to find a word square set of 4 letter words
 			And a character set "eeeeddoonnnsssrv" is specified from which to construct words
 			Then verify that the 4 words produced equate to actual word squares of 4 letters

  Scenario: Ensure word square output does not specify duplicate sequences of word squares
 			Given a dictionary of words
 			When a request is made to find a word square set of 4 letter words
 			And a character set "eeeeddoonnnsssrv" is specified from which to construct words
 			Then verify that each word square of the output is a unique sequence of word squares
	
	Scenario: Ensure capitals in the dictionary are interpreted as lower case values
 			Given a dictionary where some letters are upper case and others are lower case 
 			When a request is made to find a word square set of 4 letter words
 			And a character set "eeeeddoonnnsssrv" is specified from which to construct words
 			Then verify that the word squares produced are valid word squares of 4 word squares
 			And that the words produced are all in lower case 

	Scenario: Ensure that each word square uses only those letters provided
 			Given a dictionary of words 
 			When a request is made to find a word square set of 4 letter words
 			And a character set "eeeeddoonnnsssrv" is specified from which to construct word squares
 			Then verify that the 4 words produced equate to an actual word square
 			And that the words produced are all in lower case 

	Scenario: Check a single word square is produced when just a single word letter is provided as input
 			Given a dictionary of words 
 			When a request is made to find a word square set of 1 letter only
 			And a character set "a" is specified from which to construct a word square
 			Then verify that a single word square is produced containing the word "a"
 			
	Scenario: Check no word square is produced when just a single non-word letter is provided as input
 			Given a dictionary of words 
 			When a request is made to find a word square set of 1 letter only
 			And a character set "z" is specified from which to construct a word square
 			Then verify that an empty result set is returned


	Scenario: Check no word square is produced when there are no characters specified
 			Given a dictionary of words 
 			When a request is made to find a word square set of 0 letter only
 			And a character set "" is specified from which to construct a word square
 			Then verify that an empty result set is returned


	Scenario: Verify an input exception is thrown when the string size does not match the specified number of word squares
 			Given a dictionary of words 
 			When a request is made to find a word square set of 4 letter words			
      And a character set "eeeeddoonnnsssrvpopopopop" is specified from which to construct words
			Then an invalid input exception is thrown


	Scenario: test for different sizes of input

  Scenario: test for sequences of output (depending on final implementation)






WordSquareSolution App

This is an application to find word squares from a specified dictionary.
The dictionary defines one word per line in a text file.


The dictionary can contains words of any latin based language and is defined in the 
application config properties file:
WordSquareSolution/src/main/resources/config.properties


Config properties:  
dictionary_path - the dictionary input text file location
max_word_squares - the maximum number of word squares permitted is defined in this
page_size - where multiple word squares are sought the output is paged


This application can be started from the command line here:
WordSquareSolution com.naimuri.controller.WordSquareMain


Functionality
The user may search for word squares within the boundaries of specified user input criteria.
The results can be:
- as a list of word squares (this is view in paged form)
- the system just displays a single square word
  The running system will not return the same square word for an identical search until
  all possible searches have taken place 

    
Potential issues:
- there is no provision for manipulating the given words in a dictionary, for example by 
  chaning words by number, gender or case


PLEASE NOTE the following is incomplete:
- acceptance tests are defined but not implemented, although the basic functionality is covered in the unit tests 
- mockito unit tests need to be written to test the client in CommandLineView  

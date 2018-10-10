package com.naimuri.engine;
import org.junit.Test;
import com.naimuri.model.LettersAvailable;
import static org.junit.Assert.*;
import java.util.Set;
import java.util.stream.Collectors;


public class WordConstructorTest {
	
	private LettersAvailable sq4LettersA;
	
	public WordConstructorTest() {
		sq4LettersA = new LettersAvailable(4, "eeeeddoonnnsssrv");
	}
	
	
	@Test
	public void testStore() {
		WordConstructor wdConstruct = new WordConstructor();
		wdConstruct.store("rose");
		Set<String> resultSet = wdConstruct.searchPossibleNextWords("ro", sq4LettersA);
		String actual = resultSet.stream().collect(Collectors.toList()).get(0);
		assertEquals("rose", actual);
		assertEquals("rose", wdConstruct.getFilteredWordList().get(0));
		assertNotEquals("days", wdConstruct.getFilteredWordList().get(0));
		
	}
	
	
	@Test
	public void testSearchPossibleNextWords() {
		WordConstructor wdConstruct = new WordConstructor();
		wdConstruct.store("rose");
		wdConstruct.store("send");
		wdConstruct.store("ends");
		wdConstruct.store("oven");
		
		Set<String> resultSet = wdConstruct.searchPossibleNextWords("sen", sq4LettersA);
		String actual = resultSet.stream().collect(Collectors.toList()).get(0);
		assertEquals("send", actual);
		
		resultSet = wdConstruct.searchPossibleNextWords("ov", sq4LettersA);
		actual = resultSet.stream().collect(Collectors.toList()).get(0);
		assertEquals("oven", actual);
		
		resultSet = wdConstruct.searchPossibleNextWords("end", sq4LettersA);
		actual = resultSet.stream().collect(Collectors.toList()).get(0);
		assertEquals("ends", actual);
		
		resultSet = wdConstruct.searchPossibleNextWords("sends", sq4LettersA);
		assertEquals(0, resultSet.size());
	}
	
	
}

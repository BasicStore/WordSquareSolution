package com.naimuri.engine;
import org.junit.Test;
import com.naimuri.model.LettersAvailable;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class LettersAvailableTest {

	LettersAvailable sq4LettersA;
	
	public LettersAvailableTest() {
		sq4LettersA = new LettersAvailable(4, "eeeeddoonnnsssrv");
	}
	
	
	@Test
	public void testUsesValidLettersOnInputLetters() {
		assertTrue(sq4LettersA.usesValidLetters("rose"));
		assertTrue(sq4LettersA.usesValidLetters("Rose"));
		assertFalse(sq4LettersA.usesValidLetters("cups"));
		assertFalse(sq4LettersA.usesValidLetters("mice"));
		assertTrue(sq4LettersA.usesValidLetters("oven"));
		assertFalse(sq4LettersA.usesValidLetters("groovy"));
	}
	
	
	@Test
	public void testUsesValidLettersForWordSquareValidity() {
		List<String> wordList = Arrays.asList(new String[] {"rose", "oven", "send", "ends"});
		assertTrue(sq4LettersA.usesValidLetters(wordList));
		wordList = Arrays.asList(new String[] {"days", "oven", "glee", "ends"});
		assertFalse(sq4LettersA.usesValidLetters(wordList));
	}
	
	
	@Test(expected = CloneNotSupportedException.class)
	public void testClone() throws CloneNotSupportedException  {
		sq4LettersA.clone();
	}
	
}



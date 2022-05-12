package com.treasuremap.app.model;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;

/**
 * AdventurerParserTest
 */
public class AdventurerParserTest {

	/**
	 * Parsing file 'adventurer_test.txt' should produces expected adventurer:
	 * {name = 'John', position = (1,1), orientation = EAST, path = 'AADADAGA'}.
	 *
	 * Testing method {@link AdventurerParser#parseFile(String)}.
	 */
	@Test
	public void reading_file_should_produce_correct_adventurer() throws IOException {
		AdventurerParser parser = AdventurerParser.getInstance();

		Adventurer adventurer = parser.parseFile("src/test/resources/adventurer_test.txt").get(0);

		assertEquals("John", adventurer.getName());
		assertEquals(1, adventurer.getX());
		assertEquals(1, adventurer.getY());
		assertEquals(Orientation.EAST, adventurer.getOrientation());
		assertEquals("AADADAGA", adventurer.getPath());
	}
}

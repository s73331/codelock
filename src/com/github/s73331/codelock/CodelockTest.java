package com.github.s73331.codelock;

import static org.junit.Assert.*;

import org.junit.Test;

public class CodelockTest {
	@Test
	public void testConstructorNull() {
		try {
			new Door(null);
			fail("door accepts illegal argument null");
		} catch (Exception e) {
			if (!(e instanceof NullPointerException || e instanceof IllegalArgumentException)) {
				fail("door throws unsuited exception " + e.getClass() + " when argument is null");
			}
		}
	}

	@Test
	public void testConstructorEmptyString() {
		try {
			new Door("");
			fail("door accepts illegal argument empty string");
		} catch (Exception e) {
			if (!(e instanceof IllegalArgumentException)) {
				fail("door throws unsuited exception " + e.getClass() + " when argument is empty string");
			}
		}
	}

	@Test
	public void testConstructorAlphanumeric() {
		String[] testStrings = { "123a", "abc", "def", "kek", "%%", "0xFFFF" };
		for (String string : testStrings) {
			try {
				new Door("123a");
				fail("door accepts illegal argument" + string);
			} catch (Exception e) {
				if (!(e instanceof IllegalArgumentException)) {
					fail("door throws unsuited exception " + e.getClass() + " when argument is alphanumerical string: "
							+ string);
				}
			}
		}
	}

	@Test
	public void testMainNull() {
		Door.main(null);
	}

	@Test
	public void testMainEmptyArgs() {
		Door.main(new String[0]);
	}

	@Test
	public void testMainMultipleArgs() {
		for (int i = 0; i < 100; i++) {
			Door.main(new String[i]);
		}
	}

	@Test
	public void testMainNullString() {
		Door.main(new String[] { null });
	}

	@Test
	public void testMainEmptyString() {
		Door.main(new String[] { "" });
	}

	@Test
	public void testMainAlphanumericalString() {
		Door.main(new String[] { "0xff" });
	}

	@Test
	public void testPrintUsage() {
		for (int i = 0; i < 1000; i++) {
			Door.printUsage();
		}
	}

}

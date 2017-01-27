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
		try {
			Door.main(null);
		} catch (Exception e) {
			fail("Door.main throws Exception when args is null");
		}
	}

	@Test
	public void testMainEmptyArgs() {
		try {
			Door.main(new String[0]);
		} catch (Exception e) {
			fail("Door.main throws Exception when args is empty array");
		}
	}

	@Test
	public void testMainMultipleArgs() {
		for (int i = 0; i < 100; i++)
			try {
				Door.main(new String[i]);
			} catch (Exception e) {
				fail("Door.main throws Exception when args is array of length " + i);
			}
	}

	@Test
	public void testMainNullString() {
		try {
			Door.main(new String[] { null });
		} catch (Exception e) {
			fail("Door.main throws Exception when args[0] is null");
		}
	}

	@Test
	public void testMainEmptyString() {
		try {
			Door.main(new String[] { "" });
		} catch (Exception e) {
			fail("Door.main throws Exception when args[0] is empty string");
		}
	}

	@Test
	public void testMainAlphanumericalString() {
		try {
			Door.main(new String[] { "0xff" });
		} catch (Exception e) {
			fail("Door.main throws Exception when args[0] is 0xff");
		}
	}

}

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * jUnit tests
 */
public class InputTest {

	@Test
	public void typical() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x = y"));
		assertEquals("x - y = 0", s.trim());
	}
	
	@Test
	public void noVar() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + 22 = y"));
		assertEquals("x + 22 - y = 0", s.trim());
	}
	
	@Test
	public void twoVar() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + 22 = y + 33"));
		assertEquals("-11 + x - y = 0", s.trim());
	}
	
	@Test
	public void multipleVar() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + y + z = y + a + b + c"));
		assertEquals("x + z - a - b - c = 0", s.trim());
	}
	
	@Test
	public void noCoefficient() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("23424x = 234 - asf"));
		assertEquals("23424x - 234 + asf = 0", s.trim());
	}	

	
	@Test
	public void brackets() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + (22) = y + 33"));
		assertEquals("-11 + x - y = 0", s.trim());
	}
	
	@Test
	public void multipleBrackets() throws Exception{
		String s = Canonicalize.createOutput(Canonicalize.parseInput("a + b + (c) - (d - e) = a -b"));
		assertEquals("2b + c - d + e = 0", s.trim());
	}
	
	@Test
	public void zero() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x = x"));
		assertEquals("= 0", s.trim());
	}
	
	@Test
	public void firstNegative() throws Exception{
		String s = Canonicalize.createOutput(Canonicalize.parseInput("a + ( b - c )  = 5a + 22"));
		assertEquals("-4a + b - c - 22 = 0", s.trim());
	}

}

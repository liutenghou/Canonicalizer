import static org.junit.Assert.*;

import org.junit.Test;

/*
 * jUnit tests
 */
public class InputTest {

	@Test
	public void t1() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x = y"));
		assertEquals("x - y = 0", s.trim());
	}
	
	@Test
	public void t2() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + 22 = y"));
		assertEquals("x + 22 - y = 0", s.trim());
	}
	
	@Test
	public void t3() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + 22 = y + 33"));
		assertEquals("-11 + x - y = 0", s.trim());
	}
	
	@Test
	public void t4() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + y + z = y + a + b + c"));
		assertEquals("x + z - a - b - c = 0", s.trim());
	}
	
	@Test
	public void t5() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("23424x = 234 - asf"));
		assertEquals("23424x - 234 + asf = 0", s.trim());
	}	
	
	//does not handle out of order variables
	@Test
	public void outOfOrderVariables() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("xy = yx"));
		assertEquals("0 = 0", s.trim());
	}
	
	
	
	@Test
	public void brackets() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x + (22) = y + 33"));
		assertEquals("-11 + x - y = 0", s.trim());
	}
	
	@Test
	public void zero() throws Exception{
		
		String s = Canonicalize.createOutput(Canonicalize.parseInput("x = x"));
		assertEquals("= 0", s.trim());
	}

}

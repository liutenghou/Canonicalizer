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
		assertEquals("x + 22 - y - 33 = 0", s.trim());
	}

}

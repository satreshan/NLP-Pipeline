package actions;
import static org.junit.Assert.*;

//import org.junit.internal.runners.statements.Fail;
import org.junit.Test;

public class StringReverseTest {
    
	@Test
	public void reverseTest(){
		//fail("Not yet implemented");
		StringHelper helper = new StringHelper();
		assertEquals("BA",helper.rev("AB"));
	}
}

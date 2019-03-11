import java.util.Date;

public class Test {
	public static void main (String[] args) {
		File f = new File("Test•");
		f.setName("test");
		
		for (int x = 0; x < 1000000; x++)
		{
			System.out.println(f.getUsePeriod());
			f.setName("test");
		}
		
		System.out.println(f.getUsePeriod());
	}
}

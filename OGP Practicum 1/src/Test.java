import java.util.Date;

public class Test {
	public static void main (String[] args) {
		File f = new File("Test•", 0, false);
		try {
			f.shorten(10);
		} catch (UnauthorizedException er) {
			System.out.println(er);
			f.setWritable(true);
			f.enlarge(10);
			f.setWritable(false);
		}
		
	}
}

import filesystem.File;
import filesystem.Directory;
import filesystem.Link;
import filesystem.RealItem;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RealItem re = (RealItem) new File(null, null, null);
		File f = (File) re;
		
		Directory d = new Directory(null);
		Link l = new Link(d, null, false);
		
		re = (RealItem)d;
		
		re = (RealItem)l;
	}

}

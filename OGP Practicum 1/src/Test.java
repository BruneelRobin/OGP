import filesystem.File;
import filesystem.Directory;
import filesystem.Link;
import filesystem.RealItem;
import filesystem.Type;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Directory rootDir = new Directory("root");
		Directory parDir = new Directory(rootDir, "parent");
		File file_1 = new File(parDir, "file", Type.JAVA);
		System.out.println(file_1.getAbsolutePath());
		
	}
 
}

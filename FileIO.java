import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

	public static void main(String[] args){
		String fileName = args[0];
		File file = new File(fileName);
		File[] files = file.listFiles();
		for(File subFile : files){
			try {
				replace(subFile,file.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	static void replace (File file,String parentPath) throws Exception{
		if(file.isDirectory()){
			File[] subFiles = file.listFiles();
			for(File subFile : subFiles){
				replace(subFile,file.getPath());
			}
		}else if(file.isFile()){
			replacePackageName(file, parentPath);
		}
	}
	private static void replacePackageName(File file,String parentPath) throws Exception{
		
		List<String> cntList = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String pkgDeclaration =reader.readLine();
		if(pkgDeclaration.startsWith("package")){
			System.out.println(pkgDeclaration);
			parentPath = parentPath.replace("\\", ".");
			int index = parentPath.indexOf("com");
			parentPath = parentPath.substring(index);
			String newPkgDeclaration = "package "+ parentPath;
			cntList.add(newPkgDeclaration);
			String line = null;
			while(( line = reader.readLine())!=null){
				cntList.add(line);
			}
			reader.close();
		}
		FileWriter writer = new FileWriter(file);
		BufferedWriter bWriter = new BufferedWriter(writer);
		for(String line : cntList){
			bWriter.write(line);
			bWriter.newLine();
		}
		bWriter.flush();
		bWriter.close();
	}
}

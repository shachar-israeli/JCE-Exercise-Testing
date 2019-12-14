import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import java.io.BufferedWriter;

/*this class is in charge to give the tester the files he needs.
 * PATH - path to the directory with the exercises. each exercise is in rar/zip
 * DEST - where to extract the files for the tester.
 * DEST_DONE_FOLDER - where to put the file when finished checking it
 * NUM_OF_FILES - how many files need to be in the rar/zip.
 * 
 *there is a problem with java and rar files, so before run this class,use my python file to convert every rar file to zip.
 * */

public class Unzip_Files {

	public static String PATH = "C:\\Users\\shach\\OneDrive\\Desktop\\שנה ד ואחרונה\\בדיקת תרגילים\\ex4\\קבוצה2";
	public static String DEST = "C:\\Users\\shach\\OneDrive\\Desktop\\שנה ד ואחרונה\\בדיקת תרגילים\\ex6\\src";
	public static String DEST_DONE_FOLDER = "C:\\Users\\shach\\OneDrive\\Desktop\\שנה ד ואחרונה\\בדיקת תרגילים\\ex4";
	public static int NUM_OF_FILES = 2;
	
	
	public static void main(String[] args) throws IOException, RarException {
		
		readFiles();
		System.out.println("DONE\n");
	}

	public static void makeBreak()
	{
		new java.util.Scanner(System.in).nextLine(); // wait- pause 
		System.out.println("are you sure you are ready for the next one?");
		new java.util.Scanner(System.in).nextLine(); // wait- pause 
	}

	public static boolean checkIfRar(String fileName) throws IOException {

		System.out.println(fileName);
		String isRaR = fileName;
		isRaR = isRaR.substring(isRaR.indexOf(".") + 1);
		if (isRaR.equals("RAR"))
			return true;
		else 
			return false;
	}

	public static void extractRAR(String fileName) throws RarException, IOException
	{
		int countFiles = 0 ;
		System.out.println(fileName);
		File f = new File(fileName);
		Archive archive = new Archive(f);
		//archive.getMainHeader().print();
		FileHeader fh = archive.nextFileHeader();
		while(fh!=null){
			System.out.println("Extracting: "+ fh.getFileNameString());
			if(fh.getFileNameString().equals("Apartment.java")||fh.getFileNameString().equals("Room.java"))
			{
				countFiles++;
				File fileEntry = new File(fh.getFileNameString().trim());
				//System.out.println(fileEntry.getAbsolutePath());
				FileOutputStream os = new FileOutputStream(DEST+"/"+fileEntry,false);
				archive.extractFile(fh, os);
				os.close();
				fh=archive.nextFileHeader();
			}
			else {
				System.out.println("this file is inside: "+fh.getFileNameString());
				System.out.println("there is more files inside.check it out.");
				fh=archive.nextFileHeader();
			}
		}
		archive.close();
		if(countFiles != NUM_OF_FILES)
			System.out.println("$$$ There is less files that you need! check it out");
	}

	public static void readFiles() throws IOException, RarException {

		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			String fileName = listOfFiles[i].getName();
			if (listOfFiles[i].isFile()) {

				write_id_to_file(getID(fileName));
				printID(fileName);

				boolean isRaR =checkIfRar(fileName);
				if (isRaR)
				{
					System.out.println("file is RAR");
					extractRAR(PATH+"\\"+fileName);
				}
				else {
					unzip(PATH+"\\"+fileName);
				}
				printID(fileName);
				makeBreak();
				moveFinishedWork(fileName);
				printID(fileName);
			}
		}
	}

	public static void unzip(String filename) {
		int countFiles = 0;
		System.out.println(filename);
		final int BUFFER = 2048;
		try {
			BufferedOutputStream dest = null;
			FileInputStream fis = new 
					//					FileInputStream("C:\\Users\\esty\\Desktop\\ex4\\q.zip");
					FileInputStream(filename);
			ZipInputStream zis = new 
					ZipInputStream(new BufferedInputStream(fis));
			ZipEntry entry;
			while((entry = zis.getNextEntry()) != null) {

				if(entry.getName().equals("Apartment.java")||entry.getName().equals("Room.java"))
				{
					countFiles++;
					System.out.println("Extracting: " +entry);
					int count;
					byte data[] = new byte[BUFFER];
					// write the files to the disk
					FileOutputStream fos = new 
							FileOutputStream(DEST+"/"+entry.getName(),false); // overwrite the file
					dest = new 
							BufferedOutputStream(fos, BUFFER);
					while ((count = zis.read(data, 0, BUFFER)) 
							!= -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
				}
				else {
					System.out.println("this file is inside: "+entry.getName());
					System.out.println("there is more files inside.check it out.");
				}
			}
			zis.close();
		} catch(Exception e) {
			System.out.println("i think there is no zip file there");
			e.printStackTrace();
		}
		if(countFiles != NUM_OF_FILES)
			System.out.println("$$$ There is less files that you need! check it out $$$");
	}

	public static void moveFinishedWork(String fileName) throws IOException
	{
		System.out.println("you finished checking:");
		System.out.println(fileName);

		File theDir = new File(DEST_DONE_FOLDER+"/finished/");
		if (!theDir.exists()) 
			theDir.mkdir();  // make the finished dir.

		Path temp = Files.move 
				(Paths.get(PATH+"/"+ fileName),  
						Paths.get(DEST_DONE_FOLDER+"/finished/"+fileName)); 
		if(temp != null) 
			System.out.println("File moved to finished folder successfully"); 
		else
			System.out.println("Failed to move the file.check it out");  
	} 

	public static void printID(String file)
	{
		String id = getID(file);
		System.out.println("ID:\n"+id);
		System.out.println("#####################################");
		System.out.println("#####################################\n");
	}

	public static String getID(String file)
	{
		String temp = file;
		temp = temp.substring(temp.indexOf("_") + 1);
		temp = temp.substring(0, temp.indexOf("_"));
		return temp;
	}

	public static void write_id_to_file(String id) {

		BufferedWriter writer = null;
		try {
			//create a temporary file;	
			File logFile = new File(DEST_DONE_FOLDER+"\\lastID.txt");

			// This will output the full path where the file will be written to...
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(id);
			System.out.println("finish write the id to lastID file");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	
	//not in use
	public static void append_to_java_file() throws IOException {
		System.err.println("lol");
		String textToAppend = "public int[] getNumbers() {\r\n" + 
				"		return numbers;\r\n" + 
				"	} ";

		BufferedWriter writer = new BufferedWriter(
				new FileWriter(DEST + "/Array.java", true)  //Set true for append mode
				);  
		writer.newLine();   //Add new line
		writer.write(textToAppend);
		writer.close();

	}	

}

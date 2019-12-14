//import java.io.*;
import java.util.Scanner;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/*This is the test part.
 * 1.when after you finish running the tester, you have option to add more comments and to change the grade.
 * 2.after you add your comments,its write it to XML file that alreday have a row with every id of every student.
 * */

public class Tester {
	public static String PATH_TO_XML= "קבוצה1 סיכום.xlsx";
	public static String DEST_LAST_ID = "C:\\Users\\shach\\OneDrive\\Desktop\\שנה ד ואחרונה\\בדיקת תרגילים\\ex4\\lastID.txt";
	public static String warnings= "";
	public static int grade = 100;
	public static String comments= "";

	public static void checkCons()
	{
		try {
			Field field = Room.class.getDeclaredField("type");
			int modifier = field.getModifiers();
			String str =(Modifier.toString(modifier));
			if(!str.equalsIgnoreCase("private")){
				comments+= ("Room Test: ");
				comments+= " -3 private תכונות לא ב ";
				comments+= "\n";
				grade-=3;
			}
		} catch (Exception e) {
			comments+= ("Room Test: ");
			comments+= " -3  תכונות לא בשם הנתון בתרגיל";
			comments+= "\n";
			grade-=3;
		}
		Room f1 = new Room("shachar",3.0);
		if(f1.getType() == "shachar" && f1.getArea() == 3.0)
		{

			f1.setType("wow");
			if(f1.getType() != "wow")
			{
				comments+= ("Room Test: ");
				comments += "-3 setType לא נכון  !";
				comments +="\n";
				grade -=3;
			}
			//			String stringCheck = f1.toString();
			//			String s1 ="Room type: "+ f1.getType() +", Area: "+ f1.getArea();
			//			if (!stringCheck.equals(s1))
			//			{
			//				System.out.println("his:  "+stringCheck);
			//				System.out.println("need: "+s1);
			//				comments+= ("Room Test: ");
			//				comments += " הדפסה לא לפי הפורמט הדרוש בתרגיל -3 !";
			//				comments +="\n";
			//				grade -=3;
			//			}

			Room c1 = new Room("lol",-3.0);	
			String name = c1.getType();
			Double size = c1.getArea();
			if(name != "lol" || size != 0)
			{
				comments+= ("Room Test: ");
				grade -= 5;
				comments += " בנאי לא  לפי דרישות התרגיל כאשר מעבירים ערך שלילי -5";
				comments +="\n";

			}
		}
		else {
			comments+= ("Room Test: ");
			grade -= 7;
			comments += " בנאי לא לפי דרישות התרגיל -7";
			comments +="\n";
		}
	}

	public static void checkCopyCons()
	{
		Room c1 = new Room("lol",3.0);
		Room c2 =new Room(c1);
		c1.setType("banana");
		if (c2.getType() == c1.getType() || c2.getArea() != c1.getArea())
		{
			comments+= ("Room Test: ");
			grade -= 5;
			comments += " בנאי העתקה לא לפי דרישות התרגיל -5";
			comments +="\n";
		}
	}


	public static void checkApartCons()
	{
		try {
			Field field = Apartment.class.getDeclaredField("family");
			int modifier = field.getModifiers();
			String str =(Modifier.toString(modifier));
			if(!str.equalsIgnoreCase("private")){
				comments+= ("Apartment Test: ");
				comments+= " -3 private תכונות לא ב ";
				comments+= "\n";
				grade-=3;
			}
		} catch (Exception e) {
			comments+= ("Apartment Test: ");
			comments+= " -3  תכונות לא בשם הנתון בתרגיל";
			comments+= "\n";
			grade-=3;
		}

		Apartment c1 = new Apartment("lol",3);		
		if(c1.getFlat() != 3 || c1.getNumOfRooms() != 0)
		{
			grade -= 8;
			comments+= ("Apartment Test: ");
			comments += "בנאי/get ";
			comments += " לא לפי דרישות התרגיל -8";
			comments +="\n";
		}
		else {
			Apartment n1 = new Apartment("lol",-3);		
			if(n1.getFlat() != 0 || n1.getNumOfRooms() != 0)
			{
				System.out.println(n1.getFlat());
				grade -= 5;
				comments+= ("Apartment Test: ");
				comments += "בנאי/get ";
				comments += " לא לפי דרישות התרגיל במקרה של מספר שלילי -5";
				comments +="\n";
			}
		}
	}

	public static void checkSetRoom()
	{
		Apartment c1 = new Apartment("israeli",2);
		Room r1 = new Room("kitchen",10.5);
		Room r2 = new Room("living",2.3);
		Room r3 = new Room("dodo",9.5);

		c1.setRoom(r1);
		if(c1.getNumOfRooms() != 1)
		{
			comments+= ("Apartment Test: ");
			comments+=("-5 getNumOfRooms לא עודכן אחרי הוספת חדר");
			comments+=("\n");
			grade -=5;

		}
		c1.setRoom(r2);
		c1.setRoom(r3);

		if(c1.getTotalArea() != 22.3)
		{
			System.out.println(c1.getTotalArea());
			comments+= ("Apartment Test: ");
			comments+=("-5 getTotalArea לא מחזיר תשובה נכונה");
			comments+=("\n");
			grade -=5;
		}
		Room r4 = new Room("nono",20.0);
		c1.setRoom(r4);
		if (c1.getRoomByType("nono") !=null)
		{
			comments+= ("Apartment Test: ");
			comments+=("לא עצרת אחרי הוספת 3 חדרים -5");
			comments+=("\n");
			grade -=5;
		}
		r1.setType("didi");
		if (c1.getRoomByType("didi") !=null)
		{
			comments+= ("Apartment Test: ");
			comments+=("aliasing -7");
			comments+=("\n");
			grade -=7;
		}
	}
	public static void checkToString()
	{
		Apartment c1 = new Apartment("israeli",2);
		boolean flag = false;
		String check = c1.toString();

		String shouldBe = "israeli's apartment, flat 2 has 0 rooms\n";
		String shouldBe2 = "israeli's apartment, flat 2 has 0 rooms";
		String shouldBe3 = "israeli's apartment, flat 2 has 0 rooms ";
		if (!check.equals(shouldBe) && !check.equals(shouldBe2)&& !check.equals(shouldBe3))
		{
			warnings+= "check his tostring!!!!!!!!!!!";
			flag = true;
			System.out.println("his:  "+check);
			System.out.println("need: "+shouldBe2);
			System.out.println("###");
			//			comments+= ("Apartment Test: ");
			//			comments += " הדפסה לא מדויקת לפי הפורמט הדרוש בתרגיל -3 !";
			//			comments +="\n";
			//			grade -=3;
		}

		Room r1 = new Room("kitchen",10.5);
		Room r2 = new Room("living",2.3);
		c1.setRoom(r1);
		c1.setRoom(r2);
		check = c1.toString();

		shouldBe = "israeli's apartment, flat 2 has 2 rooms\n";
		shouldBe +=	 "Room type: kitchen, Area: 10.5\n" ; 
		shouldBe+=   "Room type: living, Area: 2.3";

		shouldBe2 = "israeli's apartment, flat 2 has 2 rooms\n";
		shouldBe2 +=	 "Room type: kitchen, Area: 10.5\n" ; 
		shouldBe2+=   "Room type: living, Area: 2.3\n";

		if (!check.equals(shouldBe) && !check.equals(shouldBe2))
		{
			warnings+= "check his tostring!!!!!!!!!!!";
			System.out.println("###");
			System.out.println("his:  "+check);
			System.out.println("-----");
			System.out.println("need: "+shouldBe);
			System.out.println("-----");
			if(!flag)
			{
				//					comments+= ("Apartment+Room Test: ");
				//					comments += " הדפסה לא נכונה כאשר מוסיפים חדרים -5 !";
				//					comments +="\n";
				//					grade -=5;
			}

		}

	}


	public static void main(String[] args) throws IOException, EncryptedDocumentException, InvalidFormatException {

		String id = get_id_from_file();
		if (id == null)
		{
			System.out.println("problem with id from file");
			System.exit(1);
		}

		System.out.println("\n");
		//##################### start Test here #####################//

		checkCons();
		checkCopyCons();
		//
		checkApartCons();
		checkSetRoom();
		checkToString();


		//##################### end of Test  #####################//

		System.out.println("\n\n"+warnings+"#####\n\n");
		System.out.println("\n");
		System.out.println(comments);
		if (comments.equals(""))
			System.out.println("no comments untill now. add something?");

		//comments+="\n" + addExtraComments();

		if(grade == 100)
		{
			comments+= "!עבודה מעולה";
		}
		System.out.println("\nafter add comments:");
		System.out.println(comments);

		System.out.println("grade = "+grade);


	//	writeToExel(id,comments,String.valueOf(grade));
	}



	// ###################################################################//
	// ####################### ADD TO EVERY TESTER ###################


	public static void writeToExel(String id,String comment,String grade) throws IOException, EncryptedDocumentException, InvalidFormatException {

		System.out.println("writing data to:"+ id);
		String excelFilePath = PATH_TO_XML;
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = findRow(sheet, id);

		Row row = sheet.getRow(rowNum);
		if(rowNum == -1)
		{
			System.out.println("BREAK: no id found!!!!");
			System.out.println("didnt write it to xml!!! check it out");
			return;
		}
		Cell cellComment = row.getCell(4);
		Cell cellGrade = row.getCell(3);

		if (cellComment == null)
			cellComment = row.createCell(4);

		if (cellGrade == null)
			cellGrade = row.createCell(3);

		// Update the cell's value
		cellComment.setCellType(CellType.STRING);
		cellComment.setCellValue(comment);

		cellGrade.setCellType(CellType.STRING);
		cellGrade.setCellValue(grade);
		inputStream.close();
		FileOutputStream fileOut = new FileOutputStream(PATH_TO_XML);
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();

		System.out.println("finished write the comments to the XML file");
	}


	public static int findRow(Sheet sheet, String cellContent) {

		DataFormatter dataFormatter = new DataFormatter();
		int counter = 0;
		for (Row row: sheet) { 
			String cellValue = dataFormatter.formatCellValue(row.getCell(0));
			if (cellValue.equals(cellContent))
				return counter;
			counter++;
		}
		return -1;
	}

	public static String get_id_from_file() throws IOException {
		String id = "";
		File file = new File(DEST_LAST_ID);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String temp = "";
		while ((temp = br.readLine()) != null)
			id += temp;
		System.out.println("ID from file is: "+ id);
		return id;
	}

	public static String addExtraComments() throws IOException {
		System.out.println("you hava Comments to add?");
		String input = "";
		Scanner keyboard = new Scanner(System.in);
		String line;
		while (keyboard.hasNextLine()) {
			line = keyboard.nextLine();
			if (line.isEmpty()) {
				break;
			}
			System.out.println("point to change");
			int n = new Scanner(System.in).nextInt(); 
			grade-= n;
			input += line + "\n";
			System.out.println("you hava Comments to add?");
		}
		return input;
	}
}

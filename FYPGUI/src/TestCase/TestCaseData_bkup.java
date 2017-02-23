package TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCaseData_bkup {

	ArrayList<TestCaseFragment> testCaseData = new ArrayList<>();
	
	public TestCaseData_bkup() {
//		Pattern pattern1 = Pattern.compile("\\(By.(.*?)\\(\"(.*?)\"\\)\\).(.*?)\\(\"(.*?)\"\\)");
	}
	
	public ArrayList<TestCaseFragment> findFragments(File testCaseFile) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(testCaseFile));
			String line = null;
			int lineNo = 0;
			while((line = br.readLine()) != null) {
				
				TestCaseFragment testCaseFragment = new TestCaseFragment();
				testCaseFragment.setLineNo(++lineNo);
				
				Pattern pattern = Pattern.compile("\\(By.(.*?)\\(\"(.*?)\"\\)\\).(.*?)\\(");
				Matcher matcher = pattern.matcher(line);
				if (matcher.find())
				{
				    if(!matcher.group(3).contains("click")) {
				    	Pattern pattern1 = Pattern.compile("\\(By.(.*?)\\(\"(.*?)\"\\)\\).(.*?)\\(\"(.*?)\"\\)");
				    	Matcher matcher1 = pattern1.matcher(line);
						if (matcher1.find())
						{
						    System.out.println(lineNo + " " + matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3) + " " + matcher1.group(4));
						}
				    }
				    else {
					    System.out.println(lineNo + " " + matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
				    }

				    
				    
				}
				
//				String findByName;
				
				
				
			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return testCaseData;
		
	}
	
}

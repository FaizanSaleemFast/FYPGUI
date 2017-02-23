package TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadTestCases {

//	ArrayList<TestCaseFragments> testCaseData = new ArrayList<>();
	ArrayList<TestCaseGroup> testCaseGroup;
	
	public ReadTestCases() {
//		Pattern pattern1 = Pattern.compile("\\(By.(.*?)\\(\"(.*?)\"\\)\\).(.*?)\\(\"(.*?)\"\\)");
		testCaseGroup = new ArrayList<TestCaseGroup>();
	}
	
	
	public ReadTestCases(ArrayList<TestCaseGroup> tcg) {
		if(tcg == null)
			tcg = new ArrayList<TestCaseGroup>();
		
		testCaseGroup = tcg;
	}
	
	public ArrayList<TestCaseGroup> findFragments(File testCaseFile) {
		
		TestCaseGroup tg = new TestCaseGroup();
		testCaseGroup.add(tg);
		
		TestCase tc = new TestCase();
		tg.getTestCases().add(tc);
		

		
		
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(testCaseFile));
			String line = null;
			int lineNo = 0;
			while((line = br.readLine()) != null) {

				TestCaseFragment testCaseFragment = new TestCaseFragment();
				
				
				Pattern pattern, pattern1, pattern2;
				Matcher matcher, matcher1, matcher2;
				
				testCaseFragment.setLineNo(++lineNo);
				
				System.out.print(lineNo + " ");
				
				pattern = Pattern.compile("\\bBy.(.*?)\\(\"(.*?)\"\\)");
				matcher = pattern.matcher(line);
				
				if (matcher.find()) {
				    System.out.print(matcher.group(1) + "=" + matcher.group(2) + " | ");
				    SimpleEntry<String, String> findBy = new SimpleEntry<String, String>(matcher.group(1), matcher.group(2));
				    
//				    testCaseFragment.setFindBy(findBy.getKey(), findBy.getValue());
				    
				    testCaseFragment.setFindBy(findBy);
				}
				
				pattern1 = Pattern.compile("\\bBy.(.*?)\\(\"(.*?)\"\\)\\).(.*?)\\(");
				matcher1 = pattern1.matcher(line);
				
				if (matcher1.find()) {
				    System.out.print(matcher1.group(3) + "=");
				
				    if(!matcher1.group(3).equals("click")) {
						pattern2 = Pattern.compile("\\bBy.(.*?)\\(\"(.*?)\"\\)\\).(.*?)\\(\"(.*?)\"\\)");
						matcher2 = pattern2.matcher(line);
						if (matcher2.find()) {
							System.out.print(matcher2.group(4));
							SimpleEntry<String, String> operation = new SimpleEntry<String, String>(matcher2.group(3), matcher2.group(4));
							testCaseFragment.setOperation(operation);
						}
				    }
				    else {
						SimpleEntry<String, String> operation = new SimpleEntry<String, String>(matcher1.group(3), null);
						testCaseFragment.setOperation(operation);
				    }
				}			
				
				System.out.println();
				
//				testCaseFragment.display();
				tc.getTestCaseFragments().add(testCaseFragment);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return testCaseGroup;
	}

	public ArrayList<TestCaseGroup> getTestCaseGroup() {
		return testCaseGroup;
	}

	public void setTestCaseGroup(ArrayList<TestCaseGroup> testCaseGroup) {
		this.testCaseGroup = testCaseGroup;
	}
	
}

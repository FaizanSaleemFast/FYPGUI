package TestCase;

import java.io.File;
import java.util.ArrayList;

import Categorize_TestCases.CategorizeTestCase;
import DOM_Difference.PageParser;

public class TestCaseMain {
	
	public static void main(String[] args) {
		
		
		System.out.println("Just To Test Git");
		System.out.println("Just To Test Git 2");
		System.out.println("Just To Test Git Waleed");

		
		File file = new File("testcase1.txt");
		PageParser pp =  new PageParser("before.html", "after.html");
		pp.setup();		

		
		ArrayList<TestCaseGroup> testCaseGroup = new ArrayList<TestCaseGroup>();
		
		ReadTestCases readTestCase = new ReadTestCases(testCaseGroup);
		testCaseGroup=readTestCase.findFragments(file);
		
		int i=0;
		for(TestCaseGroup testcaseGroup:testCaseGroup)
		{
			for(TestCase testcase:testcaseGroup.getTestCases())
			{	
				System.out.println("Size: "+testcase.getTestCaseFragments().size());
				CategorizeTestCase ctc = new CategorizeTestCase(testcase.getTestCaseFragments(), pp.getDifference());		
				System.out.println("\n=== Categorized TESTCASE DATA ===");
				ctc.display();
				i++;
			}
		}
		
	}
}

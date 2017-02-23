package Categorize_TestCases;

import java.util.ArrayList;

import DOM_Difference.Difference;
import TestCase.TestCaseFragment;
import junit.framework.TestCase;

public class Category {
	
	private int testcaseLineNo;
	private TestCaseFragment testcase;
	private ArrayList <TestCaseFragment> suggestedRepairs=new ArrayList<TestCaseFragment>();
	
	/**
	 * @return the suggestedRepairs
	 */
	public ArrayList<TestCaseFragment> getSuggestedRepairs() {
		return suggestedRepairs;
	}

	/**
	 * @param suggestedRepairs the suggestedRepairs to set
	 */
	public void setSuggestedRepairs(ArrayList<TestCaseFragment> suggestedRepairs) {
		this.suggestedRepairs = suggestedRepairs;
	}

	private Difference htmlDifference;
	
	//testcaseCategory will be 
	// 0 if obselete
	//1 if repairable
	//2 if user support required
	//3 if there is no change in html
	private int testcaseCategory;

	
	/**
	 * @param testcaseLineNo
	 * @param testcase
	 * @param htmlDifferences
	 * @param testcaseCategory
	 */
	
	
	public Category(int testcaseLineNo, TestCaseFragment testcase, Difference htmlDifference,
			int testcaseCategory) {
		super();
		this.testcaseLineNo = testcaseLineNo;
		this.testcase = testcase;
		this.htmlDifference = htmlDifference;
		this.testcaseCategory = testcaseCategory;
	}

	/**
	 * 
	 */
	public Category() {
		super();
	}

	/**
	 * @return the testcaseLineNo
	 */
	public int getTestcaseLineNo() {
		return testcaseLineNo;
	}

	/**
	 * @param testcaseLineNo the testcaseLineNo to set
	 */
	public void setTestcaseLineNo(int testcaseLineNo) {
		this.testcaseLineNo = testcaseLineNo;
	}

	/**
	 * @return the testcase
	 */
	public TestCaseFragment getTestcase() {
		return testcase;
	}

	/**
	 * @param testcase the testcase to set
	 */
	public void setTestcase(TestCaseFragment testcase) {
		this.testcase = testcase;
	}

	/**
	 * @return the htmlDifferences
	 */
	public Difference getHtmlDifferences() {
		return htmlDifference;
	}

	/**
	 * @param htmlDifferences the htmlDifferences to set
	 */
	public void setHtmlDifferences(Difference htmlDifferences) {
		this.htmlDifference = htmlDifference;
	}

	/**
	 * @return the testcaseCategory
	 */
	public int getTestcaseCategory() {
		return testcaseCategory;
	}

	/**
	 * @param testcaseCategory the testcaseCategory to set
	 */
	public void setTestcaseCategory(int testcaseCategory) {
		this.testcaseCategory = testcaseCategory;
	}
	
	
}

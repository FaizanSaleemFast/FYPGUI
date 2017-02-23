package Categorize_TestCases;

import TestCase.*;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import DOM_Difference.Difference;
import DOM_Difference.PageParser;

public class CategorizeTestCase {

	//Attributes
	ArrayList<TestCaseFragment> testCaseFragments = new ArrayList<TestCaseFragment>();
	ArrayList<Difference> htmlDifferences = new ArrayList<Difference>();
	ArrayList<Category> categorizedFaultyTestCases=new ArrayList<Category>();	

	//Constructors
	public CategorizeTestCase() {
		
	}

	public CategorizeTestCase(ArrayList<TestCaseFragment> tcfs, ArrayList<Difference> htmlDiffs) {		
		for (Difference difference : htmlDiffs) {			
			this.htmlDifferences.add(difference);			
		}
		for (TestCaseFragment tcf : tcfs) {
			testCaseFragments.add(tcf);
		}

		categorizeTestCase();
	}

	public void display(){
		for(Category category:categorizedFaultyTestCases)
		{
			System.out.println("");
			System.out.println("Category:"+category.getTestcaseCategory());
			System.out.println("Actual Test Case==>");
			category.getTestcase().display();
			if(category.getSuggestedRepairs().size()>0)
			{
				int i=0;
				for(TestCaseFragment sug_repair:category.getSuggestedRepairs())
				{
					System.out.println("\t\tSuggested Repair no:"+(++i));
					sug_repair.display();
				}
			}
			else
			{
				System.out.println("Test Case line remain unchanged==>");
				category.getTestcase().display();
			}
			System.out.println("");
			
		}
	}
	//Categorization Func for full testcase
	private void categorizeTestCase(){
		//iterate through each line of the test case
		for (int i = 0; i < testCaseFragments.size(); i++) {
			TestCaseFragment lineData = testCaseFragments.get(i);
			//check if the data corresponds to anything in DELTA
			// iterate through DELTA`
			categorizedFaultyTestCases.add(getTestcaseCategory(lineData.getFindBy().getKey(), lineData.getFindBy().getValue(),lineData));			
		}
		
	}

	//=============================== Categorization function for single testcaseline =============================== 
	Category getTestcaseCategory(String attrName, String attrVal,TestCaseFragment testcaseline){
		
//		attrName=attrName.toLowerCase();
//		attrVal=attrVal.toLowerCase();
		Category category=new Category();
		//Iterate trough all elements which have any difference
		for (Difference difference : htmlDifferences) {
			//IF V1 element has this attr with attrVal THEN we have found the element
			if(difference.getTagV2() == null||difference.getTagV1() == null)
			{
				if(difference.getTagV2() == null){
					continue;
				}
				if(difference.getTagV1() == null){
					//this shows that test case for version 1 is not exectuable at version 1 of html , user error
					continue;
				}
			}
			else{
				//if test case atttr matched
				if(difference.getTagV1().hasAttr(attrName)&&difference.getTagV1().attr(attrName).equals(attrVal)
						&&difference.getTagV2()!=null&&difference.getTagV2().hasAttr(attrName)&&difference.getTagV2().attr(attrName).equals(attrVal)
						)
				{
					category.setHtmlDifferences(difference);
					category.setTestcase(testcaseline);
					category.setTestcaseCategory(3);//it mean no need to repair
					return category;

				}
				//if attribute matches in first version but not present in 2nd version
				else if(difference.getTagV1().hasAttr(attrName)&&difference.getTagV1().attr(attrName).equals(attrVal))

				{
					category =setCategory(difference, testcaseline);
					return category;
				}				
			}

		}
		//TestCase portion is not in difference so no need to repair
		category.setTestcase(testcaseline);
		category.setTestcaseCategory(3);//it mean no need to repair

		return category;
	}// end getElementsByAttribute

	private Category setCategory(Difference difference, TestCaseFragment testcaseline) {
		// TODO Auto-generated method stub
		Category issue=new Category();
		issue.setHtmlDifferences(difference);
		issue.setTestcase(testcaseline);
		//if element deleted
		if(difference.getTagV2()==null)
		{
			issue.setTestcaseCategory(0);
		}
		
		//if repairable
		if(isTestcaseRepairable(difference,testcaseline))
		{
			issue.setTestcaseCategory(1);
			issue.setSuggestedRepairs(getSuggestedRepairs(difference, testcaseline));
		}		
		else
		{
			//user support required
			issue.setTestcaseCategory(2);
		}
		
		return issue;

	}
	private ArrayList<TestCaseFragment> getSuggestedRepairs(Difference difference, TestCaseFragment testcaseline) {
		// TODO Auto-generated method stub
		
		ArrayList<TestCaseFragment> suggestedRepairs=new ArrayList<TestCaseFragment>();
		//if differnce contain null 
		if(difference.getTagV1()==null||difference.getTagV2()==null)
		{
			return suggestedRepairs;
		}
		else
		{
			Element elementv1=difference.getTagV1();
			Element elementv2=difference.getTagV2();
			PageParser page=new PageParser();
			for(Attribute attrv2:elementv2.attributes())
			{
				//if other attributes present in html are enough to write test case
				if(page.isImp(attrv2)==true)
				{
					//Repair a test case according to the option
					TestCaseFragment testcaserepair=new TestCaseFragment(testcaseline);
					testcaserepair.setFindBy(attrv2.getKey(), attrv2.getValue());
					//Add the repaired test case in to the list of suggestedrepairs
					suggestedRepairs.add(testcaserepair);
					
				}				
			}
			
		}
		return suggestedRepairs;
		
	}


	private Boolean isTestcaseRepairable(Difference difference, TestCaseFragment testcaseline) {
		// TODO Auto-generated method stub
		
		//if differnce contain null 
		if(difference.getTagV1()==null||difference.getTagV2()==null)
		{
			return false;
		}
		else
		{
			Element elementv1=difference.getTagV1();
			Element elementv2=difference.getTagV2();
			PageParser page=new PageParser();
			for(Attribute attrv2:elementv2.attributes())
			{
				//if other attributes present in html are enough to write test case
				if(page.isImp(attrv2)==true)
				{
					return true;
				}				
			}
			
		}
		return false;
		
	}

	private void createElement(TestCaseFragment lineData) {
		//		// TODO Auto-generated method stub
		//		Element el = new Element(Tag.valueOf("ol"), "");
		//		
		//		Attributes attr = new Attributes();
		//		
		//		if( lineData.getFindBy().getKey().equals("id")){
		//			attr.a = new Attribute("id", lineData.getFindBy().getValue());
		//		}
		//		else if( lineData.getFindBy().getKey().equals("name")){
		//			attr = new Attribute("name", lineData.getFindBy().getValue());
		//		} 
		//		else{
		//			attr = new Attribute("UNKNOWN", "UNKNOWN");
		//		}
		//		
		//		el.a
	}

	/**
	 * @return the categorizedFaultyTestCases
	 */
	public ArrayList<Category> getCategorizedFaultyTestCases() {
		return categorizedFaultyTestCases;
	}

	/**
	 * @param categorizedFaultyTestCases the categorizedFaultyTestCases to set
	 */
	public void setCategorizedFaultyTestCases(ArrayList<Category> categorizedFaultyTestCases) {
		this.categorizedFaultyTestCases = categorizedFaultyTestCases;
	}


	public ArrayList<TestCaseFragment> getTestCaseFragment() {
		return testCaseFragments;
	}

	public void setTestCaseFragment(ArrayList<TestCaseFragment> testCaseFragments) {
		this.testCaseFragments = testCaseFragments;
	}

	public ArrayList<Difference> getHtmlDifferences() {
		return htmlDifferences;
	}

	public void setHtmlDifferences(ArrayList<Difference> htmlDifferences) {
		this.htmlDifferences = htmlDifferences;
	}

	//	public ArrayList<TestCaseFragment> getRepaired_testCaseData() {
	//		return repaired_testCaseData;
	//	}
	//
	//	public void setRepaired_testCaseData(ArrayList<TestCaseFragment> repaired_testCaseData) {
	//		this.repaired_testCaseData = repaired_testCaseData;
	//	}



}

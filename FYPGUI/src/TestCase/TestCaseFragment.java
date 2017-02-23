package TestCase;

import java.util.AbstractMap.SimpleEntry;

public class TestCaseFragment {

	/**
	 * @param lineNo
	 * @param findBy
	 * @param operation
	 */
	public TestCaseFragment(TestCaseFragment testcase) {
		super();
		this.lineNo = testcase.lineNo;
		this.findBy = testcase.findBy;
		this.operation = testcase.operation;
	}
	int lineNo;
	SimpleEntry<String, String> findBy;
	SimpleEntry<String, String> operation;


	public SimpleEntry<String, String> getOperation() {
		return operation;
	}

	public void setOperation(SimpleEntry<String, String> operation) {
		this.operation = operation;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public SimpleEntry<String, String> getFindBy() {
		return findBy;
	}

	public void setFindBy(SimpleEntry<String, String> findBy) {
		this.findBy = findBy;
	}

	public TestCaseFragment() {
		findBy = new SimpleEntry<String, String>("UNSET", "UNSET");
		operation = new SimpleEntry<String, String>("UNSET", "UNSET");
	}

	//====================== SHERRY ======================
	public void display(){
		System.out.println("lineNo: " + lineNo +  ". FIND BY(" + findBy.getKey() + "=" + findBy.getValue() + ")" + "| DO(" + operation.getKey() + "=" + operation.getValue() + ")");
	}
	
	public void setOperation(String operationName, String operationValue) {
		this.operation = new SimpleEntry<String, String>(operationName, operationValue);
	}
	
	public void setFindBy(String attrName, String attrVal) {
		this.findBy = new SimpleEntry<String, String>(attrName, attrVal);
	}
	public void setFindByValue(String attrVal){
		findBy.setValue(attrVal);
	}
}

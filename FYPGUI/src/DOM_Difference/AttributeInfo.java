package DOM_Difference;

import org.jsoup.nodes.Attribute;

public class AttributeInfo {

	Attribute attr;
	String differenceType;
	int differenceCode;


	public String getdifferenceType(int i)
	{

		String diffType="";

		if(i == 12)
		{
			//System.out.println("***");
			diffType="1.2.2 Index Changed";
			return diffType;
		}
		if(i == 11)
		{
			//System.out.println("***");
			diffType="1.2.1 Hierarchy Changed";
			return diffType;
		}

		diffType="1.1.1";
		//***********************Updated*****************
		if(i == 1/* update*/)
		{
			if(this.getKey().equals("id"))
			{
				diffType=(new StringBuilder(diffType)).append(".1: ID not Found(Updated)").toString();

			}
			else if(this.getKey().equals("href"))
			{
				diffType=(new StringBuilder(diffType)).append(".3: Href Attribut not found(Updated)").toString();

			}
			else if(this.getKey().equals("alt"))
			{
				diffType=(new StringBuilder(diffType)).append(".4: Alternative text attribute not found(Updated)").toString();

			}
			else if(this.getKey().equals("name"))
			{
				diffType=(new StringBuilder(diffType)).append(".5: name attribute not found (Updated)").toString();

			}
			else if(this.getKey().equals("type"))
			{
				diffType=(new StringBuilder(diffType)).append(".6: type attribute not found (Updated)").toString();

			}
			else if(this.getKey().equals("class"))
			{
				diffType=(new StringBuilder(diffType)).append(".7: class attribute not found (Updated)").toString();

			}
			else if(this.getKey().equals("onclick"))
			{
				diffType=(new StringBuilder(diffType)).append(".8: onclick attribute not found (Updated)").toString();

			}
			else if(this.getKey().equals("maxlength"))
			{
				diffType="2.1: Invalid text field values input (Updated)";

			}
			else if(this.getKey().equals("value"))
			{
				diffType=(new StringBuilder(diffType)).append(".10: value attribute not found (Updated)").toString();

			}
			else if(this.getKey().equals("required"))
			{
				this.setDifferenceCode(14);

			}
		}
		//***********************Added*******************************
		if(i == 2 /* Added*/)
		{
			if(this.getKey().equals("id"))
			{
				diffType=(new StringBuilder(diffType)).append(".1: ID not Found (Added)").toString();

			}
			else if(this.getKey().equals("href"))
			{
				diffType=(new StringBuilder(diffType)).append(".3: Href Attribut not found (Added)").toString();

			}
			else if(this.getKey().equals("alt"))
			{
				diffType=(new StringBuilder(diffType)).append(".4: Alternative text attribute not found (Added)").toString();

			}
			else if(this.getKey().equals("name"))
			{
				diffType=(new StringBuilder(diffType)).append(".5: name attribute not found (Added)").toString();

			}
			else if(this.getKey().equals("type"))
			{
				diffType=(new StringBuilder(diffType)).append(".6: type attribute not found (Added)").toString();

			}
			else if(this.getKey().equals("class"))
			{
				diffType=(new StringBuilder(diffType)).append(".7: class attribute not found (Added)").toString();

			}
			else if(this.getKey().equals("onclick"))
			{
				diffType=(new StringBuilder(diffType)).append(".8: onclick attribute not found (Added)").toString();

			}
			else if(this.getKey().equals("maxlength"))
			{
				diffType="2.1: Invalid text field values input (Added)";

			}
			else if(this.getKey().equals("value"))
			{
				diffType=(new StringBuilder(diffType)).append(".10: value attribute not found (Added)").toString();

			}
			else if(this.getKey().equals("required"))
			{
				this.setDifferenceCode(14);

			}


		}

		//**********************Deleted*****************
		if(i == 3 /* Deleted*/)
		{
			if(this.getKey().equals("id"))
			{
				diffType=(new StringBuilder(diffType)).append(".1: ID not Found (Deleted)").toString();

			}
			else if(this.getKey().equals("href"))
			{
				diffType=(new StringBuilder(diffType)).append(".3: Href Attribut not found (Deleted)").toString();

			}
			else if(this.getKey().equals("alt"))
			{
				diffType=(new StringBuilder(diffType)).append(".4: Alternative text attribute not found (Deleted)").toString();

			}
			else if(this.getKey().equals("name"))
			{
				diffType=(new StringBuilder(diffType)).append(".5: name attribute not found (Deleted)").toString();

			}
			else if(this.getKey().equals("type"))
			{
				diffType=(new StringBuilder(diffType)).append(".6: type attribute not found (Deleted)").toString();

			}
			else if(this.getKey().equals("class"))
			{
				diffType=(new StringBuilder(diffType)).append(".7: class attribute not found (Deleted)").toString();

			}
			else if(this.getKey().equals("onclick"))
			{
				diffType=(new StringBuilder(diffType)).append(".8: onclick attribute not found (Deleted)").toString();

			}
			else if(this.getKey().equals("maxlength"))
			{
				diffType="2.1: Invalid text field values input (Deleted)";

			}
			else if(this.getKey().equals("value"))
			{
				diffType=(new StringBuilder(diffType)).append(".10: value attribute not found (Deleted)").toString();

			}
			else if(this.getKey().equals("required"))
			{
				setDifferenceCode(14);;

			}
		}

		if(i ==21)
		{				
			return "5.1: onclick alert Added";
		}

		if(i ==22)	
		{
			return "5.2: onclick alert deleted";
		}

		if(i ==14)
		{
			diffType="2.2 Missing Value";
			return diffType;
		}

		if(i ==23)
		{
			diffType="6.1 Element Deleted from Html";
			return diffType;
		}
		if(i ==24)
		{
			diffType="6.2 Element Added in Html";
			return diffType;
		}


		if(i ==10)
		{
			diffType="1.1.2 Element Text not found Deleted";
			return diffType;
		}
		if(i ==15)
		{
			diffType="2.3 value deleted from drop down";
			return diffType;
		}


		return diffType;
	}

	public Attribute getAttr() {
		return attr;
	}

	public AttributeInfo(Attribute attr, String diffType, int diffCode) {
		this.attr = attr;
		differenceCode = diffCode;
		differenceType = getdifferenceType(differenceCode);
	}

	public void setAttr(Attribute attr, String diffType, int diffCode) {
		this.attr = attr;
		differenceCode = diffCode;
		differenceType = getdifferenceType(differenceCode);
	}

	public String getDifferenceType() {
		return differenceType;
	}

	public void setDifferenceType(String differenceType) {
		this.differenceType = differenceType;
	}

	public int getDifferenceCode() {
		return differenceCode;
	}

	public void setDifferenceCode(int differenceCode) {
		this.differenceCode = differenceCode;
	}

	public AttributeInfo(Attribute arg_attr){
		attr = arg_attr;		
	}



	//getters
	public String getKey(){
		return attr.getKey();
	}
	public String getValue(){
		return attr.getValue();
	}

	//setters
	public void  setKey(String key){
		attr.setKey(key);
	}
	public void  setValue(String val){
		attr.setKey(val);
	}
}

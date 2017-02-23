package DOM_Difference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;



public class PageParser {

	// ATTRIBUTES
	Document web1_doc;
	Document web2_doc;

	String web01, web02;

	ArrayList<Difference> pagesDifferences;
	


	//=============================== FUNCTIONS =================================
	//returns categories of difference types found in both pages
	public ArrayList<String> getAllDifferenceTypesCategories(){

		ArrayList<String> categories = new ArrayList<>();

		for (int i = 0; i < pagesDifferences.size(); i++) {

			categories.addAll(pagesDifferences.get(i).getAllCategoryTypes());
		}
		
		Set<String> hs = new HashSet<>();
		hs.addAll(categories);
		
		categories.clear();
		categories.addAll(hs);

		Collections.sort(categories, String.CASE_INSENSITIVE_ORDER);
		return categories;
	}

	public ArrayList<Difference> getElementByDifferenceType(String diffType){

		ArrayList<Difference> differentByType = new ArrayList<>();

		for (int i = 0; i < pagesDifferences.size(); i++) {
			Difference differentElements = pagesDifferences.get(i);

			for (int j = 0; j < differentElements.getAttributesInfo().size(); j++) {
				String currElementDiffType = differentElements.getAttributesInfo().get(j).getDifferenceType();

				if(currElementDiffType.toLowerCase().equals(diffType.toLowerCase())){
					differentByType.add(differentElements);
				}
			}
		}

		//System.out.println(diffType + " getElementByDifferenceType: difference: " + differentByType.size());
		return differentByType;
	}// 

	public ArrayList<Difference> getPagesDifferences() {
		return pagesDifferences;
	}

	public void setPagesDifferences(ArrayList<Difference> pagesDifferences) {
		this.pagesDifferences = pagesDifferences;
	}


	public PageParser()
	{

	}
	
	public PageParser(String web1, String web2)
	{
		web01 = "versions/signupv1.html";
		web02 = "versions/signupv2.html";
		
		web01 = "D:\\FYP\\examples\\p1.html";
		web02 = "D:\\FYP\\examples\\p2.html";

		web01 = "MyCollabVersions\\MyCollab5.3.3.html";
		web02 = "MyCollabVersions\\MyCollab5.3.4.html";

		web01 = "MyCollabVersions\\c1.html";
		web02 = "MyCollabVersions\\c2.html";
		
		web01 = "before.html";
		web02 = "after.html";
		
		web01 = web1;
		web02 = web2;
		
	}

	public void setup() {
		// TODO Auto-generated method stub
		
		readDocs();
		pagesDifferences = getDifference();
		
//		pagesDifferences.addAll(differencePopup());
		//pagesDifferences=differencePopup();
		
	//	ArrayList<Difference> differences= differenceDeletedElements();//difference();
		pagesDifferences.addAll(differenceDeletedElements());

		//differencePopup();
//		if(true==true)
//		{
//			return ;
//		}
		for(Difference d : pagesDifferences)
		{
			d.display();
		}
	}// setup

	public void setup(File file01, File file02) {
		// TODO Auto-generated method stub

		readDocs(file01, file02);

		pagesDifferences = getDifference();
		//pagesDifferences.addAll(differencePopup());

		pagesDifferences.addAll(differenceDeletedElements());

		System.out.println("****Page Parser****");
		for(Difference d:pagesDifferences)
		{
			d.display();
		}

		//		}
	}//setup(File file01, File file02)

	private void readDocs() {
		// TODO Auto-generated method stub

		web1_doc=readFile(web01);
		web2_doc=readFile(web02);

	}//readDocs
	private void readDocs(File file01, File file02) {
		// TODO Auto-generated method stub

		web1_doc=readFile(file01);
		web2_doc=readFile(file02);

	}

	//for added popups
	public ArrayList<Difference> differencePopup()
	{
		ArrayList<Difference> differences = new ArrayList<Difference>();

		Elements before_list=web1_doc.getAllElements();
		Elements after_list=web2_doc.getAllElements();

		for (int i = 0; i < before_list.size(); i++) {
			Boolean matched = false;
			Element elementV1 = (Element) before_list.get(i);
			//remove all elements that matches perfectly
			if (matched == false)
			{
				Attributes attrV2=elementV1.attributes();

				//Attributes attrV1=elementV2.attributes();								
				if(attrV2.hasKey("onclick"))
				{	

					for(Attribute attribute:attrV2)
					{
						if(isImp(attribute))
						{
							Elements partialMatched=web2_doc.getElementsByAttributeValue(attribute.getKey(), attribute.getValue());
							Boolean attributematches=(partialMatched.size()>0)?(true):(false);

							for(Element elmnt:partialMatched)
							{
								Attributes attrV1=elmnt.attributes();

								if(attrV1.get("onclick").toLowerCase().equals(attrV2.get("onclick")))
								{
									//System.out.println("aaaa");

									Difference d=CompareJsFuncChanged(elementV1,elmnt);
									if(!d.isEmpty()){
										//System.out.println("aaaa");
										//<Difference> found
										differences.add(d);
									}

								}

							}
							if(attributematches==true)
							{
								break;
							}
						}

						if(attribute.getKey().toLowerCase().equals("onclick"))
						{
						}
					}
				}
			}
		}
		return differences;
	}
	
	private String getProjectname(String path) {
		
		return path.substring(0,path.lastIndexOf('\\'));
		
	}
	private String getScriptInStr(Element script,String htmlfile) {
	    File f=new File(getProjectname(htmlfile)+"\\"+script.attr("src"));
	    //System.out.println(f.getAbsolutePath());
	   
	    try{
	    File file = new File(f.getAbsolutePath());
	    FileInputStream fis = new FileInputStream(file);
	    byte[] data = new byte[(int) file.length()];
	    fis.read(data);
	    fis.close();
	    
	    String str = new String(data, "UTF-8");
	    return str;
//	    System.out.println(str);
	    }
	    catch(Exception e)
	    {
	    	
//	    	System.err.println(e.getMessage());
	    	return new String();
	    }

	}
	//compare JS fucntion in both pages and return <Difference>
	private Difference CompareJsFuncChanged(Element before,Element after) {


		Difference difference = new Difference();
		
		String before_func = before.attr("onclick");
		String after_func = after.attr("onclick");

		Elements scriptbefore = web1_doc.getAllElements().select("script");
		Elements scriptafter = web2_doc.getAllElements().select("script");

		StringBuilder scriptB = new StringBuilder(scriptbefore.toString());
		StringBuilder scriptA = new StringBuilder(scriptafter.toString());

		Elements scriptsB = web1_doc.getElementsByTag("script");
		Elements scriptsA = web2_doc.getElementsByTag("script");
		
		Boolean matched=false;
		Boolean InHtml=false;
		
		int start=0;
		String sB="";
		//get all javascript files and check function in each file
		for (Element script : scriptsB ) {
			if(script.hasAttr("src"))
			{
				sB= getScriptInStr(script,web01);
				start = getStartIndex(before_func,new StringBuilder(sB));		
			}
			else
			{
				sB= getScriptInStr(script,web01);				
				start = getStartIndex(before_func,new StringBuilder(sB));		
				if(start>=0)
				{
					InHtml=true;
					break;
				}
					
			}
			
			if(start>=0)
			{
				//System.out.println("Break");
				break;
			}
			scriptB=new StringBuilder(script.toString());			
		}
		
		String FunV1 = new StringBuilder(sB).substring(start);			
		//get all javascript files and check function in each file
		for (Element script : scriptsA ) {
			if(script.hasAttr("src"))
			{
				sB= getScriptInStr(script,web02);
				start=getStartIndex(after_func,new StringBuilder(sB));
			}
			else
			{
				sB= getScriptInStr(script,web02);
				start = getStartIndex(before_func,new StringBuilder(sB));		
				if(start>=0)
				{
					scriptB=new StringBuilder(sB.toString());
					InHtml=true;
					break;

				}
			}

			if(start>=0)
			{
				//System.out.println("Break");
				break;
			}
			scriptB=new StringBuilder(sB.toString());			
		}
		

		String FunV2 = new StringBuilder(sB).substring(start);
		
		FunV2 = getFunctionFromScript(new StringBuilder(FunV2));
		FunV1 = getFunctionFromScript(new StringBuilder(FunV1));

		//System.out.println(FunV1);
		//System.out.println(FunV2);

		
		//alert is present in V1?
		if(FunV1.indexOf("alert") >= 0)
		{
			//alert is present in V1. in V2?
			if(FunV2.indexOf("alert") < 0 )
			{
				//<Difference> found
				//System.out.println("Alert is deleted");
				
				//<Difference> found
				
				difference.setTagV1(before);
				difference.setTagV2(after);
				new AttributeInfo(new Attribute("alert","Deleted"), "Alert Deleted", 22);
				
			}

		}

		//present in V2?
		if(FunV2.indexOf("alert") >= 0 )
		{
			//present in V2?
			if(FunV1.indexOf("alert") < 0)
			{				
				//System.out.println("Alert is Added");
				
				//<Difference> found
//				differences.tagV1=before;
//				differences.tagV2=after;
//				differences.setDifferenceCode(21);
//				differences.addAttribute(new Attribute("alert","Added"));
				difference.setTagV1(before);
				difference.setTagV2(after);
				new AttributeInfo(new Attribute("alert","Added"), "Alert Added", 21);
			}

		}
		return difference;
	}

	private int getStartIndex(String func_name, StringBuilder scriptB) {
		// TODO Auto-generated method stub			
		func_name=func_name.substring(0,func_name.indexOf("("));
		int start=0;

		while(start<scriptB.length())
		{			
			int m=scriptB.indexOf("function", start);						
			if(m>0)
			{
				int nEnd=scriptB.indexOf("(", start);

				if(scriptB.substring(start,nEnd).indexOf(func_name)>0)
				{
					return m;
				}
				start=m+8;
			}
			else
			{
				return start;
			}
		}
		return -1;
	}

	private String getFunctionFromScript(StringBuilder script) {


		int count=0;
		int endIndex=0;

		Boolean isStart=false;
		for(int i=0;i<script.length();i++)
		{
			if((script.charAt(i)=='}'))
			{
				count--;
			}
			if((script.charAt(i)=='{'))
			{
				isStart=true;
				count++;
			}

			if((count==0)&& isStart)
			{
				endIndex=i;
				break;
			}
		}

		return script.substring(0,endIndex);	
		// TODO Auto-generated method stub

	}

	public ArrayList<Difference> getDifference() {

		ArrayList<Difference> differences = new ArrayList<Difference>();
		//int indexUp=0;//index difference

		Elements before_a_tags = web1_doc.body().getElementsByTag("a");
		Elements after_list = web2_doc.body().getAllElements();

		// for those lines which are there in current code but not in Previous
		// version
		for (int i = 0; i < after_list.size(); i++) {


			Element elementV2 = (Element) after_list.get(i);
			Boolean newElement = true; //element added in v2?
			Boolean Imp = false; // element has id or name?

			Attributes attributes = getAttr(elementV2);// Find id and Name Attributes in the element, whichever is available				

			Difference delta = new Difference();

			for(Attribute attribute : attributes)// only id and name, whichever is available
			{
				//					if(isImp(attribute))
				//{
				Imp = true; // it has important/unique attributes, id/name

				//find element with same attribute value in previous version
				Elements partialMatched = web1_doc.getElementsByAttributeValue(attribute.getKey(), attribute.getValue());

				if(partialMatched.size() > 0)//if element partially matched (idV1 = idV2 || nameV1 = nameV2)
				
				{				
					int index = 0;
					if(elementV2.tagName().equals("a")){

						index = checkBestMatch(elementV2, partialMatched,  before_a_tags);
					}
					newElement = false; // it is not a new element

					Element partialmatch = partialMatched.get(index); //get matched element, We always take 1st element from list
					
					delta.setTagV1(partialmatch);
					delta.setTagV2(elementV2);
					

					//****************************************							
					//s:check hierarchy error
					{						
						boolean indexChanged = false;

						Element temp_nodeV2 = elementV2, temp_nodeV1 = partialmatch;

						while(temp_nodeV2.parentNode() !=null){

							if(! (temp_nodeV2.parentNode().nodeName().toLowerCase().equals(temp_nodeV1.parentNode().nodeName().toLowerCase()))){
								indexChanged = true;
								break;
							}
							temp_nodeV1 = (Element) temp_nodeV1.parentNode();
							temp_nodeV2 = (Element) temp_nodeV2.parentNode();
						}

						if(indexChanged){
							//<Difference> found
							delta.setTagV1(partialmatch);
							delta.setTagV2(elementV2);

							AttributeInfo attr_info = new AttributeInfo(new Attribute("hierarchy", ""), "HIERACHY CHNAGED", 11);
							delta.addAttributeInfo(attr_info);

						}
						else
						{
							//*************Index Changed-******************
							AttributeInfo attr_info = CompareIndexDifference(partialmatch,elementV2);
							if(attr_info!=null)
							{
								//<Difference> found
								delta.addAttributeInfo(attr_info);

							}
						}
					}//s:check hierarchy error
					//************************************************



					//for select functionality
					if(elementV2.tagName().toLowerCase().equals("select"))
					{
						//System.out.println("select");

						AttributeInfo attr_info = valueDeletedDropdownDifference(elementV2, partialmatch);
						if(! (attr_info == null) )
						{
							//<Difference> found
							delta.addAttributeInfo(attr_info);
						}
					}
					
					ArrayList<AttributeInfo> attributes_info = getElementDifference(partialmatch,elementV2);
					if(! (attributes_info == null) )
					{
						//<Difference> found
						delta.addAttributeInfo(attributes_info);
						break; // id || name has been matched, no need to check both (redundancy)

					}

				}// if partially matched

				//}// if attribute is Imp

			}//loop for attributes

			// element ADDED
			if(newElement && Imp)
			{
				Difference newElementDifference = new Difference(null, elementV2);

				if(elementV2.hasAttr("required"))
				{
					newElementDifference.addAttributeInfo(new AttributeInfo(new Attribute("required", "null"), "New Added Element that is required", 14) );
				}
				else
				{
					newElementDifference.addAttributeInfo(new AttributeInfo(new Attribute("tag", elementV2.tagName()), "Element Added in html", 24) );

				}

				differences.add(newElementDifference);
			}// element added

			if( delta.getAttributesInfo().size() > 0){

				differences.add(delta);
			}

		}// for( i < after_list.size() )

		return differences;
	}// getDifference

	private int checkBestMatch(Element elementV2, Elements partialMatched, Elements before_a_tags) {
		// TODO Auto-generated method stub
		
		for (int i=0; i< partialMatched.size(); i++) {

			if(before_a_tags.contains(partialMatched.get(i))){
				before_a_tags.remove(partialMatched.get(i));
				return i;
			}
//			if(!checkHierarchyChange(elementV2, partialElement)){
//				
//				return i;
//			}
		}
		return 0;
		
	}

	private Attributes getAttr(Element elementV2) {
		Attributes attr=new Attributes();

		if(elementV2.hasAttr("id"))
		{
			attr.put(new Attribute("id",elementV2.id()));
		}
		if(elementV2.hasAttr("name"))
		{
			attr.put(new Attribute("name",elementV2.attr("name") ));
		}
		if(elementV2.hasAttr("href"))
		{
			attr.put(new Attribute("href",elementV2.attr("href") ));
						
		}
//		
		return attr;
	}
	
	private boolean checkHierarchyChange(Element elementV1, Element elementV2) {
		Difference delta = new Difference();

		boolean indexChanged = false;

		Element temp_nodeV2 = elementV2, temp_nodeV1 = elementV2;

		while(temp_nodeV2.parentNode() !=null){

			if(! (temp_nodeV2.parentNode().nodeName().toLowerCase().equals(temp_nodeV1.parentNode().nodeName().toLowerCase()))){
				indexChanged = true;
				break;
			}
			temp_nodeV1 = (Element) temp_nodeV1.parentNode();
			temp_nodeV2 = (Element) temp_nodeV2.parentNode();
		}

		if(indexChanged){
			
			//<Difference> found
			delta.setTagV1(elementV1);
			delta.setTagV2(elementV2);

			AttributeInfo attr_info = new AttributeInfo(new Attribute("id:"+elementV1.id()+"name:"+elementV1.nodeName()+"href:"+elementV1.attr("href"), elementV2.tagName()), "HIERACHY CHNAGED", 11);
			
			delta.addAttributeInfo(attr_info);
		
		}
		else
		{
			//*************Index Changed-******************
			AttributeInfo attr_info = CompareIndexDifference(elementV1, elementV2);
			if(attr_info!=null)
			{
				//<Difference> found
				delta.addAttributeInfo(attr_info);

			}
		
		}
		
		return indexChanged;
	}// end checkHierarchyChange

	private AttributeInfo CompareIndexDifference(Element before,Element after) {

		int i=0,j=0;


		for(Element e1 : before.parent().children())
		{
			if(before.id().toLowerCase().equals(e1.id().toLowerCase()) && !before.id().isEmpty())
			{
				break;
			}
			if(before.attr("name").toLowerCase().equals(e1.attr("name")) && before.hasAttr("name"))
			{
				break;
			}
			i++;

		}

		for(Element e1 : after.parent().children())
		{
			if(after.id().toLowerCase().equals(e1.id().toLowerCase()) && !after.id().isEmpty())
			{
				break;
			}
			if(after.attr("name").toLowerCase().equals(e1.attr("name")) && after.hasAttr("name"))
			{
				break;
			}
			j++;


		}
		//		System.err.println("err"+before.id());

		if(i != j)
		{
			//commented by sherry
			//System.err.println("err"+before.id());

			return indexChanged(after, before);

		}
		return null;
	}

	public ArrayList<Difference> differenceDeletedElements() {

		ArrayList<Difference> differences = new ArrayList<Difference>();
		
		Elements before_list=web1_doc.body().getAllElements();


		for (int i = 0; i < before_list.size(); i++) {
			
			

			Element elementV1 = before_list.get(i);
			Boolean newElement = true;
			Boolean Imp = false;

			// for those lines which are there in previous code but removed in current html
			// version
			Attributes attr=elementV1.attributes();
			for(Attribute attribute:attr)
			{
				if(isImp(attribute))
				{
					Imp=true;
					//find element with same atribute value in previous version
					Elements partialMatched=web2_doc.getElementsByAttributeValue(attribute.getKey(), attribute.getValue());

					if(partialMatched.size()>0)//if element partially matched
					{
						newElement=false;
						break;
					}


				}
				//if no attribute maatches
			}
			if(newElement && Imp)
			{
				Difference delta= new Difference(elementV1, null);
				AttributeInfo attr_info = new AttributeInfo(new Attribute("tag", elementV1.tagName()), "Element deleted from V2", 23); 
				delta.addAttributeInfo(attr_info);
				differences.add(delta);
			}
		}
		return differences;
	}
	

/*
	 // Functions not used yet
	 private boolean isEqual(Element elementV2, Element elementV1){
		if (
				elementV2.id().toLowerCase().equals(elementV1.id().toLowerCase())
				&&
				elementV2.tagName().toLowerCase().equals(elementV1.tagName().toLowerCase())
				&&
				elementV2.attr("name").toLowerCase().equals(elementV1.attr("name"))
				)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private Difference ElementDeletedInNewVersion(Element elementV1) {

		Difference differences = new Difference();


		differences.setDifferenceCode(23);
		differences.addAttribute(new Attribute("null", "null"));
		differences.setTags(elementV1,null);
		differences.AddDifference("Element deleted from html");


		return differences;

	}

	private boolean IsElementsEqual(Element elementV2, Element elementV1) {

		if( elementV1.id().toLowerCase().equals(elementV2.id().toLowerCase()) && elementV1.className().toLowerCase().equals(elementV2.className().toLowerCase()) &&
				elementV1.attr("name").toLowerCase().equals(elementV2.attr("name")) )
		{
			if(elementV1.id().isEmpty() && elementV1.attr("name").isEmpty())
			{
				return false;
			}

			return true;
		}
		else
		{
			return false;
		}
	}//IsElementsEqual(Element elementV2, Element elementV1)
// Functions not used yet */
 

	private AttributeInfo indexChanged(Element elementV2, Element elementV1) {

		return new AttributeInfo(new Attribute("id", elementV2.id()), "Index Change", 12);
	}

	public Boolean isImp(Attribute attr)
	{
		if(attr.getKey().toLowerCase().equals("id")||attr.getKey().toLowerCase().equals("name")||attr.getKey().toLowerCase().equals("href"))
		{
			return true;
		}
		return false;
	}
	public AttributeInfo valueDeletedDropdownDifference(Element a,Element b)
	{

		List<Node> after= a.childNodes();
		List<Node> before= b.childNodes();

		Boolean matched=true;

		for(Node befr:before)
		{
			//System.out.println("***");
			if( !(after.indexOf(befr) > 0) )
			{
				matched=false;
				break;
			}
		}

		if(matched == false)
		{
			return ( new AttributeInfo(new Attribute("select","value deleted"), "Value Deleted from Drop down", 15) );
		}

		return null;
	}


	public	ArrayList<AttributeInfo> getElementDifference(Element v1,Element v2)
	{
		ArrayList<AttributeInfo> attributesInfo = new ArrayList<>();


		//Element text not found
		if(!v1.ownText().toLowerCase().equals(v2.ownText().toLowerCase()))
		{			
			attributesInfo.add( new AttributeInfo( new Attribute("text", "change"), "Element text not found",  10) );
		}


		for(Attribute attrV2 : v2.attributes())
		{
			Boolean valuefound = false, keyfound = false;

			for(Attribute attrV1 : v1.attributes())
			{
				if(attrV1.getKey().toLowerCase().equals(attrV2.getKey().toLowerCase()))
				{
					keyfound=true;

					if(attrV1.getValue().toLowerCase().equals(attrV2.getValue().toLowerCase())){
						valuefound=true;
						break;
					}
				}
			}

			//attribute UPDATED
			if(keyfound && !valuefound)
			{
				attributesInfo.add( new AttributeInfo( attrV2, "\t"+attrV2.getKey()+":"+attrV2.getValue()+ "Update", 1) );
			}

			// attribute ADDED
			if(!keyfound)
			{
				attributesInfo.add( new AttributeInfo( attrV2, "\t"+attrV2.getKey()+":"+attrV2.getValue()+ "Added", 2) );
			}

		}// for(Attribute attrV2 : v2.attributes())

		// attribute DELETED
		for(Attribute attrV2 : v1.attributes())
		{
			Boolean keyfound=false;

			//Attribute matcheddAttrB;
			for(Attribute attrV1:v2.attributes())
			{
				if(attrV1.getKey().toLowerCase().equals(attrV2.getKey().toLowerCase()))
				{
					keyfound=true;
					if(attrV1.getValue().toLowerCase().equals(attrV2.getValue().toLowerCase())){
						break;
					}
				}
			}
			
			if(!keyfound)
			{
				attributesInfo.add( new AttributeInfo( attrV2, "\t"+attrV2.getKey()+":"+attrV2.getValue()+ "Deleted" , 3) );
			}
		}// for(Attribute attrV2 : v1.attributes())

		return attributesInfo;
	}// getElementifference


	public Document readFile(String fileName) {

		try {
			File input = new File(fileName);
			Document document = Jsoup.parse(input, "UTF-8", fileName);
			//			System.out.println("****************"+document.toString()+"\n****************");
			return document;
		} catch (IOException io) {
			//System.out.println(io.getMessage());
		}

		return null;
	}// end readFile
	public Document readFile(File file) {

		try {
			File input = file;
			Document document = Jsoup.parse(input, "UTF-8", input.getAbsolutePath());
			//			System.out.println("****************"+document.toString()+"\n****************");
			return document;
		} catch (IOException io) {
			//System.out.println(io.getMessage());
		}

		return null;
	}// end readFile

}

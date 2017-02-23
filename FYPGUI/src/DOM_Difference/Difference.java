package DOM_Difference;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.hamcrest.Matcher;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

public class Difference {

	Element tagV1, tagV2;

	ArrayList<AttributeInfo> attributesInfo = new ArrayList<>();
	ArrayList<String> differenceTypes =  new ArrayList<>();

	public ArrayList<String> getAllCategoryTypes(){

		for (int i = 0; i < attributesInfo.size(); i++) {
			differenceTypes.add(attributesInfo.get(i).getDifferenceType());			
		}
		return differenceTypes;
	}

	

	public Element getTagV1() {
		return tagV1;
	}



	public void setTagV1(Element tagV1) {
		this.tagV1 = tagV1;
	}



	public ArrayList<String> getDifferenceTypes() {
		return differenceTypes;
	}



	public void setDifferenceTypes(ArrayList<String> differenceTypes) {
		this.differenceTypes = differenceTypes;
	}
	
	public void setTags(Element tagV1, Element tagV2){
		setTagV1(tagV1);
		setTagV2(tagV2);
	}

	public Element getTagV2() {
		return tagV2;
	}
	public void setTagV2(Element tagV2) {
		this.tagV2 = tagV2;
	}

	public String unique_attr(Element tag)
	{
		StringBuilder uniqueAttrVal=new StringBuilder();
		if(tag!=null)
		{	
			if(tag.hasAttr("id"))
			{
				uniqueAttrVal.append(" id:"+tag.attr("id"));
			}
			if(tag.hasAttr("name"))
			{
				uniqueAttrVal.append(" name:"+tag.attr("name"));
		
			}
			if(tag.hasAttr("href"))
			{
				uniqueAttrVal.append(" href:"+tag.attr("href"));
		
			}
		}
		return uniqueAttrVal.toString();
	}
	public void display() {
		//String p = (tagV1.id().isEmpty()) ? "name: " +tagV1.attr("name"): "id: " + tagV1.id();
//		/System.err.println("DISPLAY\n");
		String p = "";
		String v="";

		if(tagV1 != null){
			//commented by sherry
//		    System.out.println("Version 1 Code=> "+tagV1.getAllElements());
//		    System.out.flush();


			p = "TagV1: " + tagV1.getAllElements();
			v=unique_attr(tagV1);
		}
		if(tagV2 != null){
			//commented by sherry
//		    System.out.println("Version 2 Code=> "+tagV2.getAllElements());
//		    System.out.flush();

			p += "\nTagV2: " + tagV2.getAllElements();
			String u=unique_attr(tagV1);
			if(tagV1!=null)
			{

/*				if(u.length()>v.length())
				{
					v=u.substring(u.indexOf(v), u.indexOf(v)+v.length()-1);
				}	
*/			}
			else
			{
				v=unique_attr(tagV2);
			}
			
			//v+=unique_attr(tagV2);
		}
//    System.out.println("************************************** ");		
    System.out.println("Element with unique attribute=> "+v);
//  System.out.println("************************************** ");		
    System.out.flush();

		//System.out.println(p);
//		System.out.println();
		
		for (AttributeInfo attributeInfo : attributesInfo) {
			
			System.out.println("Diff: "+attributeInfo.getDifferenceCode() + " | " + attributeInfo.getKey() + " | " + attributeInfo.getDifferenceType());
		    System.out.flush();

			if(attributeInfo.getDifferenceType().contains(("1.2.1")))
			{
				//System.out.println(tagV1.getAllElements());
			    System.out.flush();

				for(Element e : tagV1.parents())
				{
					System.out.print(" "+e.tagName());					
				    System.out.flush();

				}
				//System.err.println(tagV2.getAllElements());

				for(Element e : tagV2.parents())
				{
					System.out.print(" "+e.tagName());
				    System.out.flush();

					
				}
			}
		}
	    System.out.flush();

		
		System.out.println();
	}
	

	public Difference(Element elementV1, Element elementV2) {
		// TODO Auto-generated constructor stub
		tagV1 = elementV1;
		tagV2 = elementV2;
		
	}
	
	public Difference() {
		// TODO Auto-generated constructor stub
		tagV1 = new Element(Tag.valueOf("UNKONWNV1TAG"), "");
		tagV2 = new Element(Tag.valueOf("UNKONWNV2TAG"), "");
	}


	public Difference(Difference difference) {
		// TODO Auto-generated constructor stub
	}



	public void AddDifference(String diff) {
		//differenceType.add(diff);
		//		if(this.differenceType.equals(""))
		//		{
		//			this.differenceType = diff;
		//		}
		//		else
		//		{
		//			this.differenceType+=diff;
		//		}
		//
		//		StringBuilder sbuild=new StringBuilder(this.differenceType);
		//		sbuild.append(diff);
		//		this.differenceType=sbuild.toString();

	}
	public Boolean isEmpty()
	{
		if(tagV1==null)
		{
			return true;
		}
		return false;
	}
	
	public ArrayList<AttributeInfo> getAttributesInfo() {
		return attributesInfo;
	}

	public void setAttributesInfo(ArrayList<AttributeInfo> attributesInfo) {
		this.attributesInfo = attributesInfo;
	}


	public void addAttributeInfo(AttributeInfo attr_info){
		attributesInfo.add(attr_info);
	}
	
	public void addAttributeInfo(ArrayList<AttributeInfo> attr_info){
		attributesInfo.addAll(attr_info);
	}

}

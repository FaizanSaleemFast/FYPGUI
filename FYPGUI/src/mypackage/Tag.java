package mypackage;

import java.util.ArrayList;

public class Tag {
	private String tag;
	private String value;
	private int lineno;

	public Tag(String tg,String val,int line)
	{
		tag=tg;
		value=val;
		lineno=line;
	}
	public Tag()
	{
	}

	public Tag FindTag(Exception e)
	{
		ArrayList<StackTraceElement> trace=new ArrayList<StackTraceElement>();
		
		for(StackTraceElement i: e.getStackTrace())
		{
			trace.add(i);
	
		}
		
		String ElementInfo=e.getMessage().substring(e.getMessage().indexOf("Using="));		
		String id=new StringBuilder(ElementInfo).substring(6, ElementInfo.indexOf(","));		
		System.out.print("Using:"+  id);	
		
		ElementInfo=e.getMessage().substring(e.getMessage().indexOf("value=")+6);
		String value=(new StringBuilder(ElementInfo)).substring(0, ElementInfo.indexOf("}"));		
		System.out.print(" Value:"+  value);	
	
		System.out.println(" At Line number: "+trace.get(trace.size()-1).getLineNumber());
		
		
		int line=trace.get(trace.size()-1).getLineNumber();
		
		tag=id;
		this.value=value;
		
		return new Tag(id,value,line);
		

	}

	public int getLineno() {
		return lineno;
	}
	public void setLineno(int lineno) {
		this.lineno = lineno;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	
}

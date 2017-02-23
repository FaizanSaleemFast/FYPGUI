package mypackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class myClass {

	public static void main(String[] args) {
	try
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("file://D://eclipse MarsFx//FYP//Repair_it_v1.1");
//		driver.findElement(By.id("eid")).sendKeys("userID");
//		driver.findElement(By.id("eid")).sendKeys("userID");
//		driver.findElement(additem());

//		driver.findElement(By.id("pw")).sendKeys("password");
//		driver.findElement(By.id("submit")).click();
//		driver.findElement(By.linkText("Logout")).click();
	}
	catch(Exception e)
	{
//		e.printStackTrace();
//		Tag n=new Tag();
//		n.FindTag(e);

	}

}

}


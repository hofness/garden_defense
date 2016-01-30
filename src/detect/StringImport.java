package detect;

import processing.core.PApplet;
import processing.serial.*;

public class StringImport extends PApplet{
	
	
	String label = "test:test2,";
 	String vals = "test:test2,";
 	String[] items = new String[100];
 	String inString = "test:test2,";
	Serial myPort; // The serial port:
	 
	float circDiameter;
	float circColour;
	
	
	 
	public void setup() {
	  size(400, 300);
	 
	  println(Serial.list());
	 
	  // You may need to change the number in [ ] to match 
	  // the correct port for your system
	  myPort = new Serial(this, Serial.list()[4], 9600);
	 
	  circDiameter = 30;
	  circColour = 0;
	 
	  myPort.bufferUntil(',');
	}
	 
	public void draw() {
	  background(200);
	  
	  circDiameter = serialEvent(myPort);
	  circDiameter = (circDiameter/4);
		 println("updated circ diameter " +circDiameter);
		  //circDiameter = mouseY;
	//	 map(circDiameter,0, 300, 0, 1023);
	 
	  //  fill(165, 27, 27);
	
	  ellipse(width/2, height/2, circDiameter, circDiameter);
	}
	 
	 
	public float serialEvent(Serial myPort) {
	  // read a byte from the serial port:
	  String inString = myPort.readStringUntil(',');
	  println(inString);
	 // split the string into multiple strings
	 // where there is a ":"
	  String items[] = split(inString, ':');
	 
	  // if there was more than one string after the split
	  if(items != null && items.length != 0) {
	//  if (items.length > 1) {
	    // remove any whitespace from the label
	    String label = trim(items[0]);
	    println(label);
	    // remove the ',' off the end
	    String val = split(items[1], ',')[0];
	    println(val);
	    // check what the label was and update the appropriate variable
	    if (label.equals("xpos")) {
	      float tempDiameter = Float.parseFloat(val);
	      circDiameter =  tempDiameter;
	    }
	    if (label.equals("switch")) {
	      //circColour = float(val);
	    }
	  }
	return circDiameter;
	}

}

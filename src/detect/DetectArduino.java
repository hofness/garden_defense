package detect;

import processing.core.PApplet;
import processing.serial.*;
import cc.arduino.*;


public class DetectArduino extends PApplet{
	

	String ledPin = "13";
	String st = "HIGH";
	Serial myPort;  // Create object from Serial class
	String val;     // Data received from the serial port
	int lf = 200;
	
	public void setup()
	{
	
		println("Available serial ports:");
 		println(Serial.list());
 		myPort = new Serial(this, Serial.list()[4], 9600);
 		println(myPort, "selected");
 		myPort.clear();
 		  // Throw out the first reading, in case we started reading 
 		  // in the middle of a string from the sender.
 		  val = myPort.readString();
 		  val = null;
	}

	public void draw()
	{
		 while (myPort.available() > 0) {
			   val = myPort.readString();
			    if (val != null) {
			      println(val);
			     // myPort.write("<" +st +"," +">");
			    // myPort.write("s" +ledPin +"," +"st"  +"/n");
			     //myPort.write("<" +st +"," +ledPin  +">");
			      //myPort.write("HIGH");
			      myPort.write("s" +ledPin +"," +lf);
			      delay(10);
			      myPort.write("s" +ledPin +"," +0);
			    } 
	}
}
}


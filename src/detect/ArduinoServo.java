package detect;

import processing.core.PApplet;
import processing.serial.Serial;
import gab.opencv.*;
import processing.*;

import java.awt.*;

import javax.print.attribute.standard.OutputDeviceAssigned;

import codeanticode.gsvideo.GSCapture; 
import cc.arduino.Arduino;

public class ArduinoServo extends PApplet {

		Serial myPort; // The serial port
	 	String val = null;;
	 	
	 	GSCapture video; //video capture
	 	OpenCV opencv; //video processing
	 	//Arduino atmega; //microcontroller output
	 	
	 	int CamSizeX = 320; //camera width (pixels),   usually 160*n
	 	int CamSizeY = 240; //camera height (pixels),  usually 120*n
	 	boolean PRINT_FRAMERATE = false;   
	 	
	 	//byte sPinX = 10; //servo pin for x direction
	 	//byte dPinX = 0; //direction pin
	 			
	 	float xStart = 0;
	 	float xEnd = 0;
	 	float xTarget = 0;
	 	float xMove = 0;
	 	float xAbs = 0; //the number of steps that need to be taken 
	 	int xNew = 0;  //what the target value becomes to compare it with the turret location
	 	float xloc = 0;  //the location of the x value on the targeting screen
	 	float yloc = 0; //location of the y value on the targeting screen
	 	float xRange = 1023; //the maximum resistance value as represented over serial
	 	float xMax = CamSizeX; //establishing the conversion parameters
	 	float xConvertFactor = xRange/xMax; // the factor by which to convert the resistor value
	 	float XConverted = 0; //the x value to convert to
	 	float xReturn = 0; //the xvalue returned from the serial event function

	 	int motorMax = 200; //steps in the motor
	 	int xMin = 0;
	 	float xLast = 0; 
	 	float xCurrent = 0;
	 	int signal = 0;
	 	
	 	float xVal = 0;
	 	float xStepps = 0;
	 	float xReturnDir = 0;
	 	String xDir = null; // 0 is false, 1 is true
	 	
	 	String myString = null;
	 	String label = null;
	 	String vals = null;
	 	String[] items = null;
	 	String inString = null;
	 	
	     public void setup() {
	    	println(xConvertFactor);
	    	println("Available serial ports:");
	 		println(Serial.list());
	 		myPort = new Serial(this, Serial.list()[4], 9600);
	 		println(myPort, "selected");
	 		myPort.bufferUntil(',');
	    	
	    	 size(CamSizeX, CamSizeY);
	    	 video = new GSCapture(this, CamSizeX, CamSizeY);
	    	 opencv = new OpenCV(this, CamSizeX, CamSizeY);
	    	 opencv.loadCascade("haarcascade_frontalface_alt.xml");  
	    	 video.start();
 
	     }

	      public void draw() {

	    	  scale(1); //if you want to zoom in turn this up, but performance gets funky
	    	  opencv.loadImage(video);

	    	  image(video, 0, 0 );

	    	  noFill();
	    	  stroke(255, 0, 0); //box and point color
	    	  strokeWeight(3); //line thickness
	    	  Rectangle[] target = opencv.detect();
	    	
	    	  // find x and y
	    	  for (int i = 0; i < target.length; i++) {
	    		   rect(target[i].x, target[i].y, target[i].width, target[i].height); //show the rectangle
	    		   xloc = target[i].x + (target[i].width/2);
	    		   yloc = target[i].y + (target[i].height/2);
	    		   println(xloc + "," + yloc);
	    	  }
	    		   
	    	  //find motor position
	    	  if (myPort.available() > 0) {
	    		  XConverted = serialEvent(myPort);
	    		 // xCurrent = (XConverted/xConvertFactor);
	    		  xCurrent = (XConverted);
	    		  println("x target or x location from ard " +xCurrent);
	    	  }
	    		  
	    	  //Find out if any faces were detected.
	    	  if(target.length > 0){
	    		  xNew =  (int) xloc;
	    		  println("xnew is: " +"<" +xNew +">");
	    		  
	    		  if(xCurrent != xNew) {
	    			  if(xCurrent < xNew){
	    				  myPort.write("<" +xNew +"," +xNew  +">");
	    			  }
	    			  
	    			  if (xCurrent > xNew){
	    				  myPort.write("<" +xNew +"," +xNew  +">");
	    			  }
	    				   
					}
    				 
	    		  }
	
	    	  	}
	    	  

	    	  
	    	  
	      public void captureEvent(GSCapture c) {
	    	  c.read();
	      }
	      
	     
	      public float serialEvent(Serial myPort) {
	    	  // read a byte from the serial port:
	    	  String inString = myPort.readStringUntil(',');
	    	  //println(inString); //for debugging
	    	 // split the string into multiple strings
	    	 // where there is a ":"
	    	  String items[] = split(inString, ':');
	    	  // if there was more than one string after the split
	    	  if(items != null && items.length != 0) {
	    	    // remove any whitespace from the label
	    	   label = trim(items[0]);
	    	    //println(label); //for debugging
	    	    // remove the ',' off the end
	    	    vals = split(items[1], ',')[0];
	    	   // println(vals); //for debugging
	    	    // check what the label was and update the appropriate variable
	    	    if (label.equals("xpos")) {
	    	    	xReturn = Float.parseFloat(vals);
	    	    	//return xReturn;
	    	    }
	    	    if (label.equals("target")) {
	    	    	xReturnDir = Float.parseFloat(vals);
	    	    	println("target is " +xReturnDir);
	    	    	return xReturnDir;
	    	    }
	    	  }
	    	//return xReturn;
	    	  return xReturnDir;
	    	}
	      
	 public void move(int steps, boolean dir) {
		 
	 }
	      
}
	     


	     
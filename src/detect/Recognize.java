package detect;

import processing.core.PApplet;
import gab.opencv.*;
import java.awt.*;
import codeanticode.gsvideo.GSCapture; 
import cc.arduino.Arduino;
import detect.Hunt; //this handles the hunting logic for the squirt gun

public class Recognize extends PApplet{

	GSCapture video; //video capture
	OpenCV opencv; //video processing
	//Arduino atmega; //microcontroller output
	
	int CamSizeX = 320; //camera width (pixels),   usually 160*n
	int CamSizeY = 240; //camera height (pixels),  usually 120*n
	boolean PRINT_FRAMERATE = false;     // set to true to print the framerate at the bottom of the IDE window
	//int ServoPinX = 4; //set servo pin for X movement
	//int ServoPinY = 2; // set servo pin for y movement
	//int ServoPinT = 3; //solenoid (trigger)
	
	public void setup() {
	 size(CamSizeX, CamSizeY);
	 video = new GSCapture(this, CamSizeX, CamSizeY);
	 opencv = new OpenCV(this, CamSizeX, CamSizeY);
	 opencv.loadCascade("sql_cascade.xml");  
	 video.start();
	  
	 //atmega = new Arduino(this, "/dev/tty", 9600);
	 //atmega.pinMode(ServoPinX, Arduino.SERVO);
	 //atmega.pinMode(ServoPinY, Arduino.SERVO);
	}

	public void draw() {
	  scale(1); //if you want to zoom in turn this up, but performance gets funky
	  opencv.loadImage(video);

	  image(video, 0, 0 );

	  noFill();
	  stroke(255, 0, 0); //box and point color
	  strokeWeight(3); //line thickness
	  Rectangle[] faces = opencv.detect();
	  println(faces.length);

	  for (int i = 0; i < faces.length; i++) {
	    println(faces[i].x + "," + faces[i].y);
	    rect(faces[i].x, faces[i].y, faces[i].width, faces[i].height); //show the rectangle
	    float xloc = faces[i].x + (faces[i].width/2);
	    float yloc = faces[i].y + (faces[i].height/2);
	    point(xloc, yloc); //show the center of the rectangle for targeting
	    
	    //atmega.servoWrite(ServoPinX, (int) constrain(xloc, 0, 180));
		//atmega.servoWrite(ServoPinY, (int) constrain(yloc, 0, 180));
	  }
	}

	public void captureEvent(GSCapture c) {
	  c.read();
	}
	
	public void trackTarget() {
		
	}
	
}
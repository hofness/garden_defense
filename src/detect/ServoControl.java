package detect;

import processing.core.PApplet;
import processing.serial.*;  

public class ServoControl extends PApplet{


	int gx = 15;
	int gy = 35;
	int spos=90;

	float leftColor = 0;
	float rightColor = 0;
	Serial port;                         // The serial port



	public void setup() 
	{
	  size(720, 720);
	  colorMode(RGB, 1);
	  noStroke();
	  rectMode(CENTER);
	  frameRate(50);

	  println(Serial.list()); // List COM-ports

	  //select second com-port from the list
	  port = new Serial(this, Serial.list()[4], 9600);
	  println(port, "selected");
	}

	public void draw() 
	{
	  background(0);
	  update(mouseX); 
	  fill(mouseX/4); 
	  rect(150, 320, gx*2, gx*2); 
	  fill(180 - (mouseX/4)); 
	  rect(450, 320, gy*2, gy*2);
	}

	void update(int x) 
	{
	  //Calculate servo postion from mouseX
	  spos= x/4;

	  //Output the servo position ( from 0 to 180)
	  port.write("s"+spos);
	  println("s"+spos);

	  // Just some graphics
	  leftColor = (float) (-0.002 * x/2 + 0.06);
	  rightColor =  (float) (0.002 * x/2 + 0.06);

	  gx = x/2;
	  gy = 100-x/2;

	}

}

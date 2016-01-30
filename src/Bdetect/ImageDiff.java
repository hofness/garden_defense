package Bdetect;

import processing.core.*;
import gab.opencv.*;

public class ImageDiff extends PApplet{

	OpenCV opencv;
	PImage  before, after, grayDiff;
	//PImage colorDiff;
	public void setup() {
	  before = loadImage("Fdetect/before.jpg");
	  after = loadImage("Fdetect/after.jpg");
	  size(before.width, before.height);

	  opencv = new OpenCV(this, before);    
	  opencv.diff(after);
	  grayDiff = opencv.getSnapshot(); 

	//  opencv.useColor();
	//  opencv.loadImage(after);
	//  opencv.diff(after);
	//  colorDiff = opencv.getSnapshot();
	 
	}

	public void draw() {
	  pushMatrix();
	  scale(0.5f);
	  image(before, 0, 0);
	  image(after, before.width, 0);
	//  image(colorDiff, 0, before.height);
	  image(grayDiff, before.width, before.height);
	  popMatrix();

	  fill(255);
	  text("before", 10, 20);
	  text("after", before.width/2 +10, 20);
	  text("gray diff", before.width/2 + 10, before.height/2+ 20);

	//  text("color diff", 10, before.height/2+ 20);
	}

}

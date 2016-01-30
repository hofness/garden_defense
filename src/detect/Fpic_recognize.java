package detect;

import processing.core.PApplet;
import gab.opencv.*;
import java.awt.Rectangle;
import processing.core.PImage;

public class Fpic_recognize extends PApplet {

	OpenCV opencv;
	Rectangle[] faces;
	PImage img;
	

	
	public void setup() {
	//img = loadImage("/home/name/projects/haar_classifiers/opencv-haar-classifier-training/sqrl5.jpg");
	img = loadImage("/home/name/Pictures/sqrl31.jpg");
	image(img, 100, 100);
	opencv = new OpenCV(this, img);
	size(opencv.width, opencv.height);
	opencv.loadCascade("sql_cascade.xml");
	faces = opencv.detect();
	}
	
	public void draw() {
	image(opencv.getInput(), 0, 0);
	noFill();
	stroke(255, 0, 0);
	strokeWeight(3);
	for (int i = 0; i < faces.length; i++) {
	rect(faces[i].x, faces[i].y, faces[i].width, faces[i].height);
	}
	}

}

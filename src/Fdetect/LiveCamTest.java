package Fdetect;
import gab.opencv.*;
import processing.core.PApplet;
import codeanticode.gsvideo.*;
import java.awt.*;

public class LiveCamTest extends PApplet{
	

	GSCapture video;
	OpenCV opencv;
	int CamSizeX = 640;
	int CamSizeY = 480;
	
	public void setup() {
	  size(CamSizeX, CamSizeY);
	  video = new GSCapture(this, CamSizeX, CamSizeY);
	  opencv = new OpenCV(this, CamSizeX, CamSizeY);
	  opencv.loadCascade(OpenCV.CASCADE_FRONTALFACE);  

	  video.start();
	}

	public void draw() {
	  scale(2);
	  opencv.loadImage(video);

	  image(video, 0, 0 );

	  noFill();
	  stroke(0, 255, 0);
	  strokeWeight(3);
	  Rectangle[] faces = opencv.detect();
	  println(faces.length);

	  for (int i = 0; i < faces.length; i++) {
	    println(faces[i].x + "," + faces[i].y);
	    rect(faces[i].x, faces[i].y, faces[i].width, faces[i].height);
	  }
	}

	public void captureEvent(GSCapture c) {
	  c.read();
	}


}

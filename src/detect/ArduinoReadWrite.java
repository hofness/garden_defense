package detect;

import processing.core.PApplet;
import processing.*;
import cc.arduino.Arduino;
import processing.serial.Serial;

public class ArduinoReadWrite extends PApplet{
	
	Serial port;
	String val;
	
	 //Variables for keeping track of the current servo positions.
    char servoTiltPosition = 90;
    char servoPanPosition = 90;
    //The pan/tilt servo ids for the Arduino serial command interface.
    char tiltChannel = 0;
    char panChannel = 1;

    //These variables hold the x and y location for the middle of the detected face.
    int midFaceY=0;
    int midFaceX=0;
    //The variables correspond to the middle of the screen, and will be compared to the midFace values
    int midScreenY = (height/2);
    int midScreenX = (width/2);
    int midScreenWindow = 10;  //This is the acceptable 'error' for the center of the screen.

    //The degree of change that will be applied to the servo each time we update the position.
    int stepSize=1;
	
	
	public void setup()
	{
		println("Available serial ports:");
		println(Serial.list());
		port = new Serial(this, Serial.list()[4], 9600);
		println(port, "selected");
		size(200,200); //make our canvas 200 x 200 pixels big
	}
	
	public void draw()
	 void draw() {

   	 
  	  // proceed detection
  	  Rectangle[] faces = opencv.detect( 1.2, 2, OpenCV.HAAR_DO_CANNY_PRUNING, 40, 40 );

  	  // display the image
  	  image( opencv.image(), 0, 0 );

  	  // draw face area(s)
  	  noFill();
  	  stroke(255,0,0);
  	  for( int i=0; ilength; i++ ) {
  	    rect( faces[i].x, faces[i].y, faces[i].width, faces[i].height );
  	  }
  	  
  	  //Find out if any faces were detected.
  	  if(faces.length > 0){
  	    //If a face was found, find the midpoint of the first face in the frame.
  	    //NOTE: The .x and .y of the face rectangle corresponds to the upper left corner of the rectangle,
  	    //      so we manipulate these values to find the midpoint of the rectangle.
  	    midFaceY = faces[0].y + (faces[0].height/2);
  	    midFaceX = faces[0].x + (faces[0].width/2);
  	    
  	    //Find out if the Y component of the face is below the middle of the screen.
  	    if(midFaceY < (midScreenY - midScreenWindow)){
  	      if(servoTiltPosition >= 5)servoTiltPosition -= stepSize; //If it is below the middle of the screen, update the tilt position variable to lower the tilt servo.
  	    }
  	    //Find out if the Y component of the face is above the middle of the screen.
  	    else if(midFaceY > (midScreenY + midScreenWindow)){
  	      if(servoTiltPosition <= 175)servoTiltPosition +=stepSize; //Update the tilt position variable to raise the tilt servo.
  	    }
  	    //Find out if the X component of the face is to the left of the middle of the screen.
  	    if(midFaceX < (midScreenX - midScreenWindow)){
  	      if(servoPanPosition >= 5)servoPanPosition -= stepSize; //Update the pan position variable to move the servo to the left.
  	    }
  	    //Find out if the X component of the face is to the right of the middle of the screen.
  	    else if(midFaceX > midScreenX + midScreenWindow){
  	      if(servoPanPosition <= 175)servoPanPosition +=stepSize; //Update the pan position variable to move the servo to the right.
  	    }
  	    
  	  }
  	  //Update the servo positions by sending the serial command to the Arduino.
  	  port.write(tiltChannel);      //Send the tilt servo ID
  	  port.write(servoTiltPosition); //Send the updated tilt position.
  	  port.write(panChannel);        //Send the Pan servo ID
  	  port.write(servoPanPosition);  //Send the updated pan position.
  	  delay(1);
  	}


}

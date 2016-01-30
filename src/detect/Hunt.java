package detect;

import processing.core.PApplet;
import cc.arduino.Arduino;

public class Hunt extends PApplet {

	Arduino atmega; //microcontroller output
	
	int ServoPinX = 4; //set servo pin for X movement
	int ServoPinY = 2; // set servo pin for y movement
	int ServoPinT = 3; //solenoid (trigger)
	
	int len = Serial.list().length;    //get number of ports available
    println(Serial.list());      //print list of ports to screen
	
	public void setup() {
		
	atmega = new Arduino(this, "/dev/tty", 9600);
	atmega.pinMode(ServoPinX, Arduino.SERVO);
	atmega.pinMode(ServoPinY, Arduino.SERVO);
	atmega.pinMode(ServoPinT, Arduino.SERVO);
	
	}
	
	public void target(xloc, yloc) {
		//atmega.servoWrite(ServoPinX, (int) constrain(xloc, 0, 180));
				//atmega.servoWrite(ServoPinY, (int) constrain(yloc, 0, 180));
	}
}

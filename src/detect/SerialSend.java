package detect;


import processing.core.PApplet;
import processing.serial.*;
import cc.arduino.*;

public class SerialSend extends PApplet{
	
//////////////////////////////////Processing/////////////////////////////////
/**
* Simple Write. < modified to control stepper direction
*
* Check if the mouse is over a rectangle and writes the status to the serial port.
* This example works with the Wiring / Arduino program that follows below.
*/

Arduino arduino;

Serial myPort;  // Create object from Serial class
// Data received from the serial port
int ledPin = 13;              // pin for LED
int XdirPin = 7;             // pin for stepper X direction
int XstepPin = 10;        //pin for X step


public void setup()
{
	println("test");
size(200, 200);
// I know that the first port in the serial list on my mac
// is always my  FTDI adaptor, so I open Serial.list()[0].
// On Windows machines, this generally opens COM1.
// Open whatever port is the one you're using.
// String portName = Serial.list()[0];
//myPort = new Serial(this, portName, 9600);
arduino = new Arduino(this, Arduino.list()[4], 9600); // v1
arduino.pinMode(ledPin, Arduino.OUTPUT);
arduino.pinMode(XdirPin, Arduino.OUTPUT);
println(arduino, "selected");
}

public void draw() {
background(255);
if (mouseOverRect() == true) {  // If mouse is over square,
fill(204);                    // change color and
arduino.digitalWrite(ledPin, Arduino.HIGH);              // LED on
arduino.digitalWrite(XdirPin, Arduino.HIGH);            // Stepper direction this way
println(XdirPin, " pin high");

}
else {                        // If mouse is not over square,
fill(0);                      // change color and
arduino.digitalWrite(ledPin, Arduino.LOW);      // LED off
arduino.digitalWrite(XdirPin, Arduino.LOW);    //Stepper direction the other way
println(XdirPin, " pin low");

}
rect(50, 50, 100, 100);         // Draw a square
}

boolean mouseOverRect() { // Test if mouse is over square
return ((mouseX >= 50) && (mouseX <= 150) && (mouseY >= 50) && (mouseY <= 150));
}
}
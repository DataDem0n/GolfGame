#Crazy Putting: Phase 1

###PURPOSE OF THE PROJECT:

The purpose of the project is the implementation of a physics engine for the golf ball movement depicted on a GUI and turning it into a game. The GUI is a two-dimensional representation of a three-dimensional function. It represents different hights with different colors, shown on a legend. The application was used to answer research questions: 
* How to make an efficient game engine?, 
* How to integrate a user-friendly GUI?, 
* How does the step size in euler method influence accuracy?. 


The GUI displays the function range between -25 to 25 for X and Y values and from -10 to 10 for the height value. The golf ball is not scaled, so it is visible better on the screen. For the GUI to display the function properly the user needs to change the return statement in the class **MainGame.java** in the method **calcHeight** to the desired function manually using Java language. 


The parameters:
* terrain (BiFunction)
* kinetic friction (double)
* static friction (double)
* initial X coordinate (double)
* initial Y coordinate (double)
* initial X velocity (double)
* initial Y velocity (double) 
* target radius (double) 
* target X coordinate (double) 
* target Y coordinate (double) 

The parameters have to be provided by the user. There are two possible ways to provide them to the application. 
1. The user can input the desired values in GolfGame/read.txt in a given order without changing parameters before "=" sign.
2. The user can input the desired values in the GUI panel.

The application implements the **Euler method** of approximation of the velocity to find the final position of the ball.


###VERSION or DATE:

Version 1, 17/03/2022

###THE REPOSITORY CONTAINS:

The main class called **Runner.java** to run (**EulerSolver.java**) and (**GUBox.java**) using multithreading.<br />
The classes needed to run all the GameEngine to visualize the movement and allow the user to play.<br />
A class **Physics.java** to calculate acceleration, velocity, and position of the ball and check if it is moving.<br />
A class **EulerSolver.java** to determine the final position of the ball.<br />

###HOW TO START THE PROJECT
The user needs to open the project as a Gradle project.
and needs to run the **Runner.java** class in GolfGame\desktop\src\com\mygdx\game\main\. 

###USER INSTRUCTIONS

After running the application the user will be able to decide either to input the desired parameters into the read.txt file or directly to the GUI panel.<br />
If the user wants to input parameters from a text file then they have to simply input the values to the right file. 
If the user wants to input parameters from a GUI panel they will be asked in the terminal to input a single character 'Y'. After that, they will be able to input values to GUI.
If the user picked the method of inputting the parameters into the text file then all the velocities have to be provided in the beginning.
If the user picked the method of inputting the parameters into the GUI panel directly, they input each velocity one by one and "PUTT!" button to confirm the initial velocity.
The algorithm calculates the position of the ball in time increments and shows the movement in the GUI.<br />
To exit the program press X on the top corner or from the open Window. (side depends on the operating system)

###AUTHORS
Liwia Padowska, Adriana Purici, Oístin Rutledge, Kumar Sambhavit, Saman Sarandib, Amelia Sasin, Matei Țurcan;
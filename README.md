#Crazy Putting: Phase 3

###PURPOSE OF THE PROJECT:

The purpose of the project is the implementation of a physics engine for 
the golf ball movement depicted on a GUI and turning it into a simulation. The
GUI is a two-dimensional representation of a three-dimensional function.
It represents different heights with different colors, shown on a legend. 
The application was used to answer research questions:

* In terms of accuracy and computational efficiency, how do the Euler, Runge-Kutta 2, and 4 ODE solvers compare?
* How does changing a physical description from an acceleration approximation that does not take into consideration slopes to a more complex one influence the model of ball’s motion?
* How does introducing a small random error in the initial position and velocity of the ball affect the performance of Hill Climbing, A* Pathfinding and Advanced Brute force algorithms, as measured in the amount of iterations required to complete the simulation?


The GUI displays the function range between -25 to 25 for X and Y 
values and from -10 to 10 for the height value. The golf ball is not scaled,
so it is visible better on the screen.

For the GUI to display the function properly the user needs to change 
the Bifunction in *Runner.java* on line 227.

The parameters inside DataField.java:
* AI running (boolean)
* GUI (boolean)
* Using GUI (boolean)
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

The parameters have to be provided by the user. There are two possible 
ways to provide them to the application.
1. The user can input the desired values in GolfGame/read.txt in a given 
2. order without changing parameters before "=" sign.
3. The user can input the desired values in the GUI panels.

The application implements the **solvers package** containing three 
implemented solver classes with the purpose of approximation of the 
velocity to find the final position of the ball.


###VERSION or DATE:

Version 1.2, 20/06/2022


###THE REPOSITORY CONTAINS:

10 packages for modularity inside GolfGame\src\com\mygdx\game\main\.<br />

A package **physics** which contains all the classes needed to calculate
acceleration, velocity, and position of the ball and check if the ball has stopped.<br />
A package **solvers** which contains three implemented solvers to 
determine the final position of the ball.<br />
A package **obstacles** which contains the classes and interfaces 
required to create said obstacles and check if the ball collides with them.<br />
A package **engine** which contains all the solvers implemented 
used to calculate and approximate the ball's position while running the simulation.<br />
A package **bots** which contains classes needed to run the AI bot and 
additional solver classes adapted from the **solvers** package made to work with the AI.<br />
A package **hillClimbingAi** containing three classes required to run the Hill Climbing AI.<br />
A package **bruteForce** containing three classes required to run the brute force bot.<br />
A package **noise** containing a class that aids in random noise generation.<br />
A package **com.mygdx.game.main** which contains simulation, pre-simulation menu and the simulation menu.<br />
elements as well as the graphical rendering of all simulation elements.<br />
A package **music** containing the two classes required to add a music input stream and a music control panel.<br />





###HOW TO START THE PROJECT

The user needs to open the project as a Gradle project.
After opening the Gradle project, the user needs to run the **Runner.java** class in GolfGame\desktop\src\com\mygdx\game\main\.


###USER INSTRUCTIONS

After running the application the user will be able to decide either to input the desired parameters into the read.txt file or directly to the GUI panel.<br />
If the user wants to input parameters from a text file then they have to simply input the values to the right file.
If the user wants to input parameters from a GUI panel they will be asked in the terminal to input a single character 'y'.<br />
After that, a pre-simulation menu will appear prompting the user to input values such as: kinetic and static friction, ball initial coordinates, hole coordinates and radius and sandpit coordinates, and the desired solver.
After all the values are set, by pressing the "Set!" button the simulation will begin.<br />

If the user wants to test any specific functions with predetermined values, they can do so by pressing: "Preset 1 (Sine hills)", "Preset 2 (Lake)", "Preset 3 (Big lake)" or "Preset 4 (Two hills)"
If the user picked the method of inputting the parameters into the text file then all the velocities have to be provided in the beginning.
If the user picked the method of inputting the parameters into the GUI panel directly, they input each velocity one by one and "PUTT!" button to confirm the initial velocity.

The Controls Menu contains 3 buttons regarding bots. Pressing one of the buttons will start the bot the button itself represents.<br />
For the Hill Climbing and Path-Finding bot, pressing their buttons once will trigger the algorithms and the bots will run the simulation.<br />
For the Brute Force bot, pressing the button will iterate the algorithm once. Therefore, one must press the button for each bot shot, until the algorithm reaches shoot the ball into the target and is then finished.<br />

To exit the program press X on the top corner or from the open Window. (side depends on the operating system)


###AUTHORS

Liwia Padowska, Adriana Purici, Oistín Rutledge, Kumar Sambhavit, Saman Sarandib, Amelia Sasin, Matei Țurcan;
Warhammer 40k List Builder

A list building program for the game Warhammer 40k. It is able to keep track of different units, their point costs, and allow you to organize their data sheets into a variety of different army configurations, including different detachments. The program allows you to save different lists to a file so that you can build and/or edit lists that are legal for the current version of Warhammer 40k



Installation Requirements: 

1. Java Development kit (JDK)
2. Integrated Development Environment (IDE)
3. SceneBuilder (JavaFX visual tool)
4. Source code provided


Configurations:

1. JDK version 20
2. SceneBuilder version 8.5.0
3. ApachePoi Jar files


File Structure:

(Project) --> Src Code and 3 .xls files

(File) --> Resources
	- Contains 3 .fxml files linked to each page developed with Scenebuilder
	- .jpg/.png images used for the program backgrounds

(File) --> Controllers
	- HarlequinsController: Main controller class for building the list
	- MainController: Main Page controller class
	- NL Controller: New list controller class

(File) --> dataModels
	- Contains 9 Object Classes for each unit
	- Each Class contains the PL / Pointcost associated with that unit
	- Contains the reference to its Role TitlePane and unit TitlePane
	- Contains unit upgrade selections
	- Contains a toString that is used for printing content to list

(File) --> Factories
	- ArmyAbilitiesFactory: Reads in data from the ArmyAbilities.xls and stores in a Map Object
	- DetachmentFactory: Reads in data from the Detachment.xls and stores in a Map Object
	- FactionFactory: Reads in data from the Factions.xls and stores in a Map object

(File) --> listbuilder
	- Main Application: Entry Point to the program
	- EventHandlers: Handles the initial user interaction events throughout the program. 
	- Utils: Contains helper methods.
		- Generating Unit TitledPanes
		- Generating Role TitledPanes
		- Generating Hbox's
		- Creating Faction/Detachment/ArmyAbilites Map
		- Switching Scenes
		- Keeping Track of Overall PL / Pointcost

	
Running the Program:

-In order to run the program, make sure all the Installation Requirements and Configurations are fulfilled. After your IDE is set up, open the listbuilder project inside your IDE. Then, Build the project and Run "Main Application". This should generate a pop up window of the listbuilder dekstop application. 



Navigating Through the List Builder Application Interface:

- When you run the program, you will be prompted to either create a "New List" or "Close"
	- "Close" button --> Will Close the Application
	- "New List" button --> Continue to next step

- If "New List" is Chosen, you will be prompted to select a Faction and select a Detachment in a drop down Menu. Both options must be selected in order to continue forward. Finally, click on the "Build Army" button in order to start creating your new army!

- Next, we arrive at the main page for building the list. This page is broken down into 3 Panes. The left pane contains the units that you want to add to your list. The middle Pane will contain the units that are currently on your list. The right pain will display upgradable options for each unit. Selecting those options will add them to the middle Pane, where your list resides.

Clicking the "+" sign, adds the unit of choice to the list. Clicking on the "X" sign, removes that unit from the list. After adding a unit using the "+" sign, navigate to the middle Pane and click on the unit name. Clicking on the unit pane will display upgradable options on the right side Pane. If you choose to select any of those options, the selections will appear under that unit in the middle Pane and adding it to the list. Meanwhile, the program keeps track of the power level (PL) and pointcost (pts) as your add or remove units/upgrades. The application will also display the specific number of units you currently have and disable you from adding more then allowed by the list building process. 

Once you are satisfied with your list, you could save the list by selecting File --> Save and following the generic saving procedure. Another option it by selecting File --> Close or utilizing the "X" at the top right corner. This will prompt you to either close the application without saving or save the list first and then close.



Further Development:

1.) Generate an exetuable jar file so you could launch your program from a desktop icon.
2.) Make use of the searchbar. Be able to search by unit role or name and only display the units on the left Pane that meet the search criteria.
3.) Load a previously saved list into the List Builder Application.



## Contributors:
- Vlad Shingarey
- Parker Groop
- Dawson Kline
- William Ahrendt



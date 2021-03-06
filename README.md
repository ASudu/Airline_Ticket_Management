# Airline Ticket Management

This is the repository for the course project in [CS F213: Object Oriented Programming, Fall 2021](https://academic.bits-pilani.ac.in/Faculty/FINAL_HANDOUT_FILES/CS_F213_1092.pdf).

## Authors

| Name | ID |
| --- | ----------- |
| A Sudarshan |2019B4A70744P |
| Rajan Sahu | 2019B4A70572P|

## Demo run

- Refer this [video](https://drive.google.com/file/d/1oRG4fnvuG2PhDWQDCuK1ea-9LhLfYV1a/view?usp=sharing)

## Description

- Login and account balance management implemented
- Basic flight booking, cancelling, viewing operations available for customer
- Staff management, airline management implemented
- Add flights, update flight status, etc are functionalities available to airline staff based on their designations
- Check-in, generate boarding pass and bag weight limit also implemented
- Additional Salient features:
    - [Password masking](https://scirge.com/glossary/password-masking): For security of the user
    - Logger: Records each action (such as login, sign-up, booking, etc.) of the user with timestamp and username (helps in keeping track of errors in case they arise)

## How to run this?

- JDK used: [16.0.2](https://www.oracle.com/java/technologies/javase/jdk16-archive-downloads.html)
- OS used: Windows 10
- Clone this repository in your system
- The problem statements folder contains
    - Question details
    - [UML](https://www.javatpoint.com/uml-class-diagram) class, sequence and use-case diagrams
- In command prompt (Windows) compile all java files using `javac *.java` and once it compiles, run main.java using `java Main`

## Important points while running the project

- As mentioned in [Project description](#Description), we have used password masking using the Console class of Java. ***This doesn't echo any character on the console but just takes input thus hiding what is being typed.***
- Each menu that takes user input to get re-directed to some other menu requires characters to be typed in uppercase (case-sensitive)
- Other assuptions:
    - 10 cities (including one Home city), each with 1 airport having just one terminal
    - Only 5 airlines
    - During check-in, if baggage weight exceeds 25kgs, the extra charges (of Rs.100 per extra kg) is assumed to be paid directly by user via cash/card, etc. and not via account balance

## Basic Modules

- This [document](https://github.com/ASudu/Airline_Ticket_Management/blob/main/Problem_statement/Modules%20of%20the%20project.pdf) has a clear explanation of working and requirements of each of the modules


## Guidelines for contributors

**Main branch shall only contain approved changes, do not push directly to it!**

- Create a branch on your local machine for each section of an article.
- Add comments so that the code is comprehendable to other contributors 
- Send PR for your branch once you start, you can keep adding commits to it later.
- Once everyone agrees, PRs will get merged(in order of sections).

## References

- https://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html
- https://stackoverflow.com/questions/8138411/masking-password-input-from-the-console-java
- https://www.hierarchystructure.com/hierarchy-of-airline-company/
- https://www.geeksforgeeks.org/java/





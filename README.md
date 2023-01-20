# My Personal Project

## The Academic management application
The purpose of this program is to manage people who engaged in university because there are a lot of students and professor at university.
For example, the science department needs to know every student and professor information.
Every student and every professor needs to know their department information such as address, email, and department number. 
The department needs to manage professors and students by viewing their information. 
Moreover, the department needs to know how many student or professor they have
Every student and professor can apply for graduation and retirement, and they are able to see the date when they graduated or retired

This program consists of three main parts. 
- This application can manage all models such as Department, Professor, and Student using Administration class.
- The department are available to see its information and have information regarding students and professors.
- The department can manage information of all students and professor


A *To-do* list:
- As a user, I want to be able to add and update department information
- As a user, I want to be able to add, update and delete a professor in department
- As a user, I want to be able to add, update and delete a student in department
- As a user, I want to be able to view detailed information of the specific student or professor.
- As a user, I want to be able to save all information to file regarding department, professors, and students.
- As a user, I want to be able to load all information from the file regarding department, professors, and students
- As a user, I want to be able to add multiple Professors and Students to a Department
- As a user, I wanted to be prompted with the option to load data from file when the application starts 
- As a user, I wanted to be prompted with the option to save data to file when the application ends

# Instructions for Grader

- I can generate the first required event related to adding professors and students to department by add button.
- I can generate the second required event related to managing professors and students in department by modify and delete button.
- I can locate my visual component by three image buttons that function department editing, data refresh, and exit. 
- I can save the state of my application by pop-up window that gives the user the option (using Yes/No buttons) to save it when the application ends.
- I can reload the state of my application by pop-up window that gives the user the option (using Yes/No buttons) to load data when the application starts

# Phase 4: Task 2
[CASE 1: When a user create new department and save information]
=============== EVENT LOG ===============
Wed Nov 30 15:53:25 PST 2022
create a department

Wed Nov 30 15:53:45 PST 2022
update the department

Wed Nov 30 15:53:49 PST 2022
add a professor from the department

Wed Nov 30 15:53:52 PST 2022
modified professor from the department

Wed Nov 30 15:53:53 PST 2022
made the professor retire from the department

Wed Nov 30 15:53:55 PST 2022
Remove professor from the department

Wed Nov 30 15:54:00 PST 2022
add a student from the department

Wed Nov 30 15:54:04 PST 2022
modified student from the department

Wed Nov 30 15:54:06 PST 2022
made the student graduate from the department

Wed Nov 30 15:54:08 PST 2022
Remove student from the department

Wed Nov 30 15:54:19 PST 2022
Save professors' information into the department through JSON

Wed Nov 30 15:54:19 PST 2022
Save students' information into the department through JSON

Wed Nov 30 15:54:19 PST 2022
Save department through JSON

=========================================

[CASE 2: When a user load existing department and save information]
=============== EVENT LOG ===============
Fri Dec 02 21:55:37 PST 2022
Load the department from JSON

Fri Dec 02 21:55:37 PST 2022
Add a professor from the department

Fri Dec 02 21:55:37 PST 2022
Add a professor from the department

Fri Dec 02 21:55:37 PST 2022
Add a professor from the department

Fri Dec 02 21:55:37 PST 2022
Load professors into the department from JSON

Fri Dec 02 21:55:37 PST 2022
Add a student from the department

Fri Dec 02 21:55:37 PST 2022
Add a student from the department

Fri Dec 02 21:55:37 PST 2022
Load students into the department from JSON

Fri Dec 02 21:55:44 PST 2022
Add a professor from the department

Fri Dec 02 21:55:48 PST 2022
Modified professor from the department

Fri Dec 02 21:55:49 PST 2022
Made the professor retire from the department

Fri Dec 02 21:55:51 PST 2022
Modified professor from the department

Fri Dec 02 21:55:57 PST 2022
Add a student from the department

Fri Dec 02 21:55:59 PST 2022
Modified student from the department

Fri Dec 02 21:56:01 PST 2022
Made the student graduate from the department

Fri Dec 02 21:56:03 PST 2022
Remove student from the department

Fri Dec 02 21:56:08 PST 2022
Save professors' information into the department through JSON

Fri Dec 02 21:56:08 PST 2022
Save students' information into the department through JSON

Fri Dec 02 21:56:08 PST 2022
Save department through JSON

=========================================

# Phase 4: Task 3
1. Object utilization methods using abstract class was not properly applied. If I create and save a List<Person> apparent type without 
   creating separate list of students and professor in the department class, I can minimize duplicate tasks. (refactoring)

2. There are methods that have different method names but play the same role in the professional and student classes inherited from the abstract class.
   Likewise, since this is a code implementation of duplicate tasks, the same task can be performed by implementing them within the abstract class.
   However, the method name should be unified.

3. In configuring the UI, there are tasks that the control position must be placed in the unique position while configuring the same Java Swing controls in the same shape.
   In addition, when defining the event method, some methods are similar to others. 
   This situation also seems to be easier to read and write lightweight code if code is simplified by refactoring.

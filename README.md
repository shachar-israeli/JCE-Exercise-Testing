# JCE-Exercise-Testing
The system was built to make the college exercise testing much faster and easier

how to use:

1.run RARToZip.py to convert a directory of rar and zip files to only zip files.
(RAR license does not allow to include it into software development tools like Java SDK)

2.run unzip_files.java:

*this method is in charge to give the tester the files he needs.
*extract the files
*wait until the user press 'enter' ( mean we finish checking the tester)
*move the zip file to the 'finished' directory
*give the tester new exercise of a new student

3.Tester.java:

in diffrent console, we run the Tester file.
*make your own test. use the comments and grade attributes to give the score to every student
*when the test is finished, this method will write the results to an exel file ( The file needs to be prepared before - first row: a list of id's of the students)

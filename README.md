# TP1 CAR

## Authors :
	Kevin FRANQUENOUILLE
	Damien CORNETTE
	
## Structure
 - doc/ for the documentation
 - src/ which includes the src files and tests
 - README.txt 
 - diagram.svg
 - FTP_server.jar
	
## How to start the FTP server :
	java -jar FTPserver.jar
	
	The current directory is "/"
	
## When you compile, there is no warnings. The server works with the FTP client in the shell, Mozilla Firefox and Filezilla (we have use the EPLF https://filezilla-project.org/specs/eplf.html).
	
## Tests :
 - YOU NEED TO USE A LINUX OS
 - you need to create a folder using root named "locked" in the directory /tmp/ (in order to not remove some system files)
 
## Not implemented : 
 - STOR
 - LIST
 - RETR
 - PORT
 - PASV
 
## To connect, your can use theses accounts :
 - user : test		pass : test
 - user : damdam	pass : damdam
 - user : anonymous
 
We can connect 5 users at the same time for example. However, we have implemented the passive mode and the anonymous mode.

## Working clients :
 - FTP in the shell
 - Filezilla
 - Mozilla Firefox

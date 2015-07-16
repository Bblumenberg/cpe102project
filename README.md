# cpe102project

This is the completed final project which I developed over the course of my CPE-102 class at Cal Poly SLO. It uses processing to display the world, using images provided by the instructor. 

The original program was given to me written in python at the begginning of the class and was completely translated to java and built upon by me.

To run the program, download all files, compile and run ProcessWorld.java using java8. The basic idea is that the miners move around and collect ore which spawns from veins (they look like rocks). Once they've collected a sufficient number of ore peices (2 to start) the miners make their way to a blacksmith where they drop off the ore. If a piece of ore sits too long without being collected it will become a blob and roam the world destroying veins. All moving entities use an A* pathing algorithm implemented by me as part of the assigment.

With no user interaction the blobs eventually destroy all the veins, and the world stops. To see user interaction which was added as the final part of the project, please see the WorldEvent.txt file.

You may use the WASD keys to move the view of the world, or use the 'big' option when running the program to view the whole world at once (from the command line run without quotes "java ProcessWorld big").

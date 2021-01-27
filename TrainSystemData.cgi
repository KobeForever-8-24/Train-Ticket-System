#!/bin/bash

# --== CS400 File Header Information ==--
# Name: Isaac Colbert
# Email: icolbert@wisc.edu
# Team: CG
# Role: Front End Developer 1
# TA: Yeping Wang
# Lecturer: Florian Heimerl
# Notes to Grader: Please ensure that this along with all other files 
# are withing the /html-s/ directory.

echo "Content-type:text/html"
echo ""
javac -source 1.7 -target 1.7 -cp . TrainSystemData.java 
java -cp . TrainSystemData

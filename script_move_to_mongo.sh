#!/usr/bin/env bash

echo "Script is executing"
if [ $2 = 1 ]
then
	echo $1 > ~/rawOutput.txt

	cd ~/

	java -jar move_to_mongo.jar $RAWOUTPUT "test_database"
fi
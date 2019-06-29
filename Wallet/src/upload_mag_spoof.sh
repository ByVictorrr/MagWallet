#!/bin/bash
track1=$1
track2=$2
file=${/home/victor/VictorsWallet/Wallet/src/magspoof/magspoof.c}
# Step 1 - edit the contents of the c code to unparsed data in java code
# https://stackoverflow.com/questions/14643531/changing-contents-of-a-file-through-shell-script
#Step 1.1 - replace track 1
sed -i 's/tracks[] = {.*/}' $file
Step 1.2 - replace track 2
sed -i 's/tracks[] = {.*/}' $file
# Step 2 - upload the firmware to the attiny
arduino --upload $file --port /dev/ttyACM0

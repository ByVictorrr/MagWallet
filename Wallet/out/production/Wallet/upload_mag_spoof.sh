#!/bin/bash
file="/home/victor/VictorsWallet/Wallet/src/magspoof/magspoof.c"
# Step 1 - edit the contents of the c code to unparsed data in java code
# https://stackoverflow.com/questions/14643531/changing-contents-of-a-file-through-shell-script
#Step 1.1 - replace track 1 and 2
sed -i "32s/\".*\?\"/$1/" "$file"
# Step 2 - upload the firmware to the attiny
# arduino --upload $file --port /dev/ttyACM0

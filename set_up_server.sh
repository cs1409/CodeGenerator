#!/usr/bin/env bash

cd /tmp
sudo wget https://www.multichain.com/download/multichain-2.0-alpha-2.tar.gz
sudo tar -xvzf multichain-2.0-alpha-2.tar.gz
cd multichain-2.0-alpha-2/
sudo mv multichaind multichain-cli multichain-util /usr/local/bin

sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2930ADAE8CAF5059EE73BB4B58712A2291FA4AD5
echo "deb [ arch=amd64 ] https://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.6 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.6.list
sudo apt-get update
sudo apt-get install -y mongodb-org

sudo apt-get update
sudo apt-get install default-jdk


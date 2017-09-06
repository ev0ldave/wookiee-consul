#!/bin/bash
export DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd $DIR

if [[ ${TRAVIS_PULL_REQUEST}  == "" ]]; then

 # manage the docker container for etcd
 docker-compose stop
 docker-compose rm --force
 docker-compose up -d etcd

else
 # remove when travis is happy with docker
 exit 0

 # when using travis-ci we need to run within User Linux Mode.
 curl -sLo - http://j.mp/install-travis-docker | sh -xe
 # start docker in a process and nohup it
 nohup ./run "docker-compose up -d consul && docker-compose ps && date && touch docker.started" &

 # wait for it to start no more than 20 sec
 i="0"
 while [ ! -f docker.started ] && [ $i -lt 4 ]
 do
  echo "Waiting for docker to start..."
  sleep 5
  i=$[$i+1]
 done
 #save the pid of so we can kill it.
 echo $! > consul.pid
fi
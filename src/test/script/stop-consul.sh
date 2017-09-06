#!/bin/bash
export DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd $DIR
# are we running in travis-ci?
if [[ ${TRAVIS_PULL_REQUEST}  == "" ]]; then

 # manage the docker container
 docker-compose stop
 docker-compose rm --force

else
 # remove when travis is happy with docker
 exit 0

 # running in travis-ci and stop and remove the pid
 kill `cat consul.pid`
 rm -f docker.started consul.pid
 echo "Shutdown docker.."
fi

#!/bin/bash

# Website URL
URL=http://localhost:8080/Project-One/index

printf "Cleaning repo..."
# First, we clean the repo
mvn -f app/pom.xml clean --quiet

printf "done !\nBuilding WAR file (Without Arquillian testing)..."
# Then, we run the package target without arquillian, so that we can get a war file
mvn -f app/pom.xml package -DNoArquillian --quiet


printf "done !\nRebuilding prod docker environment (takes up to a minute)...\n"
# Secondly, we stop and rebuild the dev docker environment (as well as stop the prod environment, for port collision safety)
# (also, when testing, and not in prod, we don't copy the 1Mil entries, so that the tests actually work)
docker-compose -f docker/topologies/amt-projectOne-dev/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectOne-prod/docker-compose.yml --log-level ERROR down >/dev/null
docker-compose -f docker/topologies/amt-projectOne-prod/docker-compose.yml --log-level ERROR up --build -d

printf "Website deployment in progress..."

a=0

until $(curl --output /dev/null --silent --head --fail $URL);
do
    sleep 1
    a=$((a+1))
    if [[ $a -ge 60 ]]
    then
        break;
    fi
done

if [[ $a -ge 60 ]]
then
    printf "Failed !\n"
else
    printf "Done !\n Website is available at : $URL"
fi



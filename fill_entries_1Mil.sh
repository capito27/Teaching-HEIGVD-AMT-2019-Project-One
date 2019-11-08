#!/bin/bash


rm -f gen_data.sql

echo "use \`amt\`;" >> gen_data.sql

TeamCount=10
UserCount=10
StadiumCount=10
matchCount=1000000


OUTPUT=""
# TEAM GENERATION
for (( i=1; i<=$TeamCount; i++ ))
do
    if [ $i -eq 1 ]
    then
        OUTPUT+="INSERT INTO \`team\` (\`team_name\`, \`team_country\`) VALUES "
    fi
     OUTPUT+="('Team$i', 'Switzerland')"
    if [ $i -eq $TeamCount ]
    then
        OUTPUT+=";"
    else
        OUTPUT+=","
    fi
done

#echo $OUTPUT >> gen_data.sql
OUTPUT=""

# USER GENERATION
# default password : password
for (( i=1; i<=$UserCount; i++ ))
do
    if [ $i -eq 1 ]
    then
        OUTPUT+="INSERT INTO \`user\` (username, first_name, last_name, password, email) VALUES " 
    fi
    OUTPUT+="(\"user$i\", \"user\", \"name\", \"5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8\", \"admin@mail.co\")" 
    if [ $i -eq $UserCount ]
    then
        OUTPUT+=";"
    else
        OUTPUT+=","
    fi
done

#echo $OUTPUT >> gen_data.sql
OUTPUT=""

# STADIUM GENERATION
for (( i=1; i<=$StadiumCount; i++ ))
do
    if [ $i -eq 1 ]
    then
        OUTPUT+="INSERT INTO \`stadium\` (stadium_name, stadium_location, stadium_viewer_places) VALUES " 
    fi
    OUTPUT+="(\"stadium$i\", \"Switzerland\", 68)" 
    if [ $i -eq $StadiumCount ]
    then
        OUTPUT+=";"
    else
        OUTPUT+=","
    fi
done

#echo $OUTPUT >> gen_data.sql
OUTPUT=""

# MATCH GENERATION

for (( i=1; i<=$matchCount; i++ ))
do
    if [ $i -eq 1 ]
    then
        OUTPUT+="INSERT INTO \`match\` (score1, score2, FK_team1, FK_team2, FK_stadium, FK_user) VALUES " 
    fi
    OUTPUT+="(1,1,1,1,1,1)" 
    if [ $i -eq $matchCount ]
    then
        OUTPUT+=";"
    else
        OUTPUT+=","
    fi
    if [ $((i % 100)) -eq 0 ]
    then
        echo $OUTPUT >> gen_data.sql
        OUTPUT=""
    fi
done

echo $OUTPUT >> gen_data.sql

echo "generation done"

# once the db_init .sql is ready, we spawn a db docker, so that the initial DB creation may take place
docker-compose up --build -d db


echo "waiting for sql instance to be available"

# wait for the mysql instance to be up
while ! mysqladmin -h"127.0.0.1" -P 3333 ping --silent; do
    sleep 1
done

echo "sql instance available, running SQL transaction"
# now, we simply run the SQL file

#mysql -u root -proot -h"127.0.0.1" -P 3333 < gen_data.sql

echo "done!"


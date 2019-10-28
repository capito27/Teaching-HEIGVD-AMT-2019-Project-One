#!/bin/bash

# check that mysql client is installed, otherwise we can't do it
hash mysql 2>/dev/null || { echo >&2 "Mysql client is required, but can't be found.
It can be installed on ubuntu and similar with the command \"sudo apt-get install mysql-client\"
Aborting."; exit 1; }


rm -f gen_data.sql

echo "use \`amt\`;" >> gen_data.sql

TeamCount=10
UserCount=10
StadiumCount=10
matchCount=1000

NL=$'\n'

OUTPUT=""


for (( i=1; i<=$TeamCount; i++ ))
do
    echo "INSERT INTO \`team\` (\`team_name\`, \`team_country\`) VALUES ('Team$i', 'Switzerland');" >> gen_data.sql
done

# default password : password
for (( i=1; i<=$UserCount; i++ ))
do
   echo "INSERT INTO \`user\` (username, first_name, last_name, password, email) VALUES (\"user$i\", \"user\", \"name\", \"5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8\", \"admin@mail.co\");" >> gen_data.sql
done

for (( i=1; i<=$StadiumCount; i++ ))
do
   echo "INSERT INTO \`stadium\` (stadium_name, stadium_location, stadium_viewer_places) VALUES (\"stadium$i\", \"Switzerland\", 68);" >> gen_data.sql
done

for (( i=1; i<=$matchCount; i++ ))
do
    s1=$((i % 13 + 1))
    s2=$((i * 7 % 13 + 1))
    t1=$((i % $TeamCount + 1))
    t2=$((i * 13 % $TeamCount + 1))
    s=$((i * 11 % $StadiumCount + 1))
    u=$((i / 5 % $UserCount + 1))
    echo "INSERT INTO \`match\` (score1, score2, FK_team1, FK_team2, FK_stadium, FK_user) VALUES ($s1,$s2,$t1,$t2,$s,$u);" >> gen_data.sql
done

# once the db_init .sql is ready, we spawn a db docker, so that we may insert the values into it
docker-compose up --build -d db

# wait for the mysql instance to be up
while ! mysqladmin ping -h"127.0.0.1" -P 3333 --silent; do
    sleep 1
done

# now, we simply run the SQL file

mysql -u root -proot -h"127.0.0.1" -P 3333 < gen_data.sql

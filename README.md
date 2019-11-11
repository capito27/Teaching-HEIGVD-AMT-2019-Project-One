# Teaching-HEIGVD-AMT-2019-Project-One
## Project description
This project is a simple MVC project, implemented purely using JAVA EE, in the context of the AMT Course, followed at HEIG-VD. 

The various aspects of JAVA EE used are as follows :
- Servlets (used as the View component of MVC)
- Entreprise Beans (Used as the Controller component of MVC)

The purpose of this project was to introduce students to the JAVA EE environment, at a deep level, without using a framework, such as Spring, to simplify it's usage.

## Project dependencies
To run this project, you MUST have the following installed : 
- maven
- docker-compose

To test this project, along with the run requirements, you MUST have the following installed :
- java 1.8 (it MUST be the 1.8 version of the JRE, to be able to run the Integration tier testing)

This project depends on the following datasources being available within payara : 
- jdbc/app

## How to manually build this project
This project is distributed as a Web Archive file (.war).
To build the project, users MUST have Maven installed.

To build this project, users MUST run either of the following commands (in the app directory) :
- `mvn clean pacakge -DskipTests`, which will entirely skip tests, and produce the .war file in the target directory
- `mvn clean package -DNoArquillian`, which will only disable the Integration tier testing, due to requiring, among many requirements, a .war file to already be built.

## How to test this project
Due to the Integration tier testing depending on both having a .war file built, and the dockerised dev envirement to be available, the project may be tested using the `runTests.sh` script, found at the root of the project.

Please note that due to the Integration tier testing depending on the Payara5 webserver, users MUST import the Payara5 webserver's self signed certificate into their Java's trusted certificate database. Said certificate may be found in the `ssl` directory.

To import the certificate, users may use the `import.sh` script, found alongside the certificate.

This script is only supported on *NIX systems, and you may need to change the `JRE_DIRECTORY` variable to point to your JRE directory.

Along with unit testing, mock testing and integration testing, this script also generates a code coverage report, with the help of `jacoco`.
Said report may be found here : `app/target/site/jacoco/index.html`

## How to run this project
To run this program, due to depending on a specific dockerised environment, you MUST run it from the `deploySite.sh` script, found at the root of the project.

The script will build the project, deploy the dockerized infrastructure and the website, along with a default dataset of over 1 million entries.

Once the script is done, it will print the website URL, which can be accessed from any browser.

NB : at the time the website URL is displayed, the sql database may not be available yet, as such you may receieve empty pages for 10-20 seconds after receiving the website URL.

## Report

### Implemented features

A list of implemented features may be found at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/implemented.md).

### Implementation details

The details of our implementation may be found at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/implementDetails.md).

### Testing strategy

Our testing strategy is explicited at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/TestStrategy.md).

### Performance experiments

The results of our experiments about data paging may be found at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/PerformanceTest.md).

### Known bugs and limitations

Our lists of our know bugs and limitations may be found at [the following link](https://github.com/capito27/Teaching-HEIGVD-AMT-2019-Project-One/blob/master/docs/KnownBugs.md).

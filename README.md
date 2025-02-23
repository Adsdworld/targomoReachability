# Targomo reachability

Project created using Maven 3.9.9, powered by Java 17.

Imagine you are a store manager with 10 stores in Berlin. You want to transform one store to the central storage unit. A central storage unit can only be a store which has the lowest total travel time to all other stores, i.e. the most central store. You should now implement a Java program (with Maven) which calculates the travel time for each store to all other stores and sorts all stores by their total travel times to all other stores. Additionally, you should also calculate the total distance between each one and all other stores in meters (bee line). Please keep the instruction in mind that we've already sent you in last email.

### Code logic
* Extract store.json
* Create a List of Store (id, longitude, latitude)
* Request targomo api
* Sort and display results for time and distance

### Ouput
Stores sorted by travel time:
* Store [point 7] has total travel time of: 2826 s
* Store [point 6] has total travel time of: 3095 s
* Store [point 5] has total travel time of: 3276 s
* Store [point 1] has total travel time of: 3473 s
* Store [point 2] has total travel time of: 3484 s
* Store [point 8] has total travel time of: 3637 s
* Store [point 9] has total travel time of: 4017 s
* Store [point 3] has total travel time of: 4050 s
* Store [point 10] has total travel time of: 4405 s
* Store [point 4] has total travel time of: 4777 s

Stores sorted by distance:
* Store [point 5] has total distance of: 21110 m
* Store [point 1] has total distance of: 22815 m
* Store [point 8] has total distance of: 24208 m
* Store [point 7] has total distance of: 25780 m
* Store [point 9] has total distance of: 26674 m
* Store [point 3] has total distance of: 27357 m
* Store [point 6] has total distance of: 28471 m
* Store [point 4] has total distance of: 28873 m
* Store [point 2] has total distance of: 29602 m
* Store [point 10] has total distance of: 55827 m

## Run with intelliJ

### Create new Maven configuration

````
*RUN*
clean install exec:java
````

## Run with windows

### cmd

````
mvn clean install exec:java
````

### Maven settings.xml if blocked error :
````xml
https://gist.github.com/vegaasen/1d545aafeda867fcb48ae3f6cd8fd7c7
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 http://maven.apache.org/xsd/settings-1.2.0.xsd">
    <mirrors>
        <mirror>
            <id>maven-default-http-blocker</id>
            <mirrorOf>external:dont-match-anything-mate:*</mirrorOf>
            <name>Pseudo repository to mirror external repositories initially using HTTP.</name>
            <url>http://0.0.0.0/</url>
            <blocked>false</blocked>
        </mirror>
    </mirrors>
</settings>
````
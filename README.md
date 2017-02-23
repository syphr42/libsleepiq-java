# SleepIQ API for Java [![Build Status](https://travis-ci.org/syphr42/libsleepiq-java.svg?branch=master)](https://travis-ci.org/syphr42/libsleepiq-java)

## What is SleepIQ?

SleepIQ is a service provided by Select Comfort and sold as an option for Sleep Number beds. The system collects data about the bed (including individual air chamber data for dual chamber beds). This information includes whether or not a sleeper is in bed, the current sleep number setting, the pressure of the air chamber, and it's link status. This data can then be analyzed for any number of purposes, including improving sleep.

## Usage

First, create an instance:
```java
SleepIQ sleepiq = SleepIQ.create(new Configuration().withUsername("username")
                                                    .withPassword("password"));
```

Next, pull data:
```java
List<Sleeper> sleepers = sleepiq.getSleepers();
System.out.println(sleepers);

List<Bed> beds = sleepiq.getBeds();
System.out.println(beds);
for (Bed bed : beds)
{
    System.out.println(sleepiq.getPauseMode(bed.getBedId()));
}

FamilyStatus familyStatus = sleepiq.getFamilyStatus();
System.out.println(familyStatus);
```

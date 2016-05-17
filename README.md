# _Band Tracker_

#### _Java Solo Project for Epicodus 5.13.2016_

#### By _**Luca Quatela**_

## Description

_It allow the user to create a database of bands and venues.
The user can associate venues with band to keep track of the venues in which the bands have played.
Bands can be delited and their names can be modified._

## Setup/Installation Requirements

* _clone the following directory: https://github.com/LucaIta/band_tracker.git
* _run the Spark Server
* _restore the database from the file hair_salon.sql, or create a database by following the steps below:

* _CREATE DATABASE band_tracker;
* _\c band_tracker;
* _CREATE TABLE bands (id serial PRIMARY KEY, name varchar, band_size int);
* _CREATE TABLE venues (id serial PRIMARY KEY, name varchar, max_band_size int);
* _CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int);
* _CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;

* _open your browser at the page http://localhost:4567/

## Known Bugs
* in the band detail page, when no Venue is selected and the submit button is clicked, the error page is displayed


## Support and contact details

_contact Luca in case of you have trouble with the page_

## Technologies Used

_Java, Velocity, Spark, JUnit, FluentLenium, Postgres_

### License

*This software is licensed under the MIT license*

Copyright (c) 2016 **_Luca Quatela_**

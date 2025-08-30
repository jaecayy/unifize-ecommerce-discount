# Unifize E-commerce Discount Service

This is a **Java Spring Boot service** for calculating discounts on products in a shopping cart. It supports **brand-based**, **category-based**, and **voucher-based** discounts.  

## Document : This document contains details related to the Complete Project (like Project Flow, Architecture, Code Quality , Features , Assumptions, Output etc)
https://docs.google.com/document/d/1-u5-BpeZkBMzD1ZkfAilqafMZL-vMK_tYrkSsgor9_E/edit?usp=sharing

The project is fully **Dockerized**, so it can be run or tested without installing Java or Maven locally.

## Features

- Brand-specific discounts (e.g., PUMA, LEVIS, NIKE etc)  
- Category-specific discounts (e.g., T-SHIRT, JEANS etc)  
- Voucher code discounts  
- Payment-method based discounts  
- Integration and unit tests included
  

## Prerequisites

- Docker installed and running
  

## Docker Images : 
- jiteshkhatri/unifize-discount-app:latest  :  to run the applicaiton
- jiteshkhatri/unifize-app-test:latest      :  to run the tests

### Run the Application

## Use below command to pull the image : 
docker pull jiteshkhatri/unifize-discount-app:latest

## for running the application run below command :
docker run --rm -it jiteshkhatri/unifize-discount-app:latest


### Run the Tests

## Use below command to pull the image : 
docker pull jiteshkhatri/unifize-app-test:latest

## for running the application run below command :
docker run --rm -it jiteshkhatri/unifize-app-test:latest ./mvnw test



### DEVELOPMENT SETUP : 

## SHOULD HAVE JAVA 17
## SHOULD HAVE SUPPORT OF LOMBOK IN YOUR IDE

## clone the repository
git clone https://github.com/jaecayy/unifize-ecommerce-discount.git

## move into the downloadable folder
cd unifize-ecommerce-discount

## clean the package
./mvnw clean package

## to run the application
./mvnw spring-boot:run

## to run the tests
./mvnw test

## Check the document for the Project Flow, Architecure , Code and Assumptions related stuff : 

https://docs.google.com/document/d/1-u5-BpeZkBMzD1ZkfAilqafMZL-vMK_tYrkSsgor9_E/edit?usp=sharing






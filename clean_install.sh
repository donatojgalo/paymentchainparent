#!/bin/bash

log() {
    echo $(date +"%Y-%m-%d %H:%M:%S") "[$1] $2"
}

clean_install() {
    log "INFO" "Clean & install $1"
    cd $1
    mvn clean install
    cd ../..
}

clean_install bussinessdomain/customer
clean_install bussinessdomain/product
clean_install bussinessdomain/transaction
clean_install infrastructuredomain/adminserver
clean_install infrastructuredomain/configserver
clean_install infrastructuredomain/eurekaserver

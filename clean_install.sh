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

clean_install businessdomain/customer
clean_install businessdomain/product
clean_install businessdomain/transaction
clean_install infrastructuredomain/adminserver
clean_install infrastructuredomain/configserver
clean_install infrastructuredomain/eurekaserver

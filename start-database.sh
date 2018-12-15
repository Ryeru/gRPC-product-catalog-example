#!/bin/bash
sudo docker pull postgres
sudo docker run --rm --name webshop -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=webshop -d -p 5432:5432  postgres

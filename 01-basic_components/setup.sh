#!/bin/bash

whaoami=$(whoami)
first_char="$(printf '%s' "$whaoami" | cut -c1)"
last_name=$(echo $whaoami | cut -d '.' -f2)
short=$first_char$last_name-workshop

oc new-project $short


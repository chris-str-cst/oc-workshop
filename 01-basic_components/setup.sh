#!/bin/bash

# Check if you're logged in first
if [[ "$(oc whoami 2> /dev/null)" == "" ]]; then
  echo "You're not logged in :("
  exit 1
fi

whaoami=$(whoami)
first_char="$(printf '%s' "$whaoami" | cut -c1)"
last_name=$(echo $whaoami | cut -d '.' -f2)
short=$first_char$last_name-workshop

oc new-project $short


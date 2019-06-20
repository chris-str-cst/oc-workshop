# Chained builds
https://blog.openshift.com/chaining-builds/

**You need to finish 03-okd-s2i first to make this example work**
```bash
# Generated artifact is located in /wildfly/standalone/deployments/ROOT.war
oc new-build \
  --name=runtime \
  --docker-image=fabric8/java-alpine-openjdk8-jdk:latest \
     --source-image='spring-boot:latest' \
     --source-image-path=/deployments/gs-spring-boot-0.1.0.jar:. \
     --dockerfile=$'FROM fabric8/java-alpine-openjdk8-jdk:latest\nCOPY /gs-spring-boot-0.1.0.jar /deployments/app.jar'


oc logs -f bc/runtime # get logs from build

# set trigger to update runtime automatically if a new spring-boot:latest exists
oc set triggers bc/runtime --from-image=spring-boot:latest

oc new-app --image-stream runtime --allow-missing-imagestream-tags

oc start-build  spring-boot-dev # now triggers also the downstream job and updates the dc
oc describe is # check out the details about the image streams
```


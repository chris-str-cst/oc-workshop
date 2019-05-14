# Docker

## Mutlistage 

```bash
cd docker-multistage
IMAGE_NAME=spring-test

docker build $IMAGE_NAME .
docker build -t ${IMAGE_NAME}:runtime-alpine --target runtime-alpine .
docker build -t ${IMAGE_NAME}:debug --target debug .


TAG_TO_RUN=runtime-alpine
docker run --rm -p 8080:8080 ${IMAGE_NAME}:${TAG_TO_RUN}
```

## Docker basics

### COPY VS ADD
"Although ADD and COPY are functionally similar, generally speaking, COPY is preferred. That’s because it’s more transparent than ADD. COPY only supports the basic copying of local files into the container, while ADD has some features (like local-only tar extraction and remote URL support) that are not immediately obvious. Consequently, the best use for ADD is local tar file auto-extraction into the image, as in ADD rootfs.tar.xz /."

## Entrypoint https://docs.docker.com/engine/reference/builder/#entrypoint
`--init # https://github.com/krallin/tini`
## ENTRYPOINT VS. CMD
https://docs.docker.com/engine/reference/builder/#entrypoint


Further resoruces
- https://medium.com/@tonistiigi/advanced-multi-stage-build-patterns-6f741b852fae
- https://docs.docker.com/develop/develop-images/dockerfile_best-practices/

# Pattern 
![pattern](https://devopedia.org/images/article/122/7070.1538988426.jpg)
- Single node multi container: sidecar, ambassador, adapter
- Multi node: leader election, work queue, scatter/gather

## sidecar
https://github.com/openshift/oauth-proxy/blob/master/contrib/sidecar.yaml
# Openshift
https://github.com/openshift-labs/devops-guides/tree/ocp-3.9
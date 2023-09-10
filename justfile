set dotenv-load

default: run

run:
    ./gradlew -t run

build:
    gcloud builds submit -t $CLOUD_REGION-docker.pkg.dev/$PROJECT_ID/containers/$CONTAINER_NAME:v1

deploy: build
     gcloud run deploy bedtimestories --image=$CLOUD_REGION-docker.pkg.dev/$PROJECT_ID/containers/$CONTAINER_NAME:v1
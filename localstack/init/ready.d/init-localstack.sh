#!/bin/bash
set -e

awslocal dynamodb create-table \
  --table-name feedbacks \
  --attribute-definitions \
    AttributeName=id,AttributeType=S \
    AttributeName=periodWeek,AttributeType=S \
    AttributeName=createdAt,AttributeType=S \
  --key-schema \
    AttributeName=id,KeyType=HASH \
  --provisioned-throughput \
    ReadCapacityUnits=5,WriteCapacityUnits=5 \
  --global-secondary-indexes \
    'IndexName=periodWeek-createdAt-gsi,KeySchema=[{AttributeName=periodWeek,KeyType=HASH},{AttributeName=createdAt,KeyType=RANGE}],Projection={ProjectionType=ALL},ProvisionedThroughput={ReadCapacityUnits=5,WriteCapacityUnits=5}'

awslocal sqs create-queue --queue-name report-queue
awslocal sqs create-queue --queue-name notification-queue

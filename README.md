# nowait-reactive

Reactive api application with
   - API gateway
   - Eureka load balancing
   - 2 microservices

To run:
    1. docker run --rm -p 27017:27017 --name mongodb mongo:4.0.4
    2. start 'registry-service'
    2. start 'business-service'
    2. start 'person-service'
    2. start 'api-gateway'
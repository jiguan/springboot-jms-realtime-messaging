# Realtime messaging
A simple project demonstrates the integration of spring boot, activemq, camel and stomp. 

### How it works 
 1. When a user logs in, stompClient sends a message to `/messaging`. The controller (`WebSocketController`) captures the request and notifies all users that a new user just joined in. 
 2. Meanwhile, the stompClient subscribes `/topic/users` channel to get all online user list. Here `/topic` matches `config.enableSimpleBroker("/topic")`. Subscribe `/user/topic/messages` channel to receive new messages.
 3. When a user sends a message, stompClient sends the message to `/app/messaging`. Here, `/app` matches `config.setApplicationDestinationPrefixes("/app")` and `/messaging` matches `@MessageMapping("/messaging")`.
 4. ActiveMq handles the message over to Camel `from("activemq:sample.queue").to("bean:queueHandler");`
 5. In `QueueHandler`, the message is sent to `/topic/messages` channel.
  
 #### Flow
  StompClient -> ActiveMq -> Camel -> Topic -> StompClient
 
### How to run
 - After download or clone, go to root directory, and run `mvn spring-boot:run`.
 - After the app starts, open the browser and go to [http://localhost:8080](http://localhost:8080/login)

### Reference:
I followed the tutorial and code from [here](http://yalingunayer.com/blog/realtime-data-delivery-on-spring-boot-using-activemq-and-stomp-over-websockets-part-2/), but changed it to use embedded ActiveMQ. 

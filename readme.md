Log:


https://spring.io/guides/tutorials/rest




References:



JPA rest API:
 - https://www.baeldung.com/spring-boot-hibernate
 - https://spring.io/guides/tutorials/rest

H2-setup:
 - https://www.baeldung.com/spring-boot-h2-database

Project structure:
 - https://www.javaguides.net/2019/01/spring-mvc-project-structure.html
 - https://medium.com/@bubu.tripathy/spring-mvc-best-practices-427fbd11e1f8


H2-console:
 - http://localhost:8080/h2-console


Data model:

https://data.gov.hk/en-data/dataset/hk-ogcio-st_div_04-carpark-info-vacancy/resource/81c45810-cd90-4812-a5bd-923744df3aba

Carpark
 - id (String)
 - name (String)
 - nature (String)
 - address (String)
 - district (String)
 - evFriendly (Boolean)


REST script:


Read:
curl -v localhost:8080/carparks
curl -v localhost:8080/carparks/99

Create:
curl -X POST localhost:8080/carparks -H 'Content-type:application/json' -d '{"id":99,"name":"99_name","nature":"99_nature","address":"99_address","district":"99_district","evFriendly":false}'

Update:
curl -X PUT localhost:8080/carparks/99 -H 'Content-type:application/json' -d '{ "name":"UPDATE_name","nature":"UPDATE_nature","address":"UPDATE_address","district":"UPDATE_district","evFriendly":false}'

Delete:
curl -X DELETE localhost:8080/carparks/99

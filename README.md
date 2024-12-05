# login
SpringBoot + React Basic Login

- 가장 기본적이지만 핵심을 다 짚으며, Login을 개발한다.
- JWT Token + Redis 를 기반으로 하고, Refresh Token을 이용해 보안성을 강화
- OAuth를 추가적으로 연동할 예정
- Filter, Intercepter, AOP등 Spring의 공통 처리 기능을 모두 사용하며, JVM을 최적화하여 메모리 관리한다.
- JVM Warm Up, GC 튜닝등의 작업을 할 예정이다.

Java 21
Gradle 8.11.1
SpringBoot 3.3.3  


### Basic Dependercy  
1. Spring Web
2. Spring Security
3. Spring Data Redis
4. JWT (아직 추가 안함)
5. Spring AOP (아직 추가 안함)
6. Spring Validation
7. Lombok
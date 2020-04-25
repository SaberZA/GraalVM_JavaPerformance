# Getting started with GraalVM with Spring Java Applications

### THIS ONLY WORKS ON LINUX - THE GRAAL NATIVE IMAGE BUILDER IS NOT SUPPORTED ON WINDOWS

## Installation
- https://youtu.be/u1XJTI1PVLw
- https://www.graalvm.org/docs/getting-started/
- You will first need to install the GraalVM onto your machine and replace the relevant environment variables as described in the link above
- Next, you need to install the Graal Native Image Builder; You can use the Graal CLI anywhere to do this.
  - gu install native-image
- Apply mainClassName to build.gradle
    ```groovy
    springBoot {
        mainClassName = 'io.stevenv.graalvm.GraalvmApplication'
    }
    ```
- Add dependency
```groovy
    implementation 'org.springframework.experimental:spring-graal-native:0.6.0.RELEASE'
	implementation 'org.springframework:spring-context-indexer'
	implementation 'org.projectlombok:lombok'
```
- Using lombok gives us simpler to write classes!
    ```java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Reservation {
        @Id
        public Integer id;
        private String name;
    }
    ```
    ```java
    class Reservation {
    
        public Reservation(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    
        @Override
        public String toString() {
            return "Reservation{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    
        @Id
        public Integer id;
        private String name;
    
        public Integer getId() {
            return id;
        }
    
        public void setId(Integer id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    }
    ```


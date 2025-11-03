# Java Exercising using vsCode on Windows os

> JDK 17
```bash
C:\Users\user>java -version
java version "17.0.15" 2025-04-15 LTS
Java(TM) SE Runtime Environment (build 17.0.15+9-LTS-241)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.15+9-LTS-241, mixed mode, sharing)
```

---

```
# 프로젝트 구조 예시
JAVAEX/
├── app/
│   ├── build/
│   │   ├── classes/
│   │   │   └── java/
│   │   │       └── main/
│   │   │           └── javaex003/
│   │   │               └── App.class   👈 ← 여기에 생성됨
│   │   ├── libs/
│   │   │   └── app.jar                 👈 ← 빌드된 실행용 JAR 파일
│   │   ├── tmp/
│   │   └── reports/
│   └── src/
│       └── main/java/javaex003/App.java

```


## 1. Java 파일 작성
- `app/src/main/java/javaex/App.java` 파일 생성
```java
package javaex;
public class App {
    public static void main(String[] args) {
        System.out.println("Hello, Java World!");
    }
}
```

## 2. 컴파일 및 빌드
- vsCode 터미널에서 아래 명령어 실행
```bash
cd app
javac -d build/classes/java/main src/main/java/javaex/App.java
jar --create --file build/libs/app.jar -C build/classes/java/main .
```

## 3. 실행
- vsCode 터미널에서 아래 명령어 실행
```bash
cd app
java -cp build/libs/app.jar javaex.App
```

- 출력 결과
```bash
Hello, Java World!
```

## 4. 참고
- [Java 공식 문서](https://docs.oracle.com/en/java/)
- [vsCode Java Extension](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
- [JDK 다운로드](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [JDK 설치 및 환경 변수 설정 가이드](https://www.baeldung.com/java-home-on-windows-10)
- [Java 컴파일 및 실행 가이드](https://www.geeksforgeeks.org/how-to-compile-and-run-a-java-program-in-windows/)
- [vsCode에서 Java 프로젝트 설정 가이드](https://code.visualstudio.com/docs/java/java-project)
- [Java JAR 파일 생성 및 실행 가이드](https://www.javatpoint.com/how-to-create-jar-file-in-java)
- [Java 패키지 및 모듈 가이드](https://www.baeldung.com/java-modularity)
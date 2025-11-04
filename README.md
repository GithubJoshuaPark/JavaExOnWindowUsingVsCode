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

---

## 예제 프로그램 목록 및 설명

### 기본 프로그래밍 개념
1. **Advanced Data Types** (DataTypesAdvanced)
   - Java의 고급 데이터 타입 사용 예제
   - BigInteger, BigDecimal 등 정밀 계산 타입 활용

2. **String Manipulation** (StringManipulationExamples)
   - 문자열 처리와 조작 메서드 활용
   - StringBuilder, 정규표현식 등 문자열 작업

3. **Collections Framework** (CollectionsFrameworkOverview)
   - Java 컬렉션 프레임워크 활용
   - List, Set, Map 인터페이스 사용법

4. **ArrayList Implementation** (ArrayListImplementation)
   - ArrayList 사용 방법과 활용 예제
   - 동적 배열 조작과 관리

5. **HashMap Usage** (HashMapUsageExamples)
   - HashMap을 이용한 키-값 데이터 관리
   - Map 인터페이스 실전 활용

### 예외 처리와 입출력
6. **Exception Handling** (ExceptionHandlingExamples)
   - try-catch 구문과 예외 처리
   - 다양한 예외 상황 대처 방법

7. **Custom Exceptions** (CustomExceptionsCreation)
   - 사용자 정의 예외 클래스 생성
   - 비즈니스 로직에 맞는 예외 처리

8. **File I/O Operations** (FileIOOperations)
   - 파일 입출력 작업 예제
   - 텍스트/바이너리 파일 처리

9. **Serialization** (SerializationExample)
   - 객체 직렬화와 역직렬화
   - 객체 저장과 복원 방법

### 동시성과 스레드
10. **Multithreading Basics** (MultithreadingBasics)
    - 기본 스레드 생성과 관리
    - 병렬 처리 기초

11. **Synchronization** (SynchronizationTechniques)
    - 스레드 동기화 메커니즘
    - 공유 리소스 접근 제어

12. **Executor Framework** (ExecutorFrameworkUsage)
    - 스레드 풀과 작업 관리
    - 비동기 작업 실행

### 제네릭과 람다
13. **Generic Classes** (GenericClassesDemo)
    - 제네릭 클래스 설계
    - 타입 안정성 보장

14. **Wildcards in Generics** (WildcardsInGenerics)
    - 제네릭 와일드카드 활용
    - 유연한 타입 바운드

15. **Lambda Expressions** (LambdaExpressionsDemo)
    - 람다식 문법과 활용
    - 함수형 인터페이스 사용

### 스트림과 함수형 프로그래밍
16. **Stream API** (StreamAPIDemo)
    - 스트림 연산과 파이프라인
    - 데이터 처리 최적화

17. **Optional Class** (OptionalClassDemo)
    - null 처리의 안전한 방법
    - Optional 활용 패턴

18. **Functional Interfaces** (FunctionalInterfacesDemo)
    - 기본 함수형 인터페이스
    - 커스텀 함수형 인터페이스 설계

### 리플렉션과 어노테이션
19. **Method References** (MethodReferencesDemo)
    - 메서드 참조 문법
    - 람다식 대체 활용

20. **Custom Annotations** (CustomAnnotationsDemo)
    - 커스텀 어노테이션 정의
    - 어노테이션 프로세서 활용

21. **Reflection API** (ReflectionAPIDemo)
    - 동적 클래스 조작
    - 런타임 메타데이터 활용

### 유틸리티와 디자인 패턴
22. **Date/Time API** (DateTimeAPIDemo)
    - 현대적 날짜/시간 처리
    - 시간대와 기간 계산

23. **Singleton Pattern** (SingletonPatternDemo)
    - 싱글톤 패턴 구현
    - 스레드 안전한 싱글톤

24. **Factory Pattern** (FactoryPatternDemo)
    - 팩토리 패턴 활용
    - 객체 생성 캡슐화

25. **Observer Pattern** (ObserverPatternDemo)
    - 옵저버 패턴 구현
    - 이벤트 기반 프로그래밍

### 고급 기능과 테스팅
26. **Advanced Enums** (AdvancedEnumsDemo)
    - 열거형 고급 기능
    - 열거형 메서드와 필드

27. **JUnit Testing** (JUnitTestingDemo)
    - 단위 테스트 작성
    - 테스트 케이스 설계

28. **Mockito Framework** (MockitoFrameworkDemo)
    - 목 객체 생성과 검증
    - 테스트 더블 활용

29. **JDBC Operations** (JDBCOperations)
    - 데이터베이스 연결과 쿼리
    - CRUD 작업 구현

### 실전 응용
30. **Snake Game** (SnakeGame)
    - Swing GUI 게임 구현
    - 키보드 이벤트 처리
    - 게임 루프와 상태 관리
    - 효과음 재생 (eat.wav, gameover.wav)
    - 게임 규칙:
      - 방향키로 이동
      - 먹이(빨간 점)를 먹으면 뱀이 성장
      - 자신의 몸과 충돌하면 게임 오버
      - 'R' 키로 재시작

## 프로젝트 빌드 및 실행
### Gradle 명령어
```bash
# 전체 프로젝트 빌드
./gradlew build

# 애플리케이션 실행
./gradlew :app:run

# 테스트 실행
./gradlew test

# 배포용 JAR 생성 (의존성 포함)
./gradlew :app:jar
```

### 배포 방법
1. **Fat JAR 생성**
```bash
./gradlew :app:build
# JAR 파일은 app/build/libs/ 디렉토리에 생성됨
```

2. **실행 방법**
```bash
java -jar app/build/libs/app.jar
```




# spring-mvc

### 웹 애플리케이션 서버의 요청 및 응답

1. 웹 브라우저를 통해 HTTP요청 
2. HTTP 요청 메세지를 기반으로 HttpServletRequest 생성
3. 서블릿 컨테이너에 싱글톤으로 되어있는 Servlet을 찾아 service메서드를 호출하여 HttpServletRequest, HttpServletResponse 념겨준다.
4. 작업을하고 HttpServletResponse를 통해 Http 응답 메세지를 반환한다.

### HttpServletRequest

HTTP요청 메시지를 편리하게 사용할 수 있도록 HTTP요청 메시지를 파싱하고 결과를 담아 제공한다.

부가기능

- 요청이 시작부터 끝까지 유지되는 동안 Attribute를 저장할 수 있다.(임시 저장소)

- 세션을 관리 할 수 있다

### Http 데이터 요청

1. GET - query parameter 

   URL에 데이터 포함

2. POST - HTML Form

   Content-type: application/x-www-form-urlencoded

   body에 쿼리파라미터 형식으로 전달

   `HttpServletRequest` 에서 `getParameter` 메서드로 queryParameter형식으로 조회가능하다.

3. HTTP message body 에 데이터를 직접 담아 요청

   주로 JSON사용

### HttpServletResponse

HTTP응답 메세지 생성 - HTTP응답 코드, Header, Body 생성기능을 제공한다.

편의 기능으로 Content-Type, Cookie, Redirect 제공

## Template Engine

`servlet` 으로 동적인 HTML을 만들 수 있었다. 그런데 코드 자체가 복잡하고 생산성이 너무 떨어진다.

그래서 자바코드로 HTML을 관리하는게 아닌 HTML문서에 동적으로 데이터를 변경하는 자바코드를 넣을 수 있도록해주는 Template Engine이 등장한다.

JSP, Thymeleaf, Velocity 등 다양한 템플릿 엔진이 있지만 Spring과 잘맞는 Thymeleaf를 사용하도록 한다.



### Servlet JSP 한계

서블릿으로 개발할때 View를 위한 HTML을 자바코드로 구현해 지저분하고 복잡했다.

JSP를 사용해서 HTML을 분리했지만 역시나 지저분하고, 비즈니스 로직이 절반인 JSP도 보인다.

JSP가 너무 많은 역할을 하게된다. 수천줄이 넘어가는 비즈니스 로직을 구현한다면 생각만해도 끔찍하다..



이러한 한계와 고민들이 **MVC 패턴의 등장** 하게 만들었다.

## MVC패턴

하나의 서블릿과 JSP만으로 비즈니스 로직과 뷰 렌더링을 하면 많은 역할을 하고, 유지보수가 어렵다. 

변경의 라이프사이클이 다르다는 점이 문제다.

UI와 비즈니스로직의 수정은 다르게 발생할 가능성이 높고, 서로 영향을 주지않는다. 그런데 하나의 코드로 관리된다면 유지보수가 어렵게 만든다.

### Model View Controller

세가지 영역으로 나눠서 관리한다.

Controller : Http요청을 받아 파라미터를 검증, 비즈니스 로직을 실행, 뷰에 전달할 결과 데이터를 모델에 담아 전달한다.

Model : View에 전달할 데이터를 담아둔다. 뷰는 비즈니스로직과 데이터 접근을 몰라도되고 렌더링에만 집중한다.

View : 모델에 담겨있는 데이터를 사용해 HTML생성만 집중한다. 렌더링에만 집중



컨트롤러가 많은 로직을 담당하다보니 Service 계층을 만들어 비즈니스 로직을 분리한다.

---

>**redirect vs forward
>**redirect : 실제 클라이언트(웹 브라우저)에 응답이 나갔다가, 클라이언트가 redirect 경로로 다시요청한다. 따라서 클라이언트가 인지할 수 있고, URL 경로도 실제로 변경된다. 
>
>foward : 서버 내부에서 일어나는 호출이기 때문에 클라이언트가 전혀 인지하지 못한다.

### MVC 한계

컨트롤러 역할과 뷰 렌더의 역할은 명확하게 구분했다.

컨트롤러, Servlet은 중복이 너무많다. viewPath, forward 코드 등..

```
String viewPath = "/WEB-INF/views/xxxx.jsp";
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
		dispatcher.forward(request, response);
```

request, response를 사용하지 않을때가 있는점..

공통처리가 어렵다. 개별적인 servlet으로 막 들어오기 때문에..

Front Controller 패턴을 도입해서 문제를 해결할 수 있다. Spring MVC의 핵심도 FrontController에 있다.

### FrontController패턴

프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받는다.

프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출한다.

한군데서 요청을 받아 공통 처리가 가능하다.

프론트 컨트롤러를 제외하고 나머지 컨트롤러는 서블릿을 사용하지 않아도된다.



- /servlet/web/frontcontroller/v1

  가장 앞단에 서블릿 하나를 통해서 요청을 받고 각 컨트롤러로 비즈니스 로직을 처리하는 구조로 변경

- /servlet/web/frontcontroller/v2

  각각의 컨트롤러에서 forward하는 중복 로직을 MyView 객체를 통해 FrontController로 반환하고 forward하는 방식으로 변경

- /servlet/web/frontcontroller/v3

  View 경로의 경로 중복을 제거 - 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경한다. View Resolver

  개별 컨트롤러의 서블릿 request, response의 종속성을 제거 - FrontController만 request, response에 종속적

- /servlet/web/frontcontroller/v4

  개발자의 사용 편의성을 개선 - 객체가 아닌 뷰 이름만 반환하도록

  ---

  지금까지의 컨트롤러들은 호환이 불가능한 단점이 있다.

- /servlet/web/frontcontroller/v5

  핸들러 어댑터를 통해 원하는 종류의 컨트롤러를 호출한다.

### DispatcherServlet

이런 과정들이 SpringMVC 구조와 유사하게 만들어본 내용이다.

- FrontController -> DispatcherServlet

- handlerMappingMap -> HandlerMapping

- MyHandlerAdapter -> HandlerAdapter

- ModelView -> ModelAndView

- viewResolver -> ViewResolver

- MyView -> View

---

DispatcherServlet도 상속을 따라가다보면 HttpServlet을 상속받아 사용하고, 서블릿으로 동작한다.

스프링 부트는 DispatcherServlet을 서블릿으로 자동등록하면서 모든 경로를 매핑한다.

### 요청 흐름

DispatcherServlet의 부모인 FrameworkServlet에서 service 메서드를 오버라이드 되어있다.

service 메서드를 시작으로 DispatcherServlet의 doDispatcher가 호출된다.

정리하면

1. 핸들러 조회
2. 핸들러 어댑터를 조회 - 핸들러를 처리 할 수 있는 어댑터
3. 핸들러 어댑터를 실행
4. 핸들러 어댑터를 통해 핸들러를 실행
5. ModelAndView 반환
6. ViewResolver를 통해 View반환
7. View 렌더링

### 핸들러 매핑과 핸들러 어댑터

HandlerMapping

- 핸들러 매핑에서 핸들러 어댑터를 찾을 수 있어야한다.

  ```
  0 = RequestMappingHandlerMapping : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
  1 = BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러를 찾는다.
  ```

HandlerAdapter

- 핸들러 매핑을 통해서 찾은 핸들러를 실행할 수 있는 핸들러 어댑터가 필요하다.

  ```
  0 = RequestMappingHandlerAdapter : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
  1 = HttpRequestHandlerAdapter : HttpRequestHandler 처리
  2 = SimpleControllerHandlerAdapter : Controller 인터페이스(애노테이션X, 과거에 사용) 처리
  ```

### View Resolver

Spring Boots는 InternalResourceViewResolver 를 자동 등록하고 application.properties를 통해 prefix, suffix를 설정하여 해당 정보를 통해 등록.

```
1 = BeanNameViewResolver : 빈 이름으로 뷰를 찾아서 반환한다. (예: 엑셀 파일 생성 기능에 사용)
2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.
```

view이름을 통해 ViewResolver를 찾고, viewResolver를 통해 view를 반환한다.

View는 render를 호출하고 forward하여 jsp를 실행한다.

---

### Spring MVC

 @RequestMapping 애노테이션을 사용하는 컨트롤러

- RequestMappingHandleMapping
- RequestMappingHandlerAdapter

우선순위가 가장 높은 핸들러 매핑과 핸들러로 실무에서 거의 이방식을 사용한다.

/servlet/web/springmvc/v1

​	 -> 기존 FrontController방식을 @RequestMapping 어노테이션 기반으로 변경

/servlet/web/springmvc/v2

​	 -> 하나의 컨트롤러로 통합

/servlet/web/springmvc/v3

​	 -> Spring제공하는 @RequestParam, @GetMapping, @PostMapping 등 편의 기능을 사용하여 코드 정리

---

### HTTP응답 - 정적 리소스, 뷰 템플릿

/static, /public, /resources, /META-INF/resources 디렉토리의 정적리소스를 제공

Src/main/resources는 리소스를 보관하는곳, 또 클래스패스의 시작경로

응답 방식

​	HttpServletResponse -> writer를 통해 메세지 반환

​	HttpEntity사용해서 반환

​	@ResponseBody 통해서 반환, @ResponseStatus로 상태코드 반환

​	@RestController를 사용하면 @ResponseBody를 기본 적용하여 데이터를 직접 반환

​		View Resolver 대신 HttpMessageConverter 동작

### HttpMessageConverter

바이트 처리 : ByteArrayHttpMessageConverter  

​	- 요청 응답 byte[] data

​	- 미디어타입 ->`application/octet-stream`

기본 문자처리 : StringHttpMessageConverter

​	- 요청 응답 String data

​	- 미디어타입 ->`text/plain`

객체 처리 : MappingJackson2HttpMessageConverter 

​	- 요청 응답 HelloData data , HashMap

​	- 미디어타입 ->`application/json`

HttpMessageConverter는 HTTP 요청, 응답 둘다 사용

- 해당 클래스와 미디어타입을 지원하는지 확인

  요청시 `canRead()` 로 대상 클래스 타입을 지원하는지 확인, Http요청의 Content-Type 미디어 타입 지원 확인

  조건을 만족하면 `read()` 를 통해 객체 생성 및 반환

- 메시지를 읽고 쓰는 기능

  응답시 `canWrite()` 로 대상 클래스 타입 지원 확인, Http요청의 Accept 미디어 타입 지원을 확인(클라이언트가 메세지를 읽을 수 있다는걸 확인해야하기 때문)

  조건을 만족하면 `write()` 를 통해 http body에 데이터 생성

## ReuqestMappingHandlerAdapter 동작방식

1. DispatcherServlet으로부터 @RequestMapping 애노테이션 기반의 요청이 들어오면 핸들러 어댑터를 찾아 RequestMappingHandlerAdapter를 찾게 된다.

2. RequestMappingHandlerAdapter는 ArgumentResolver를 호출해서 컨트롤러가 필요로 하는 다양한 파라미터 값들을 생성한다.

   @RequestParam, @RequestBody, @ModelAttribute 등등..

3. 파라미터의 값이 준비가되면 컨트롤러를 호출하며 값을 넘겨준다.

4. 응답의 반환은 ReturnValueHandler를 통해 ArgumentResolver와 비슷하게 응답 값을 준비해서 반환한다.

   @ResponseBody, ModelAndView, HttpEntity..

> 공식문서 참고해서 Arguments, Return Values 참고하자.
>
> https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-arguments

ArgumentResolver, ReturnValueHandler들이 HttpMessageConverter를 사용해서 필요한 값들을 생성한다

---

### Tymeleaf

스프링 부트가 자동으로 ThymeleafViewResolver를 빈으로 등록한다.

prefix, suffix설정

```
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

공식문서 참고 : https://docs.spring.io/spring-boot/docs/2.4.3/reference/html/appendix-application-properties.html#common-application-properties-templating



- SSR(Server Side Rendering)

  Backend 서버에서 HTML을 동적으로 렌더링한다.

- Natural Template

  순수 HTML을 최대한 유지한다. 파일을 열어도 내용을 확인 할 수 있다.

  서버를 통해 뷰 템플릿을 거쳐 동적으로 변경된 결과를 확인할 수 있다.

- 스프링 통합 지원

- 타임리프 사용

  ```html
  <html xmlns:th="http://www.thymeleaf.org">
  ```

### Thymeleaf - Escape

HTML엔티티 : `<` 태그의 시작이 아니라 문자로 표현할 방법

HTML에서 사용하는 특수문자를 HTML엔티티로 변경하는것을 escape라 한다.  `th:text` 사용

unEscape : 태그로 인식 -> `th:utext` 사용

> escape를 사용하지 않아 HTML 이 정상 렌더링 되지 않는 문제가 발생하기도 한다. escape를 기본으로 하고 필요할떄 unescape을 사용해야 한다.

### springEL

Object, List, Map 의 EL 표현식

```
${user.username}
${user.['username']}
${user.getUsername()}

${users[0].username}
${users[0].['username']}
${users[0].getUsername()}

${userMap['userA'].username}
${userMap['userA'].['username']}
${userMap['userA'].getUsername()}
```

지역변수 선언 - 해당 태그내에서 사용, 벗어나면 사용 불가능

```html
<div th:with="first=${users[0]}">
  <p>처음 사람의 이름은 <span th:text="${first.username}"></span></p>
</div>
```

기본 객체의 접근

```
${#request}
${#response}
${#session}
${#locale}

@{request.paramData} // 기본 객체에 대한 thymeleaf 편의기능 제공
${session.sessionData} // 기본 객체에 대한 thymeleaf 편의기능 제공
${@helloBean.hello("spring!")}  //스프링 빈에 접근
```

리터럴 : 소스 코드상 고정된값

타임리프에서 문자리퍼털은 `'` 로 감싸야 한다. 공백없이 이어진값은 하나의 토큰으로 인정하여 생략가능하다.

공백이 있으면 감싸야함!



반복

```html
<tr th:each="user, userStat : ${users}">
```

두번째 파라미터를 설정해 리스트의 상태를 확인할 수 있는 기능들을 제공한다.

두번째 파라미터는 생략이 가능하다. rule = 첫번째변수 + Stat



javascript inline

```html
<script th:inline="javascript">
```





---

### 참고

`/resources/static` 폴더에 HTML을 넣어두면 실제 서비스에서도 공개된다. 공개할 필요가 없는 HTML을 두는것을 피하자.

@ModelAttribute는 프로퍼티 접근법(setXXX)로 입력

@ModelAttribute의 추가기능은 name속성을 통해 모델에 포함시킬 수 있다.

​	@ModelAttribute("item")  -> model.addAttribute("item", item) 자동 추가해준다.



웹 브라우저의 새로고침은 마지막 서버 전송데이터를 다시 전송

상품 등록 폼에서 데이터 입력 저장 -> 새로고침 -> 다시 저장 요청 -> 동일한 데이터가 계속 쌓임

PRG -> Post/Redirect/Get

redirect로 상품상세화면을 호출하도록 해주면 새로고침을 해도 추가되지 않는다.

---


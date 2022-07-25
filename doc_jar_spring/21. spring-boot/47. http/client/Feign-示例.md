<span  style="font-family: Simsun,serif; font-size: 17px; ">

### GET
~~~
// 1. 追加参数
@GetMapping(value = "/url/path", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
returnType methodName(@RequestHeader(name = "token") String token,@RequestParam(value = "type") Integer type);

// 2. get带请求体
@GetMapping(value = "/url/path", produces = APPLICATION_JSON_VALUE, consumes APPLICATION_JSON_VALUE)
JSONObject methodName(@RequestHeader(name = "Content-Type") String Content_Type,@RequestBody Map<String,String> map);

// 3. 
~~~

### POST
~~~
// 1. 请求体为对象
@PostMapping(value = "/url/path", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
returnType methodName(@RequestHeader(name = "keyName") String keyName, @RequestBody Object obj);

// 2. 
@PostMapping(value = "/url/path", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
returnType methodName(@RequestHeader(name = "Content-Type") String Content_Type, @SpringQueryMap Map<String, String> map);
~~~

### 参数类型 

~~~
- 网络请求类型
- import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
~~~

### 参数

spring | api
---|---
请求 | @FeignClient(name = "name", url = "${spring.}") <br> @FeignClient（类注解）
根地址 | 注解可选参数url
数据类型 | MediaType
请求路径和类型 | @GetMapping(value = "/path", produces = APPLICATION_JSON_VALUE <br> value路径，produces数据类型


</span>
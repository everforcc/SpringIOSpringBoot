<span  style="font-family: Simsun,serif; font-size: 17px; ">


api | 说明
---|---
类 | 
@FeignClient | 类注解
name | 本服务名
url | 请求地址的公共根目录
方法  | 
@PostMapping | post
@GetMapping | get
value = "/path" | 请求地址
consumes | 请求参数格式
producesg | 返回参数格式
@RequestParam | 地址栏参数
@SpringQueryMap | 将map转化为地址栏参数，当参数>=4 一般来说
@RequestBody | post的body
参数 | 
@PathVariable("number") final int number <br> @PathVariable("size") final int size | rest风格的参数
返回值 | 
String | 常见返回，json或string数据类型
feign.Response | 用来处理复杂问题，用api可以转化为stream <br> InputStream inputStream = response.body().asInputStream();
示例代码 |

---

~~~

Object getObject(@RequestHeader(name = "token") String token,
@SpringQueryMap Object2 obj); // SpringQueryMap RequestBody
~~~

---

- 转化流
~~~

~~~




</span>
<font face="Simsun" size=3>

## service

### 1. 必须传入对象，禁止传入字符串等数据

### 2. 数据校验

~~~
@Validated
@Transactional
public class WebSiteService {

    @Validated({ISave.class})
    @Transactional(rollbackFor = Exception.class)
    public WebSiteDto insert(@Valid WebSiteDto webSiteDto){
        
        return webSiteDto;
    }
}    
~~~



</font>
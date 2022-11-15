<span  style="font-family: Simsun,serif; font-size: 17px; ">

### if

~~~
@Select({"<script>",
            "SELECT * FROM cc_mybatis_plus_user WHERE 1=1 ",
            "<if test='mybatisPlusUser.name != null and mybatisPlusUser.name != `` '>",
            " and name = #{mybatisPlusUser.name}",
            "</if>",
            "</script>"
    })
~~~

</span>
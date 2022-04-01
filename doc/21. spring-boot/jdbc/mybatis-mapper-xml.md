<font face="Simsun" size=3>

## cache缓存
~~~


~~~

## keysql片段
~~~

~~~

### where,set,if,foreach

1. select
~~~
<select id="getUser" parameterType="com.fc.pojo.User" resultType="com.fc.pojo.User">
    select * from user 
    <where>
     <if test="username != null">
       username=#{username}
    </if>
     
    <if test="age!= null">
       and age=#{age}
    </if>
    </where>
</select>
~~~
2. update
~~~
<update id="updateUser" parameterType="com.fc.pojo.User">
    update user 
        <set>
            <if test = "username != null">
                user.username = #{username},
            </if>
            <if test = "age != 0">
                user.age = #{age}
            </if>
        </set>    
        where id = #{id}
</update>
~~~
3. foreach
~~~
<!-- 以id集合删除用户 -->
    <delete id="deleteByIdList" parameterType="com.fc.pojo.UserVo">
        delete from user
        where id in
        <foreach item="id" collection="idList" open="(" separator="," close=")">
         #{id}
        </foreach>
    </delete>
    <!-- 以id数组删除用户 -->
    <delete id="deleteByIdArray" parameterType="com.fc.pojo.UserVo">
        delete from user
        where id in
        <foreach item="id" collection="idArray" open="(" separator="," close=")">
         #{id}
        </foreach>
    </delete>
~~~
4. sql片段

- 重复率较高的sql取出来公共使用

~~~
<sql id="base_insert_sql" >
    insert into user(id,username,age)values(#{id},#{username},#{age})
</sql>
<!-- 新增用户 -->
<insert id="addUser" parameterType="com.fc.pojo.User">
    <include refid="base_insert_sql" />
</insert>
<!-- 新增用户，返回用户id -->
<insert id="addUserWithId" parameterType="com.fc.pojo.User">
    <selectKey keyProperty="id" resultType="int">  
        select LAST_INSERT_ID()  
    </selectKey> 
    <include refid="base_insert_sql" />
</insert>
~~~

</font>

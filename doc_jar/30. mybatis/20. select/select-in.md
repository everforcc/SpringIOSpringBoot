<span  style="font-family: Simsun,serif; font-size: 17px; ">

### in查询，如果超过1000条,需要分开

- 如果坐标对1000取余=999,那就用  ) OR e.`id` IN( 相当于加个OR
- 否则用 #{id} 取数
- 刚开始999条加一个 OR,后来1000条

~~~xml

<select id="whereIn" resultType="java.lang.String">
    SELECT e.`orderno` FROM cc_t_order e
    WHERE e.`id` IN
    <foreach collection="stringList" index="index" open="(" close=")" item="bean" separator=",">
        <if test="(index % 1000) == 999">
            ) OR e.`id` IN(
        </if>
        #{bean.id},#{bean.uuid}
    </foreach>
</select>
~~~

</span>
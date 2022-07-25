<span  style="font-family: Simsun,serif; font-size: 17px; ">

- 选择下拉框
- @ 替换单元格内数据
- 函数
~~~
=IF(INDIRECT(CHAR(COLUMN()+64)&"1")=INDIRECT("A"&ROW()),"是","不是")
~~~

</span>
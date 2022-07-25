<span  style="font-family: Simsun,serif; font-size: 17px; ">


字段摘要
 

static double
 
E
     比任何其他值都更接近 e（即自然对数的底数）的 double 值。
 

static double
 
PI
       比任何其他值都更接近 pi（即圆的周长与直径之比）的 double 值。
 


---

1. 返回绝对值类   
   
返回 int 值的绝对值           static int  abs(int a)   
   
返回 long 值的绝对值          static long  abs(long a)   
   
返回 float 值的绝对值         static float  abs(float a)   
   
返回 double 值的绝对值        static double  abs(double a)   
   
    
   
2. 返回角的三角函数类   
   
返回角的余弦                 static double cos(double a)   
   
返回角的正弦                 static double sin(double a)   
   
返回角的正切                 static double tan(double a)   
   
    
   
3. 返回角的反三角函数类   
   
返回角的反余弦，范围在 0.0 到 pi 之间        static double acos(double a)   
   
返回角的反正弦，范围在 -pi/2 到 pi/2 之间    static double asin(double a)   
   
返回角的反正切，范围在 -pi/2 到 pi/2 之间    static double atan(double a)   
   
    
   
4. 返回双曲线值类   
   
返回 double 值的双曲线余弦                 static double  cosh(double x)   
   
返回 double 值的双曲线正弦                 static double  sinh(double x)   
   
返回 double 值的双曲线正切                 static double  tanh(double x)   
   
    
   
5. 返回指数值类   
   
返回欧拉数 e 的 double 次幂的值。         Static double  exp(double a)   
   
返回 ex -1。                               Static double  expm1(double x)   
   
返回第一个参数的第二个参数次幂的值。       Static double pow(double a, double b)   
   
    
   
6. 返回对数值类   
   
返回 double 值的自然对数（底数是 e）。    static double  log(double a)   
   
返回 double 值的底数为 10 的对数          static double  log10(double a)     
   
返回参数与 1 之和的自然对数                static double  log1p(double x)   
   
    
   
7. 返回方根值类   
   
返回正确舍入的 double 值的正平方根        static double sqrt(double a)   
   
返回 double 值的立方根                    static double cbrt(double a)   
   
返回 sqrt(x2 +y2)，没有中间溢出或下溢      Static double  hypot(double x, double y)   
   
    
   
8. 返回舍入值类   
   
返回最小的（最接近负无穷大）double 值，该值大于等于参数，并等于某个整数。   
   
                                       static double ceil(double a)   
   
返回最大的（最接近正无穷大）double 值，该值小于等于参数，并等于某个整数。   
   
                                       static double  floor(double a)   
   
返回最接近参数的 long                    static long   round(double a)   
   
返回最接近参数的 int                     static int    round(float a)   
   
返回最接近参数并等于某一整数的 double 值   static double  rint(double a)   
   
返回 d × 2scaleFactor，其舍入方式如同将一个正确舍入的浮点值乘以 double 值集合中的一个值                                           static double  scalb(double d, int scaleFactor)   
   
返回 f × 2scaleFactor，其舍入方式如同将一个正确舍入的浮点值乘以 float 值集合中的一个值   
   
                                            static float  scalb(float f, int scaleFactor)   
   
返回 d 和正无穷大之间与 d 相邻的浮点值      static double   nextUp(double d)   
   
返回 f 和正无穷大之间与 f 相邻的浮点值      static float    nextUp(float f)   
   
返回第一个参数和第二个参数之间与第一个参数相邻的浮点数   
   
                                   static double  nextAfter(double start, double direction)   
   
返回第一个参数和第二个参数之间与第一个参数相邻的浮点数   
   
                                   static float  nextAfter(float start, double direction)   
   
9. 角度与弧度的互换   
   
将用弧度表示的角转换为近似相等的用角度表示的角   
   
                                              Static double  toDegrees(double angrad)   
   
将用角度表示的角转换为近似相等的用弧度表示的角   
   
                                              Static double  toRadians(double angdeg)   
   
10. 将矩形坐标 (x, y) 转换成极坐标 (r, theta) ，返回所得角 theta。   
   
                                        static double  atan2(double a, double b)   
   
    
   
11. 返回最值类   
 返回两个 double 值中较大的一个             static double max(double a, double b)   
  返回两个 int 值中较大的一个                static int   max(int a, int b)   
   
 返回两个 double 值中较小的一个            static double  min(double a, double b)   
   
 返回两个 int 值中较小的一个               static int min(int a, int b)   
   
12. 其他   
 返回带正号的 double 值，该值大于等于 0.0 且小于 1.0。   
   
                                           static double random()   


</span>
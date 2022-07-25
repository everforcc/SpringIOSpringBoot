<span  style="font-family: Simsun,serif; font-size: 17px; ">

## 1. 各种属性对应码值

~~~
    public static final int PUBLIC           = 0x00000001;

    /**
     * The {@code int} value representing the {@code private}
     * modifier.
     */
    public static final int PRIVATE          = 0x00000002;

    /**
     * The {@code int} value representing the {@code protected}
     * modifier.
     */
    public static final int PROTECTED        = 0x00000004;

    /**
     * The {@code int} value representing the {@code static}
     * modifier.
     */
    public static final int STATIC           = 0x00000008;

    /**
     * The {@code int} value representing the {@code final}
     * modifier.
     */
    public static final int FINAL            = 0x00000010;

    /**
     * The {@code int} value representing the {@code synchronized}
     * modifier.
     */
    public static final int SYNCHRONIZED     = 0x00000020;

    /**
     * The {@code int} value representing the {@code volatile}
     * modifier.
     */
    public static final int VOLATILE         = 0x00000040;

    /**
     * The {@code int} value representing the {@code transient}
     * modifier.
     */
    public static final int TRANSIENT        = 0x00000080;

    /**
     * The {@code int} value representing the {@code native}
     * modifier.
     */
    public static final int NATIVE           = 0x00000100;

    /**
     * The {@code int} value representing the {@code interface}
     * modifier.
     */
    public static final int INTERFACE        = 0x00000200;

    /**
     * The {@code int} value representing the {@code abstract}
     * modifier.
     */
    public static final int ABSTRACT         = 0x00000400;

    /**
     * The {@code int} value representing the {@code strictfp}
     * modifier.
     */
    public static final int STRICT           = 0x00000800;
~~~

## 2. 判断是否为对应属性

~~~
按位&来判断
    public static boolean isPublic(int mod) {
        return (mod & PUBLIC) != 0;
    }
~~~

</span>
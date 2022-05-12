import com.cc.sp90utils.commons.lang.RObjectsUtils;

public class TestUtils {

    public static void main(String[] args) {
        String s_null = null;
        String s_not_null = "not null";
        System.out.println(RObjectsUtils.nonNull(s_null));

        System.out.println(RObjectsUtils.nonNull(s_not_null));
    }

}

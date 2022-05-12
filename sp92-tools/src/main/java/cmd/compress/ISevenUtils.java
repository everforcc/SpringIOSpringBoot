package cmd.compress;

public interface ISevenUtils {

    void compress(String pas,String path,FileTypeCMDEnum fileTypeCMDEnum,CompressEnum compressEnum);

    void compress(String pas,String path,String initPath,FileTypeCMDEnum fileTypeCMDEnum,CompressEnum compressEnum);

}

package cmd.compress;

import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;

public interface ISevenUtils {

    void compress(String pas, String path, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum);

    void compress(String pas, String path, String initPath, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum);

}

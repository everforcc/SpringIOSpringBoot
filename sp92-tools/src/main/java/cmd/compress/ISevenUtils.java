package cmd.compress;

import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;

public interface ISevenUtils {

    /**
     * 压缩
     *
     * @param pas             密码
     * @param path            文件路径
     * @param fileTypeCMDEnum 压缩类型,zip,7z
     * @param compressEnum    按照什么压缩,文件或目录
     */
    void compress(String pas, String path, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum);

    /**
     * 压缩
     *
     * @param pas             密码
     * @param path            文件路径
     * @param initPath        目标路径
     * @param fileTypeCMDEnum 压缩类型,zip,7z
     * @param compressEnum    按照什么压缩,文件或目录
     */
    void compress(String pas, String path, String initPath, FileTypeCMDEnum fileTypeCMDEnum, CompressEnum compressEnum);

    /**
     * 解压
     *
     * @param pas     密码
     * @param oldPath 文件路径
     * @param newPath 解压后的路径
     */
    void deCompression(String pas, String oldPath, String newPath);

}

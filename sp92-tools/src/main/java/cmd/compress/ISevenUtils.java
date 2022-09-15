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

    /**
     * 解压目录
     *
     * @param pas        解压密码
     * @param oldPathDir 压缩文件夹路径
     * @param newPath    解压后的路径, 直接到当前文件夹下,这个使用要注意
     */
    void deCompressionDir(String pas, String oldPathDir, String newPath);

}

package cmd.compress;

import cmd.compress.constant.CompressEnum;
import cmd.compress.constant.FileTypeCMDEnum;
import cmd.compress.impl.SevenUtilsCMD;

public class FileCompressBuild {

    private ISevenUtils iSevenUtils = new SevenUtilsCMD();
    private String path;
    private String pas;
    private FileTypeCMDEnum fileTypeCMDEnum = FileTypeCMDEnum.z7;
    private CompressEnum compressEnum = CompressEnum.DIR;

    private FileCompressBuild() {
    }
    private FileCompressBuild(String path) {
        this.path = path;
    }

    public static FileCompressBuild build(String path){
        return new FileCompressBuild(path);
    }

    /**
     * 修改压缩的接口
     * @param useriSevenUtils
     */
    public void ofCompressImpl(ISevenUtils useriSevenUtils){
        this.iSevenUtils = useriSevenUtils;
    }
    public void ofPas(String pas){
        this.pas = pas;
    }
    public void ofCompressImpl(FileTypeCMDEnum fileTypeCMDEnum){
        this.fileTypeCMDEnum = fileTypeCMDEnum;
    }
    public void ofCompressImpl(CompressEnum compressEnum){
        this.compressEnum = compressEnum;
    }

    public void compress(){
        iSevenUtils.compress(pas,path,fileTypeCMDEnum,compressEnum);
    }

}

package cn.cc.sync.binlog.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class BinlogMessage implements Serializable {
    private String database;
    private String table;
    private String sqlType;          // INSERT / UPDATE / DELETE / QUERY
    private List<String> sqlList;    // 构建出的 SQL 集合
    private String binlogFile;
    private Long binlogPos;
    private Long timestamp;
    private String schemaVersion;    // 可选
    private Map<String, Object> extra;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.PrettyFormat);
    }
}

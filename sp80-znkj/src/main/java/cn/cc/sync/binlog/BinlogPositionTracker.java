package cn.cc.sync.binlog;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeader;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * BinlogPositionTracker 持续记录当前 binlog 文件与位点（含 rotate 事件），
 * 便于后续消息落库、恢复时能定位精确位置。
 */
@Component
public class BinlogPositionTracker {

    private final AtomicReference<String> currentFile = new AtomicReference<>();
    private final AtomicLong currentPosition = new AtomicLong(0L);

    public void setInitialFile(String filename) {
        if (filename != null && !filename.isEmpty()) {
            currentFile.compareAndSet(null, filename);
        }
    }

    public void track(Event event) {
        if (event == null) {
            return;
        }
        EventHeader header = event.getHeader();
        if (header instanceof EventHeaderV4) {
            EventHeaderV4 v4 = (EventHeaderV4) header;
            currentPosition.set(v4.getNextPosition());
        }
        EventData data = event.getData();
        if (data instanceof RotateEventData) {
            RotateEventData rotateEventData = (RotateEventData) data;
            currentFile.set(rotateEventData.getBinlogFilename());
            currentPosition.set(rotateEventData.getBinlogPosition());
        }
    }

    public String getCurrentFile() {
        return currentFile.get();
    }

    public long getCurrentPosition() {
        return currentPosition.get();
    }
}


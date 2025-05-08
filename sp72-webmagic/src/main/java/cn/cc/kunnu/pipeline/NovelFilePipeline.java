package cn.cc.kunnu.pipeline;

import cn.cc.kunnu.dao.CcCrawBookCapterDao;
import cn.cc.kunnu.dao.CcCrawBookContentDao;
import cn.cc.kunnu.dto.CcCrawBookCapter;
import cn.cc.kunnu.dto.CcCrawBookContent;
import cn.cc.utils.ChapterUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class NovelFilePipeline implements Pipeline {

    private static final String OUTPUT_PATH = "D:/novel_output/";

    CcCrawBookCapterDao ccCrawBookCapterDao;

    CcCrawBookContentDao ccCrawBookContentDao;

    public NovelFilePipeline() {
    }

    public NovelFilePipeline(CcCrawBookCapterDao ccCrawBookCapterDao, CcCrawBookContentDao ccCrawBookContentDao) {
        this.ccCrawBookCapterDao = ccCrawBookCapterDao;
        this.ccCrawBookContentDao = ccCrawBookContentDao;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");
        String content = resultItems.get("content");

        if (title != null && content != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_PATH + title + ".txt"))) {
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(Objects.nonNull(ccCrawBookCapterDao) && Objects.nonNull(ccCrawBookContentDao)){

            CcCrawBookCapter ccCrawBookCapter = new CcCrawBookCapter();
            ccCrawBookCapter.setName(title);
            ccCrawBookCapter.setBookId(1L);
            ccCrawBookCapter.setOrderNum(ChapterUtils.getChapterNumber(title));

            ccCrawBookCapterDao.insert(ccCrawBookCapter);

            CcCrawBookContent ccCrawBookContent = new CcCrawBookContent();
            ccCrawBookContent.setContent(content);
            ccCrawBookContent.setChapterId(ccCrawBookCapter.getId());
            ccCrawBookContentDao.insert(ccCrawBookContent);
        }

    }
}

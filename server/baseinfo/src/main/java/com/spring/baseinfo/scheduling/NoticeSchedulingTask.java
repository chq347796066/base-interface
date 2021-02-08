package com.spring.baseinfo.scheduling;
import com.spring.base.entity.baseinfo.NoticeEntity;
import com.spring.baseinfo.dao.INoticeDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Desc:通知公告有效期到了自动上线有效期过了自动下线
 * @Author:邓磊
 * @UpdateDate:2020/5/11 14:19
 * @return: 返回
 */
@Configuration
@EnableScheduling
public class NoticeSchedulingTask{
    @Autowired
    private INoticeDao noticeDao;
    @Scheduled(cron = "0 0 1 * * ?")
    private void noticeTasks() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(currentTime);
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setStartDate(date);
        List<NoticeEntity> noticeList= noticeDao.queryList(noticeEntity);
        if(CollectionUtils.isNotEmpty(noticeList)){
            noticeList.stream().forEach(noticeEntitys -> {
                //发布状态（1 未发布 2 已发布 3 已下线）
                NoticeEntity vo = new NoticeEntity();
                if(StringUtils.isEmpty(noticeEntitys.getReleaseDate())){
                    SimpleDateFormat offDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String releaseDate = offDate.format(new Date());
                    vo.setReleaseDate(releaseDate);
                }
                vo.setId(noticeEntitys.getId());
                vo.setReleaseStatus(2);
                noticeDao.updateNoticeStatus(vo);
            });
        }
        NoticeEntity notice = new NoticeEntity();
        notice.setEndDate(date);
        List<NoticeEntity> noticeEntityList= noticeDao.queryList(notice);
        if(CollectionUtils.isNotEmpty(noticeEntityList)){
            noticeEntityList.stream().forEach(noticeEntityVo -> {
                //发布状态（1 未发布 2 已发布 3 已下线）
                NoticeEntity vo = new NoticeEntity();
                if(StringUtils.isEmpty(noticeEntityVo.getOfflineDate())){
                    SimpleDateFormat offDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String offinedate = offDate.format(new Date());
                    vo.setOfflineDate(offinedate);
                }
                vo.setId(noticeEntityVo.getId());
                vo.setReleaseStatus(3);
                noticeDao.updateNoticeStatus(vo);
            });
        }
    }
}

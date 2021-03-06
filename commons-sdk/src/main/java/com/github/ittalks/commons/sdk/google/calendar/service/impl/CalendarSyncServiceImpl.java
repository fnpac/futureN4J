package com.github.ittalks.commons.sdk.google.calendar.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.ittalks.commons.sdk.google.calendar.entity.SyncCalendarListEntity;
import com.github.ittalks.commons.sdk.google.calendar.enums.Queue;
import com.github.ittalks.commons.sdk.google.calendar.service.CalendarSyncService;
import com.github.ittalks.commons.sdk.google.calendar.task.TaskType;
import com.kingsoft.wps.mail.queue.KMQueueManager;
import com.kingsoft.wps.mail.queue.Task;
import com.kingsoft.wps.mail.queue.TaskQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by 刘春龙 on 2017/2/20.
 */
@Service
public class CalendarSyncServiceImpl implements CalendarSyncService {

    private static final Logger logger = Logger.getLogger(CalendarSyncServiceImpl.class.getName());

    private final KMQueueManager calKMQueueManager;

    @Autowired
    public CalendarSyncServiceImpl(@Qualifier("calKMQueueManager") KMQueueManager calKMQueueManager) {
        this.calKMQueueManager = calKMQueueManager;
    }

    @Override
    public void sync(String userid) {
        // 向`消息队列`中加入`同步日历消息`
        // 1.获取`消息队列`
        TaskQueue taskQueue = calKMQueueManager.getTaskQueue(Queue.msg_queue.getName());
        //2.组装`同步日历消息`
        SyncCalendarListEntity syncCalendarListEntity = new SyncCalendarListEntity();
        syncCalendarListEntity.setUserId(userid);//当前要同步日历的用户ID
        syncCalendarListEntity.setOnly(false);//设置不只同步日历，也同步日历下的事件

        String syncCalendarListData = JSON.toJSONString(syncCalendarListEntity);
        Task task = new Task(taskQueue.getName(), "", false, TaskType.SYN_CALENDARLIST.getType(), syncCalendarListData, new Task.TaskStatus());

        //3.将`同步日历消息`加入`消息队列`
        taskQueue.pushTask(task);
        logger.info(String.format("将[同步CalendarList消息]加入[消息队列]. 队列: %s, 消息: %s", taskQueue.getName(), task.getData()));
    }
}

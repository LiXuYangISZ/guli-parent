package com.rg.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rg.eduservice.entity.EduCourseCollectEntity;

/**
 * 课程收藏
 *
 * @author Leo
 * @email 2422737092@qq.com
 * @date 2024-07-25 09:41:19
 */
public interface EduCourseCollectService extends IService<EduCourseCollectEntity> {

    /**
     * 收藏/取消收藏 课程
     * @param memberId
     * @param courseId
     */
    void collectCourse(String memberId, String courseId);

    /**
     *
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    Boolean isCollect(String courseId, String memberIdByJwtToken);
}


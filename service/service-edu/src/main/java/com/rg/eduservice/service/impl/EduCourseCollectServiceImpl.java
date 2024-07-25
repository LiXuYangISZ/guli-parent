package com.rg.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rg.eduservice.entity.EduCourseCollectEntity;
import com.rg.eduservice.mapper.EduCourseCollectMapper;
import com.rg.eduservice.service.EduCourseCollectService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class EduCourseCollectServiceImpl extends ServiceImpl<EduCourseCollectMapper, EduCourseCollectEntity> implements EduCourseCollectService {

    @Override
    public void collectCourse(String memberId, String courseId) {
        LambdaQueryWrapper <EduCourseCollectEntity> lambdaQueryWrapper = new LambdaQueryWrapper <>();
        lambdaQueryWrapper.eq(EduCourseCollectEntity::getCourseId,courseId).eq(EduCourseCollectEntity::getMemberId,memberId);
        Integer count = this.baseMapper.selectCount(lambdaQueryWrapper);
        if (count == 0){
            EduCourseCollectEntity courseCollectEntity = new EduCourseCollectEntity();
            courseCollectEntity.setCourseId(courseId);
            courseCollectEntity.setMemberId(memberId);
            courseCollectEntity.setIsDeleted(0);
            this.baseMapper.insert(courseCollectEntity);
        }else{
            EduCourseCollectEntity courseCollectEntity = this.getOne(lambdaQueryWrapper);
            courseCollectEntity.setIsDeleted(courseCollectEntity.getIsDeleted() == 1 ? 0: 1);
            this.baseMapper.updateById(courseCollectEntity);
        }
    }

    @Override
    public Boolean isCollect(String courseId, String memberId) {
        LambdaQueryWrapper <EduCourseCollectEntity> lambdaQueryWrapper = new LambdaQueryWrapper <>();
        lambdaQueryWrapper.eq(EduCourseCollectEntity::getCourseId,courseId).eq(EduCourseCollectEntity::getMemberId,memberId);
        EduCourseCollectEntity courseCollectEntity = this.baseMapper.selectOne(lambdaQueryWrapper);
        if (courseCollectEntity == null || courseCollectEntity.getIsDeleted() == 1){
            return false;
        }else{
            return true;
        }
    }
}
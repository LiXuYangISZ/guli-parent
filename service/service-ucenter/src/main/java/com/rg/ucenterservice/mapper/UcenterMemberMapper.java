package com.rg.ucenterservice.mapper;

import com.rg.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lxy
 * @since 2022-03-08
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer registerCount(@Param("day") String day);
}

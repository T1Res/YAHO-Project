package com.njm.yaho.mapper.oracle.schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ImageMapper {

	 String selectUserProfileImgById(@Param("userId") Long userId);
}
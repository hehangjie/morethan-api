package com.morethan.game.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.morethan.game.entity.Record;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RecordMapper extends BaseMapper<Record> {

    @Select("SELECT sum(amount) FROM t_record WHERE score_id = #{scoreId}")
    Double sumScoreAmount(@Param("scoreId") Long scoreId);

    @Select("SELECT count(record_id) FROM t_record WHERE score_id = #{scoreId}")
    Integer countRecord(@Param("scoreId") Long scoreId);
}

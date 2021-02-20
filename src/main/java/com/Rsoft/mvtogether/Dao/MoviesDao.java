package com.Rsoft.mvtogether.Dao;

import com.Rsoft.mvtogether.Entity.Movies;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LR
 * @date 2021/2/19 交流可加qq群 1027263551
 */
@Mapper
@Repository
public interface MoviesDao {
    // 根据电影名查找电影号
    @Select("select MvName from movies where MvNum = #{MvNum}")
    String getNameByNum(@Param("MvNum") String MvNum);

    // 插入一条电影信息
    @Insert("insert into movies (MvName,MvUrl) values (#{MvName},#{MvUrl})")
    void addMv(@Param("MvName") String MvName, @Param("MvUrl") String MvUrl);

    // 根据电影名查找电影号
    @Select("select MvNum from movies where MvName = #{MvName}")
    String getNumByName(@Param("MvName") String MvName);

    // 查找所有电影
    @Select("select * from movies limit #{offset},#{capacity}")
    List<Movies> getAllMoviesByPage(@Param("offset") int offset, @Param("capacity") int capacity);

    @Select("select * from movies ")
    List<Movies> getAllMovies();

    // 根据电影名查找电影URL
    @Select("select MvUrl from movies where MvName =#{MvName}")
    String getUrlByName(@Param("MvName") String MvName);

    // 根据电影号查找电影URL
    @Select("select MvUrl from movies where MvNum =#{MvNum}")
    String getUrlByNum(@Param("MvNum") String MvNum);
}

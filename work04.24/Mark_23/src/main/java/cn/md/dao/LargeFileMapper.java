package cn.md.dao;


import cn.md.entity.LargeFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LargeFileMapper {
    @Insert(value = "insert into t_largefile "
            + "(filename,content) "
            + "values(#{filename},"
            + " #{content, javaType=byte[], jdbcType=BLOB, " +
            "typeHandler=org.apache.ibatis.type.BlobTypeHandler})")
    public int add(LargeFile largefile);

    @Select(value = "select pic,filename,content from t_largefile where pic=#{uuid}")
    public LargeFile findOne(int id);

    @Select(value = "Select pic from t_largefile where filename=#{filename}")
    public int findPic(String filename);

    @Delete(value = "delete from t_largefile where pic=#{id}")
    int delete(int id);

    @Select(value = "SELECT COUNT(*) FROM t_largefile WHERE t_largefile.filename = #{fileName}")
    int findPicByName(String filename);
}

package com.piveguyz.empickbackend.employment.jobtests.common.typehandler;

import com.piveguyz.empickbackend.employment.jobtests.common.enums.JobtestDifficulty;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedTypes(JobtestDifficulty.class)
public class JobtestDifficultyTypeHandler  extends BaseTypeHandler<JobtestDifficulty> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JobtestDifficulty parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    @Override
    public JobtestDifficulty getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JobtestDifficulty.fromCode(rs.getInt(columnName));
    }

    @Override
    public JobtestDifficulty getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JobtestDifficulty.fromCode(rs.getInt(columnIndex));
    }

    @Override
    public JobtestDifficulty getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JobtestDifficulty.fromCode(cs.getInt(columnIndex));
    }
}
